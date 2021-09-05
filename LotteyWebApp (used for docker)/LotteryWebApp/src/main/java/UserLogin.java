import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Key;
import java.sql.*;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {


    private Connection conn;
    private Statement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize DB object with info for connection
        DataStore db = new DataStore();
        HttpSession session = request.getSession();

        //Retrieve data inserted in HTML form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sign_out = request.getParameter("sign_out");

        //Initialize the user class
        User user = new User(username,password);

        //If sign out button is pressed enter here
        if (sign_out != null && sign_out.equals("sign_out")){

            //Temporarily store user details as a variable to be inserted in new session
            Key privateTemp = (Key) session.getAttribute("private_key");
            Key publicTemp = (Key) session.getAttribute("public_key");

            //Delete all session data
            session.invalidate();

            //Create a new session
            HttpSession sessionTemp = request.getSession();

            //Set Private and Public Key into the new session (all txt files are encrypted with the same)
            sessionTemp.setAttribute("private_key", privateTemp);
            sessionTemp.setAttribute("public_key", publicTemp);

            //Transfer User to Sign out Page
            request.setAttribute("message", "You have successfully signed out");
            request.getRequestDispatcher("/error.jsp").forward(request,response);
        }
        else {
            try {
                // create database connection and statement
                Class.forName(db.getDriver());
                conn = DriverManager.getConnection(db.getDB_URL(), db.getUser(), db.getPass());
                stmt = conn.createStatement();


                // query database and get results
                PreparedStatement query = conn.prepareStatement("SELECT Pwd, salt, Firstname, Lastname, Email,account_type,Login_attempts FROM userAccounts where Username = ?");

                // Set username for query
                query.setString(1,user.getUsername());
                ResultSet accounts = query.executeQuery();

                //If user exists
                if (accounts.next()) {

                    //Retrieve available login attempts from db and turn into int
                    int loginAttempt = Integer.parseInt(accounts.getString("Login_attempts"));

                    //If login limit not reached
                    if (loginAttempt < 3){

                    //Initialize offUser class adding db retrieved info
                    offUser dbUser = new offUser(username, accounts.getString("Pwd"),
                            accounts.getString("salt"));

                    //Insert all user info into the user class
                    user.setFirstname(accounts.getString("Firstname"));
                    user.setLastname(accounts.getString("Lastname"));
                    user.setEmail(accounts.getString("Email"));
                    user.setAccount_type(accounts.getString("account_type"));

                    //Check if the provided password with the salt retrieved from db matches the hash and salt from db
                    boolean validated = dbUser.userValidate(dbUser.getPassword(), dbUser.getSalt(), user.getPassword());

                    if (validated){

                        // Get lottery winning numbers for later
                        PreparedStatement lot_nums = conn.prepareStatement("SELECT * FROM winning_number");
                        ResultSet winning_num = lot_nums.executeQuery();

                        //If winning numbers found insert into current session
                        if(winning_num.next()){
                            session.setAttribute("winning_num", winning_num.getString("lottery_win"));
                        }


                        //Set the password as the hashed one
                        user.setPassword(dbUser.getPassword());

                        //Set all user details into the session
                        session.setAttribute("username",user.getUsername());
                        session.setAttribute("pass",user.getPassword());
                        session.setAttribute("email",user.getEmail());
                        session.setAttribute("firstname",user.getFirstname());
                        session.setAttribute("lastname",user.getLastname());
                        session.setAttribute("account_type",user.getAccount_type());

                        //Since the user is verified turn the attempt counter back to 0 and update DB
                        insertAttempt(conn, loginAttempt,username,true);

                        //Set the page according to user type
                        String page = "/account.jsp";
                        if (user.getAccount_type().equals("admin")){
                            page = "/admin_home.jsp";
                        }
                        request.getRequestDispatcher(page).forward(request,response);

                    }
                    else {
                        //If login attempt didnt succeed increment DB attempts by 1 and inform the user
                        int attemptsRemaining = insertAttempt(conn, loginAttempt,username,false);
                        request.setAttribute("message", "Password Incorrect "+attemptsRemaining+" /3 attempts remaining");
                        request.getRequestDispatcher("/error.jsp").forward(request,response);;
                    }

                }else{
                    request.setAttribute("message", "You have reached Max login attempts please reset account from server");
                    request.getRequestDispatcher("/error.jsp").forward(request,response);}
                }
                else {
                    request.setAttribute("message", "User not found");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
                // close connection
                conn.close();

            } catch (Exception se) {
                se.printStackTrace();
                // display error.jsp page with given message if successful
                request.setAttribute("message", "Database Error, Please try again");
                request.getRequestDispatcher("/error.jsp").forward(request, response);

            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    public static int insertAttempt(Connection conn, int loginAttempt, String username, boolean verified) throws SQLException {
        //If user inserted details correctly set attempts to 0 else increment by 1
        if(verified){
            loginAttempt = 0;
        }else {loginAttempt++;}

        //Insert login attempts into database in the users row.
        String sql = "UPDATE userAccounts set Login_attempts = ? where Username= ?";
        PreparedStatement prepstmt = conn.prepareStatement(sql);
        prepstmt .setString(1, String.valueOf(loginAttempt));
        prepstmt .setString(2, username);
        prepstmt .executeUpdate();
        return loginAttempt;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp");
    }
}
