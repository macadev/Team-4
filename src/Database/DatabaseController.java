/**
 * Created by Owen Li on 14-11-08.
 */
package Database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * The DatabaseController class handles all user data storage by implementing the SQLite library.
 * The methods in this class use SQL queries to read,update,delete,and create entries from a table inside the database.
 * When appropriate, the methods in this class use the username of a user as the key to access all other entries in the database
 * corresponding to that username.
 * All methods involving database connections will automatically terminate their connections following execution.
 */
public class DatabaseController {

    /**
     * String representing the database_id, used to connect to the database.
     */
    public static String database_id = "jdbc:sqlite:user_data.db";
    /**
     * String representing directory for which games will be saved to
     */
    public static String saveDirectory = "savedgames/";

    /**
     * Initializes the database, and inserts a table used to store user data. Contains fields: username,
     * password, real name, high-score, level unlocked and number of games played.
     * Connections are closed after statements execute.
     * @throws ClassNotFoundException
     */
    public static void initializeDatabase() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            Statement stmt = null;
            try {
                // create a database connection
                connection = DriverManager.getConnection(database_id);
                stmt = connection.createStatement();
                stmt.executeUpdate("create table if not exists  Users (username String , password String, realName String, highScore int, levelUnlocked int, gamesPlayed int )");
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database: user_data.db initialized");
    }


    /**
     * Drops the tables present in the database. All data in the tables will be lost.
     * @throws ClassNotFoundException
     */
    public static void dropDatabaseTable() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            Statement stmt = null;

            try {
                // create a database connection
                connection = DriverManager.getConnection(database_id);
                stmt = connection.createStatement();
                stmt.executeUpdate("DROP TABLE if exists Users");
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
        System.out.println("Database: test_data.db table dropped");
    }

    /**
     * Inserts a new user into the database table "Users". The boolean returns true if a user was successfully inserted,
     * and false if it was not successful.
     * @param userName A string representing the specified username for the user entry.
     * @param pass A string representing the specified password for the user entry.
     * @param rName A string representing the specified real name for the user entry.
     * @return Returns true if the user insertion operation was successful, false if it was not succesful.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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
                stmt.setInt(6, 0);
                stmt.executeUpdate();
                System.out.println("New User inserted into database");
                createDirectoryForUserSavedFiles(userName);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Creates a directory savedgames where saved game data is stored.
     * @param username A string representing the name of the folder to be created, where the user's saved game files will be stored.
     *                 Corresponds to the user's username.
     */
    public static void createDirectoryForUserSavedFiles(String username) {
        File dir = new File(saveDirectory + username);
        dir.mkdirs();
    }

