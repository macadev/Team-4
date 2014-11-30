package GameObject;

import Menu.MenuManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TileMapTest {
    private TileMap tileMap;
    private Player player;
    private String userName;
    private ArrayList<Enemy> enemies;

    @Before
    public void Setup(){
        player = new Player(35, 35, true, MovableObject.NORMALSPEED);
        tileMap = new TileMap(player,1, userName);

    }

    @Test
    public void testDetermineIfShouldSpawnHarderEnemies() throws Exception {
        tileMap.setSpawnHarderSet(true);
        tileMap.setTimeToHarderSet(2);
        tileMap.determineIfShouldSpawnHarderEnemies();
        assertEquals("timeToHarderSet should be 1 after determineIfShouldSpawnHarderEnemies() is called",
                1, tileMap.getTimeToHarderSet());

        tileMap.setSpawnHarderSet(true);
        tileMap.setTimeToHarderSet(1);
        tileMap.determineIfShouldSpawnHarderEnemies();
        assertEquals("timeToHarderSet should be rolled back to 20 since timeToHarderSet hits 0 after " +
                "determineIfShouldSpawnHarderEnemies() is called ",20,tileMap.getTimeToHarderSet());
        assertFalse("spawnHarderSet is set to false after determineIfShouldSpawnHarderEnemies() is called since " +
                "timeToHarderSet hits 0  ", tileMap.isSpawnHarderSet());


    }

    @Test
    public void testCountDownToHarderEnemySetSpawn() throws Exception {
        tileMap.setHarderSetAlreadyCreated(true);
        tileMap.setTimeToHarderSet(1);
        tileMap.setTimeToHarderSetSpawn(1);
        tileMap.countDownToHarderEnemySetSpawn();
        assertEquals("The timeToHarderSetSpawn should be 0 after countDownToHarderEnemySetSpawn() is called ",
                    0, tileMap.getTimeToHarderSetSpawn());

        tileMap.setTimeToHarderSetSpawn(0);
        tileMap.setHarderSetAlreadyCreated(false);
        tileMap.setSpawnHarderSet(false);
        tileMap.countDownToHarderEnemySetSpawn();
        assertTrue("The spawnHarderSet should be true after countDownToHarderEnemySetSpawn() is called as " +
                "timeToHarderSetSpawn is equals to 0", tileMap.isSpawnHarderSet());
        assertTrue("The harderSetAlreadyCreated should be true after countDownToHarderEnemySetSpawn() is called as " +
                "timeToHarderSetSpawn is equals to 0", tileMap.isHarderSetAlreadyCreated());
    }

    @Test
    public void testSpawnSetOfHarderEnemies() throws Exception {
        
    }

    @Test
    public void testIncrementBombRadius() throws Exception {
        tileMap.setBombRadius(5);
        tileMap.incrementBombRadius();
        assertEquals("Bomb radius should be 6 after calling incrementBombRadius();", 6, tileMap.getBombRadius());
    }
}