package GameObject;

import Menu.MenuManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileMapTest {
    private TileMap tileMap;
    private Player player;
    private String userName;

    @Before
    public void Setup(){
        player = new Player(35, 35, true, MovableObject.NORMALSPEED);
        tileMap = new TileMap(player,1, userName);

    }

    @Test
    public void testDetermineIfShouldSpawnHarderEnemies() throws Exception {

    }

    @Test
    public void testCountDownToHarderEnemySetSpawn() throws Exception {

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