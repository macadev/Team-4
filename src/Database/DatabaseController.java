package Database;

import javax.xml.transform.Result;
import java.sql.*;
//2014
/**
 * Created by Owen Li on 14-11-08.
 */
public class DatabaseController {

    public static void initializeDatabase() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            Statement stmt = null;

            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                stmt = connection.createStatement();
                stmt.executeUpdate("create table if not exists  Users (username String , password String, realName String, highScore int)");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {

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

    public static void printDBContents() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        System.out.println("DB Contents!!!!");
        Class.forName("org.sqlite.JDBC");
        String verify = "select * from Users";
        try {
            Connection connection = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");

                stmt = connection.prepareStatement(verify);
                rs = stmt.executeQuery();

                if (!rs.isBeforeFirst() ) {
                    System.out.println("Username/Password does not exist");
                }

                while (rs.next()) {
                    String usernameOnDB = rs.getString("username");
                    String passwordOnDB = rs.getString("password");
                    System.out.println("Username " +usernameOnDB);
                    System.out.println("Password " +passwordOnDB);
                }

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


    public static boolean createNewUser(String Uname, String pass, String rName) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement stmt;
            ResultSet rsUserCheck;
            String sql = "INSERT INTO Users"
                    + "(USERNAME, PASSWORD, REALNAME, HIGHSCORE) VALUES"
                    + "(?,?,?,?)";
            String verify = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                stmt = connection.prepareStatement(verify);
                stmt.setString(1, Uname);
                rsUserCheck = stmt.executeQuery();
                if (rsUserCheck.isBeforeFirst()) {
                    System.out.println("User already exists");
                    return false;
                }
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, Uname);
                stmt.setString(2, pass);
                stmt.setString(3, rName);
                stmt.setInt(4, 0);
                stmt.executeUpdate();
                System.out.println("New User inserted into database");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean authenticateUser(String Uname, String pass) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        boolean result = false;

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

                if (!rsUser.isBeforeFirst()) {
                    System.out.println("Username/Password does not exist");
                }

                while (rsUser.next()) {
                    usernameOnDB = rsUser.getString("username");
                    passwordOnDB = rsUser.getString("password");
                    System.out.println("Verified Username : " + usernameOnDB);
                    System.out.println("Verified Password : " + passwordOnDB);
                }


                if (usernameOnDB != null && passwordOnDB != null) {
                    result = true;
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

        return result;
    }


    public boolean updateInformation(String newPass, String newRealName, String Uname) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement updatePasswordStmt;
            PreparedStatement updateRealNameStmt;
            PreparedStatement updateStmt;
            String sql = "update Users set password = ? where username = ?;";
            String sqlUpdateRealName = "update Users set realName = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                updatePasswordStmt = connection.prepareStatement(sql);
                updatePasswordStmt.setString(1, newPass);
                updatePasswordStmt.setString(2, Uname);
                updatePasswordStmt.executeUpdate();

                updateRealNameStmt = connection.prepareStatement(sqlUpdateRealName);
                updateRealNameStmt.setString(1, newRealName);
                updateRealNameStmt.setString(2, Uname);
                updateRealNameStmt.executeUpdate();


                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1,Uname);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    String updatedPassword = rsUpdate.getString("password");
                    String updatedRealName = rsUpdate.getString("realName");
                    System.out.println("Updated Password to : " + updatedPassword);
                    System.out.println("Updated Real Name to : " + updatedRealName);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsUpdate != null) {
                    rsUpdate.close();
                }

                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}








