import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;


@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {

    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Access DB class to retrieve connection data
        DataStore db = new DataStore();
        PreparedStatement query = null;
        ResultSet accounts = null;


        // get parameter data that was submitted in HTML form (use form attributes 'name')
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String acc_type = request.getParameter("acc_type");

        try{
            // create database connection and statement
            Class.forName(db.getDriver());
            conn = DriverManager.getConnection(db.getDB_URL(),db.getUser(),db.getPass());


            // query database to find user
            query = conn.prepareStatement("SELECT * FROM userAccounts where Username = ?");
            query.setString(1,username);
            accounts = query.executeQuery();

            //Check if username is taken, continue if not
            if (!accounts.next()){

                //Initialize Offline user Class
                offUser user = new offUser(username, password, null);

                //Call password hashing function, second part null in order to create a random salt
                user.passHash(user.getPassword(),null);

                //Set the account type
                if (acc_type != null){
                    acc_type = "admin";
                }else acc_type = "public";

                // Create sql query
                String insertQuery = "INSERT INTO userAccounts (Firstname, Lastname, Email, Phone, Username, Pwd,salt,account_type,Login_attempts)"
                        + " VALUES (?, ?, ?, ?, ?, ?,?,?,?)";

                // set values into SQL query statement
                stmt = conn.prepareStatement(insertQuery);
                stmt.setString(1,firstname);
                stmt.setString(2,lastname);
                stmt.setString(3,email);
                stmt.setString(4,phone);
                stmt.setString(5,username);
                stmt.setString(6,user.getPassword());
                stmt.setString(7,user.getSalt());
                stmt.setString(8,acc_type);
                stmt.setString(9,"0");

                // execute query and close connection
                stmt.execute();
                conn.close();

                //Set session attributes for later redirect (no login required)
                HttpSession session = request.getSession();
                session.setAttribute("username",user.getUsername());
                session.setAttribute("pass",user.getPassword());
                session.setAttribute("email",email);
                session.setAttribute("firstname",firstname);
                session.setAttribute("lastname",lastname);
                session.setAttribute("acc_type",acc_type);

                //Send user according to account type
                String page = "/account.jsp";
                if(acc_type.equals("admin")){
                     page = "/admin_home.jsp";
                }

                request.setAttribute("message", firstname+", you have successfully created an account");
                request.getRequestDispatcher(page).forward(request, response);

            }else {
                //Username is taken ask user to change
                request.setAttribute("message",  "Username "+ username +" is taken");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch(Exception se){
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            request.setAttribute("message", firstname+", There has been an error");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2){
                se2.printStackTrace();
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        request.setAttribute("message",  " ");
    }
}
