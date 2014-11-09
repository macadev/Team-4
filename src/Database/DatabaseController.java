package Database;

import javax.xml.transform.Result;
import java.sql.*;

/**
 * Created by Owen Li on 14-11-08.
 */
public class DatabaseController {

    public void initializeDatabase() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                stmt = connection.createStatement();
                stmt.executeUpdate("drop table if exists Users");
                stmt.executeUpdate("create table Users (username String ,password String)");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {

                if (stmt != null) {
                    stmt.close();
                }

                if (connection != null){
                    connection.close();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createNewUser() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                stmt = connection.createStatement();
                stmt.executeUpdate("insert into Users values('John Doe','Johnpass')");
                stmt.executeUpdate("insert into Users values('m','d')");
                stmt.executeUpdate("insert into Users values('Jane Doe','Janepass')");
                rs = stmt.executeQuery("select * from Users");
                while (rs.next()) {
                    // read the result set
                    System.out.println("username = " + rs.getString("username"));
                    System.out.println("password = " + rs.getString("password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String Uname, String pass) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try {
            Connection connection = null;
            ResultSet rsUser = null;
            PreparedStatement stmt = null;
            String usernameOnDB = null;
            String passwordOnDB = null;
            String sql = "select * from Users where username = ? and password = ? ";


            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, Uname);
                stmt.setString(2, pass);
                rsUser = stmt.executeQuery();

                if (!rsUser.isBeforeFirst() ) {
                    System.out.println("No data");
                }

                while (rsUser.next()) {
                    usernameOnDB = rsUser.getString("username");
                    passwordOnDB = rsUser.getString("password");
                    System.out.println(usernameOnDB);
                    System.out.println(passwordOnDB);
                }


                if (usernameOnDB != null && passwordOnDB != null) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsUser != null) {
                    rsUser.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}




