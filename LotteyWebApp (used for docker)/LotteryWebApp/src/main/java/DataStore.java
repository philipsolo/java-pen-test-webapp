import java.sql.*;

public class DataStore {
    private final String url;
    private final String user;
    private final String pass;
    private final String driver;
    // URLs to connect to database depending on your development approach
    // (NOTE: please change to option 1 when submitting)

    // 1. use this when running everything in Docker using docker-compose
     String DB_URL = "jdbc:mysql://db:3306/lottery";

    // 2. use this when running tomcat server locally on your machine and mysql database server in Docker
//    String DB_URL = "jdbc:mysql://localhost:33333/lottery";

    // 3. use this when running tomcat and mysql database servers on your machine
    //String DB_URL = "jdbc:mysql://localhost:3306/lottery";

    //Used to allow for swift database info change
    public DataStore() {
        this.url = DB_URL;
        this.user = "user";
        this.pass = "password";
        this.driver = "com.mysql.cj.jdbc.Driver";
    }

    public String getUser(){
        return user;
    }
    public String getPass(){
        return pass;
    }
    public String getDriver(){
        return driver;
    }
    public String getDB_URL(){
        return DB_URL;
    }


}
