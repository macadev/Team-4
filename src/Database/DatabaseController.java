package Database;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
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
                stmt.executeUpdate("create table if not exists  Users (username String , password String, realName String, highScore int, levelUnlocked int, gamesPlayed int )");
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
        System.out.println("Database Contents: ");
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

                if (!rs.isBeforeFirst()) {
                    System.out.println("Username/Password does not exist");
                }

                while (rs.next()) {
                    String usernameOnDB = rs.getString("username");
                    String passwordOnDB = rs.getString("password");
                    System.out.println("Username " + usernameOnDB);
                    System.out.println("Password " + passwordOnDB);
                    System.out.println("Real Name " + rs.getString("realName"));
                }

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


    public static boolean createNewUser(String userName, String pass, String rName) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement stmt;
            ResultSet rsUserCheck;
            String sql = "INSERT INTO Users"
                    + "(USERNAME, PASSWORD, REALNAME, HIGHSCORE, LEVELUNLOCKED, GAMESPLAYED) VALUES"
                    + "(?,?,?,?,?,?)";
            String verify = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                stmt = connection.prepareStatement(verify);
                stmt.setString(1, userName);
                rsUserCheck = stmt.executeQuery();
                if (rsUserCheck.isBeforeFirst()) {
                    System.out.println("User already exists");
                    return false;
                }
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, userName);
                stmt.setString(2, pass);
                stmt.setString(3, rName);
                stmt.setInt(4, 0);
                stmt.setInt(5, 1);
                stmt.setInt(6,0);
                stmt.executeUpdate();
                System.out.println("New User inserted into database");

                createDirectoryForUserSavedFiles(userName);

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

    public static void setLevelUnlocked(String username, int level) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        int currentLevelUnlocked = getLevelUnlocked(username);
        if (currentLevelUnlocked >= level) return;

        try {
            Connection connection = null;
            PreparedStatement updateLevelUnlocked;
            String sql = "update Users set levelUnlocked = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            PreparedStatement updateStmt;
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                updateLevelUnlocked = connection.prepareStatement(sql);
                if (level >= 1 && level <= 60) {
                    updateLevelUnlocked.setInt(1, level);
                    updateLevelUnlocked.setString(2, username);
                    updateLevelUnlocked.executeUpdate();
                } else {
                    System.out.println("Level does not exist");
                    return;
                }
                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, username);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    int updatedLevel = rsUpdate.getInt("levelUnlocked");
                    System.out.println("Updated levelUnlocked to : " + updatedLevel);
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
    }


    public static int getLevelUnlocked(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        int currentLevel;
        currentLevel = 1;

        try {
            Connection connection = null;
            ResultSet rsLevel = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";
            int size = 0;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsLevel = stmt.executeQuery();

                while (rsLevel.next()) {
                    currentLevel = rsLevel.getInt("levelUnlocked");
                    System.out.println("Unlocked Level is : " + currentLevel);
                    size++;
                    System.out.println("size of result set of unlocked level is : " + size);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsLevel != null) {
                    rsLevel.close();
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
        return currentLevel;
    }

    public static void createDirectoryForUserSavedFiles(String username) {
        //Create directory in savegames folder where saved game data will be stored
        //we follow the unix naming convention
        File dir = new File("savedgames/" + username);
        dir.mkdir();
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


    public static boolean updatePassword(String newPass, String Uname) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement updatePasswordStmt;
            PreparedStatement updateStmt;
            String sql = "update Users set password = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                updatePasswordStmt = connection.prepareStatement(sql);
                if (newPass != null && !newPass.isEmpty()) {
                    updatePasswordStmt.setString(1, newPass);
                    updatePasswordStmt.setString(2, Uname);
                    updatePasswordStmt.executeUpdate();
                } else {
                    System.out.println("Password not changed");
                    return false;
                }
                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, Uname);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    String updatedPassword = rsUpdate.getString("password");
                    System.out.println("Updated Password to : " + updatedPassword);
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


    public static boolean updateRealName(String newRealName, String Uname) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement updateRealNameStmt;
            PreparedStatement updateStmt;
            String sqlUpdateRealName = "update Users set realName = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                updateRealNameStmt = connection.prepareStatement(sqlUpdateRealName);
                if (newRealName != null && !newRealName.isEmpty()) {
                    updateRealNameStmt.setString(1, newRealName);
                    updateRealNameStmt.setString(2, Uname);
                    updateRealNameStmt.executeUpdate();
                } else {
                    System.out.println("Real name not changed");
                    return false;
                }

                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, Uname);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    String updatedRealName = rsUpdate.getString("realName");
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


    public static boolean deleteAccount(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement delAccount;
            PreparedStatement updateStmt;
            String deleteAccount = "DELETE from Users where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                delAccount = connection.prepareStatement(deleteAccount);
                delAccount.setString(1, username);
                delAccount.executeUpdate();
                System.out.println("Account Deleted");
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


    public static int getScore(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        int score;
        score = 0;

        try {
            Connection connection = null;
            ResultSet rsScore = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsScore = stmt.executeQuery();

                while (rsScore.next()) {
                    score = rsScore.getInt("highScore");
                    System.out.println("Highscore of " + username + " is: " + score);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsScore != null) {
                    rsScore.close();
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
        return score;
    }

    public static void setScore(String username, int score) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        int currentScore = getScore(username);

        try {
            Connection connection = null;
            PreparedStatement updateHighScore;
            String sql = "update Users set highScore = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            PreparedStatement updateStmt;
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                updateHighScore = connection.prepareStatement(sql);
                updateHighScore.setInt(1, score + currentScore);
                updateHighScore.setString(2, username);
                updateHighScore.executeUpdate();

                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, username);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    int newHighScore = rsUpdate.getInt("highScore");
                    System.out.println("Updated High Score to : " + newHighScore);
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
    }

    public static ArrayList<PlayerScore> getTopScoresSet() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        ResultSet rsTopScores = null;
        ArrayList<PlayerScore> ps = new ArrayList<PlayerScore>();
        int topScore;
        int i;
        int size = 0;
        try {
            Connection connection = null;
            Statement stmt = null;
            String sql = "SELECT * from Users ORDER BY highScore DESC";

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                stmt = connection.createStatement();
                rsTopScores = stmt.executeQuery(sql);
                while (rsTopScores.next()) {
                    topScore = rsTopScores.getInt("highScore");
                    System.out.println("Top score is : " + topScore + " and username is : " + rsTopScores.getString("username") + " for user (real name) " + rsTopScores.getString("realName") + " they have played " + rsTopScores.getInt("gamesPlayed")+ " games.");
                    size++;
                    System.out.println("size of getTopScores result set is: " + size);
                    ps.add(PlayerScore.createPlayer(rsTopScores.getString("username"), rsTopScores.getInt("highScore"), rsTopScores.getString("realName"), rsTopScores.getInt("gamesPlayed")));
                    System.out.println("this is being executed");
                }
                System.out.println("size of arraylist is : " + ps.size());
                for (i = 0 ; i<ps.size(); i++) {
                    System.out.println("Top Users are : " + ps.get(i).username + " with real name " + ps.get(i).realName + " with "+ ps.get(i).score + " points in " + ps.get(i).gamesPlayed + " games played.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsTopScores != null) {
                    rsTopScores.close();
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
        return ps;
    }

    public static void incrementGamesPlayed(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try {
            Connection connection = null;
            PreparedStatement incrementGamesPlayed;
            String sql = "update Users set gamesPlayed = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            PreparedStatement updateStmt;
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                incrementGamesPlayed = connection.prepareStatement(sql);
                incrementGamesPlayed.setInt(1, getGamesPlayed(username) + 1);
                incrementGamesPlayed.setString(2, username);
                incrementGamesPlayed.executeUpdate();

                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, username);
                rsUpdate = updateStmt.executeQuery();
                while (rsUpdate.next()) {
                    int updatedGamesPlayed = rsUpdate.getInt("gamesPlayed");
                    System.out.println("Updated games played to : " + updatedGamesPlayed);
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
    }

    public static int getGamesPlayed(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        int gamesPlayed;
        gamesPlayed = 0;

        try {
            Connection connection = null;
            ResultSet rsGamesPlayed = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:user_data.db");
                //connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsGamesPlayed = stmt.executeQuery();

                while (rsGamesPlayed.next()) {
                    gamesPlayed = rsGamesPlayed.getInt("gamesPlayed");
                    System.out.println(username + "has played: " + gamesPlayed + " games.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsGamesPlayed != null) {
                    rsGamesPlayed.close();
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
        return gamesPlayed;
    }





}




