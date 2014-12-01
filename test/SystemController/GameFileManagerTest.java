package SystemController;

import GamePlay.GamePlayManager;
import com.sun.tools.javac.util.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameFileManagerTest {
    GameFileManager fileManager = new GameFileManager("testUser1");
    GameStateManager gsm = new GameStateManager(5);
    GamePlayManager gpm = new GamePlayManager(gsm,5);

    @Before
    public  void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSaveGame() throws Exception {

        GameFileManager.saveDirectory = "testSavedGames/";
        fileManager.saveGame(gpm ,"testUser1");


    }

    @Test
    public void testLoadGame() throws Exception {

    }

    @Test
    public void testSetUserName() throws Exception {

    }
}