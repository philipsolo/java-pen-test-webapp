import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet("/getUsers")
public class Admin_home extends HttpServlet  {

    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Initialize DB class acquiring credentials for connection
        DataStore db = new DataStore();
        ResultSet accounts = null;
        PreparedStatement query = null;

        try {
            // create database connection and statement
            Class.forName(db.getDriver());
            conn = DriverManager.getConnection(db.getDB_URL(), db.getUser(), db.getPass());

            //Select all account in table
            query = conn.prepareStatement("SELECT * FROM userAccounts");
            accounts = query.executeQuery();


            // create HTML table text
            String content = "<table border='1' cellspacing='2' cellpadding='2' width='100%' align='left'>" +
                    "<tr><th>First name</th><th>Last name</th><th>Email</th><th>Phone number</th><th>Username</th><th>Account Type</th></tr>";



            // add HTML table data using data from database
            while (accounts.next()) {
                content += "<tr><td>"+ accounts.getString("Firstname") + "</td>" +
                        "<td>" + accounts.getString("Lastname") + "</td>" +
                        "<td>" + accounts.getString("Email") + "</td>" +
                        "<td>" + accounts.getString("Phone") + "</td>" +
                        "<td>" + accounts.getString("Username") + "</td>" +
                        "<td>" + accounts.getString("account_type") + "</td></tr>";
            }
            // finish HTML table text
            content += "</table>";

            // close connection
            conn.close();

            // display table to admin page
            request.setAttribute("table", content);
            request.getRequestDispatcher("/admin_home.jsp").forward(request, response);


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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        request.setAttribute("message",  " ");
    }
}