package GameObject.Database;

import Database.DatabaseController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseControllerTest {

    @Before
    public void InitializeDatabase() throws Exception {
        DatabaseController.database_id = "jdbc:sqlite:test_data.db";
        DatabaseController.initializeDatabase();
        DatabaseController.createNewUser("username", "password","realname");
    }
    @Test
    public void createNewUser() throws Exception {

        assertEquals("The level is set to 0 upon user creation",1, DatabaseController.getLevelUnlocked("username"));
        assertEquals("Highscore is set to 0 upon user creation",0,DatabaseController.getScore("username"));
    }


    @Test
    public void testCreateDirectoryForUserSavedFiles() throws Exception {

    }

    @Test
    public void testAuthenticateUser() throws Exception {
        assertTrue("User exists", DatabaseController.authenticateUser("username","password"));
    }

    @Test
    public void testUpdatePassword() throws Exception {


    }

    @Test
    public void testUpdateRealName() throws Exception {

    }

    @Test
    public void testDeleteAccount() throws Exception {
        DatabaseController.deleteAccount("username");
        assertFalse("User account no longer exists", DatabaseController.authenticateUser("username","password"));
    }

    @Test
    public void testGetScore() throws Exception {

    }

    @Test
    public void testSetScore() throws Exception {


    }

    @Test
    public void testGetTopUsers() throws Exception {


    }

    @Test
    public void testGetScores() throws Exception {


    }

    @Test
    public void testIncrementGamesPlayed() throws Exception {
        int gamesPlayed = DatabaseController.getGamesPlayed("username");
        assertEquals("Number of games played is set to 0 upon user creation",gamesPlayed,DatabaseController.getGamesPlayed("username"));
        DatabaseController.incrementGamesPlayed("username");
        assertEquals("Games played is incremented by 1 when method is called", gamesPlayed+1 , DatabaseController.getGamesPlayed("username"));
    }

}