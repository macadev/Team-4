package Database;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by Owen Li on 14-11-08.
 */
public class DatabaseController {

    public void initializeDatabase() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("drop table if exists Users");
            stmt.executeUpdate("create table Users (username String ,password String)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }


    public void createNewUser() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("insert into Users values('John Doe','Johnpass')");
            stmt.executeUpdate("insert into Users values('Jane Doe','Janepass')");
            ResultSet rs = stmt.executeQuery("select * from Users");
            while (rs.next()) {
                // read the result set
                System.out.println("username = " + rs.getString("username"));
                System.out.println("password = " + rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String Uname, String pass) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            ResultSet rsuser = stmt.executeQuery("select * from Users where username = 'Uname'");
            String string1 = rsuser.getString("username");
            Statement stmt2 = connection.createStatement();
            ResultSet rspass = stmt2.executeQuery("select * from Users where password = 'pass'");
            String string2 = rspass.getString("password");
            if (string1 != null && string2 != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}