    /**
     * Getter method to return a password for a given username.
     * @param username A string representing the username of which the corresponding password is to be obtained.
     * @return returns a String representing the password corresponding to the username upon which getPassword() was called.
     * @throws ClassNotFoundException
     */
    public static String getPassword(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String password;
        password = null;

        try {
            Connection connection = null;
            ResultSet rsPassword = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection(database_id);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsPassword = stmt.executeQuery();

                while (rsPassword.next()) {
                    password = rsPassword.getString("password");
                    System.out.println("Password for user " + username + " is : " + password);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsPassword != null) {
                    rsPassword.close();
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
        return password;
    }

    /**
     * Getter method to return a real name for a given username.
     * @param username A string representing the username of which the corresponding real name is to be obtained.
     * @return returns a String representing the real name corresponding to the username upon which getRealName() was called.
     * @throws ClassNotFoundException
     */
    public static String getRealName(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String realName;
        realName = null;

        try {
            Connection connection = null;
            ResultSet rsRealName = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";

            try {
                connection = DriverManager.getConnection(database_id);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsRealName = stmt.executeQuery();

                while (rsRealName.next()) {
                    realName = rsRealName.getString("realName");
                    System.out.println("real name for user " + username + " is : " + realName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsRealName != null) {
                    rsRealName.close();
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
        return realName;
    }

    /**
     * Sets the levelUnlocked field in the database table to a specified level for a given username.
     * @param username A string representing the username of which the corresponding level is to be set.
     * @param level An int representing the level of which the corresponding user's levelUnlocked field in the database is to be set.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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
                if (!rsUpdate.isBeforeFirst()) {
                    System.out.println("level not updated, user doesn't exist");
                    return;
                }
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

    /**
     * Getter method to return an int level corresponding to a given username.
     * @param username A string representing the username of which the corresponding level is to be obtained.
     * @return returns an int representing the level unlocked corresponding to the username upon which getLevelUnlocked() was called.
     * @throws ClassNotFoundException
     */
    public static int getLevelUnlocked(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        int currentLevel;
        currentLevel = 1;

        try {
            Connection connection = null;
            ResultSet rsLevel = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";
            try {
                connection = DriverManager.getConnection(database_id);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsLevel = stmt.executeQuery();

                while (rsLevel.next()) {
                    currentLevel = rsLevel.getInt("levelUnlocked");
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

    /**
     * Authenticates a user by querying the database for a specified username and password.
     * @param Uname A string representing the specified username of the user to be authenticated.
     * @param pass A string representing the specified password of the user to be authenticated.
     * @return Boolean returns true if the user was succesfully authenticated (user exists in the user_data.db table, and username/password match). Returns false otherwise.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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

    /**
     * Updates the password field in the user database of a given user to a new specified password.
     * @param newPass A string representing the new specified password for the user.
     * @param Uname A string representing the specified username for which the password field must be updated.
     * @return Boolean returns true if the user's password was succesfully updated. Returns false otherwise.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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
                if (!rsUpdate.isBeforeFirst()) {
                    System.out.println("User not found, password unchanged");
                    return false;
                }
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

    /**
     * Updates the real name field in the user database of a given user to a new specified real name.
     * @param newRealName A string representing the new specified real name for the user.
     * @param Uname A string representing the specified username for which the real name field must be updated.
     * @return  Boolean returns true if the user's real name was successfully updated. Returns false otherwise.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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
                if (!rsUpdate.isBeforeFirst()) {
                    System.out.println("User not found, realname unchanged");
                    return false;
                }
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

    /**
     * Removes all the fields in the database table associated to a username.
     * @param username A string representing the username of a user, for which all fields should be deleted.
     * @return Boolean returns true if the deletion was successful (all fields corresponding to the username removed from the table). Returns false otherwise.
     * @throws ClassNotFoundException
     */
    public static boolean deleteAccount(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = null;
            PreparedStatement delAccount;
            String deleteAccount = "DELETE from Users where username = ?;";

            try {
                connection = DriverManager.getConnection(database_id);
                delAccount = connection.prepareStatement(deleteAccount);
                delAccount.setString(1, username);
                delAccount.executeUpdate();
                System.out.println("Account Deleted");
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

    /**
     * Getter method to return an int score corresponding to a given username.
     * @param username A string representing the username of which the corresponding score is to be obtained.
     * @return Returns an int representing the score corresponding to the username upon which getScore() was called.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsScore = stmt.executeQuery();

                while (rsScore.next()) {
                    score = rsScore.getInt("highScore");
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

    /**
     * Adds a specified score to the existing score of a user in the database, as specified by the username upon which setScore() is called.
     * The score for a given user is culmulative.
     * @param username A string representing the username of which the specified score is to be added to the existing score.
     * @param score An int representing the score of which the corresponding user's score field in the database is to be incremented by.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
                if (score < 0) {
                    System.out.println("negative score invalid");
                    return;
                }

                updateHighScore = connection.prepareStatement(sql);
                updateHighScore.setInt(1, score + currentScore);
                updateHighScore.setString(2, username);
                updateHighScore.executeUpdate();
                updateStmt = connection.prepareStatement(updateRecords);
                updateStmt.setString(1, username);
                rsUpdate = updateStmt.executeQuery();
                if (!rsUpdate.isBeforeFirst()) {
                    System.out.println("score not updated, user doesn't exist");
                    return;
                }
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

    /**
     * Creates an arraylist of type PlayerScore, populated by PlayerScore objects corresponding to all users in the database.
     * The arraylist is sorted by score, in descending order.
     * @return Returns an arraylist populated with PlayerScore objects corresponding to all users in the database sorted by score in descending order.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
                //connection.setAutoCommit(false);
                stmt = connection.createStatement();
                rsTopScores = stmt.executeQuery(sql);
                while (rsTopScores.next()) {
                    ps.add(PlayerScore.createPlayer(rsTopScores.getString("username"), rsTopScores.getInt("highScore"), rsTopScores.getString("realName"), rsTopScores.getInt("gamesPlayed")));
                }
                System.out.println("size of arraylist is : " + ps.size());
                for (i = 0; i < ps.size(); i++) {
                    System.out.println("Top Users are : " + ps.get(i).username + " with real name " + ps.get(i).realName + " with " + ps.get(i).score + " points in " + ps.get(i).gamesPlayed + " games played.");
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

    /**
     * Increments the number of games played by 1 for a specified user.
     * @param username A string representing the username for which the corresponding gamesPlayed field is to be incremented by 1.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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

    /**
     * Getter method to return the number of games played corresponding to a given username.
     * @param username A string representing the username of which the corresponding number of games played is to be obtained.
     * @return Returns an int representing the number of games played corresponding to the username upon which getGamesPlayed() was called.
     * @throws ClassNotFoundException
     */
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
                connection = DriverManager.getConnection(database_id);
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

    /**
     * Creates a PlayerScore object with the entries corresponding to a specified username in the database.
     * @param username A string representing the username for which the relevant corresponding entries in the database are to be pulled.
     * @return Returns a PlayerScore object defined by elements corresponding to the specified username.
     * @throws ClassNotFoundException
     */
    public static PlayerScore getPlayerObject(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        PlayerScore player = null;
        try {
            Connection connection = null;
            ResultSet rsUsers = null;
            PreparedStatement stmt = null;
            String sql = "select * from Users where username = ?";


            try {
                connection = DriverManager.getConnection(database_id);
                //connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                rsUsers = stmt.executeQuery();

                while (rsUsers.next()) {
                    player = PlayerScore.createPlayer(username, rsUsers.getInt("highScore"), rsUsers.getString("realName"), rsUsers.getInt("gamesPlayed"));
                    System.out.println("Player Object created successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rsUsers != null) {
                    rsUsers.close();
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
        return player;
    }

    /**
     * Decrements the number of games played by 1 for a specified user.
     * @param username A string representing the username for which the corresponding gamesPlayed field is to be decremented by 1.
     * @throws ClassNotFoundException
     */
    public static void decrementGamesPlayed(String username) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try {
            Connection connection = null;
            PreparedStatement incrementGamesPlayed;
            String sql = "update Users set gamesPlayed = ? where username = ?;";
            String updateRecords = "SELECT * from Users where username = ?;";
            PreparedStatement updateStmt;
            ResultSet rsUpdate = null;

            try {
                connection = DriverManager.getConnection(database_id);
                incrementGamesPlayed = connection.prepareStatement(sql);
                incrementGamesPlayed.setInt(1, getGamesPlayed(username) - 1);
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
}