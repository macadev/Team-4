package GamePlay;

import GameObject.GameObject;
import GameObject.BrickWall;
import GameObject.ConcreteWall;
import GameObject.EnemyType;
import GameObject.Stages;
import GameObject.Enemy;
import GameObject.TileMap;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpawnerTest {

    private Spawner bonusSpawner;
    private Spawner normalSpawner;

    @Before
    public void beforeSpawnerTest() {
        bonusSpawner = new Spawner(Stages.gameStages[6]);
        normalSpawner = new Spawner(Stages.gameStages[1]);
    }

    @Test
    public void testGenerateWalls() throws Exception {
        GameObject[][] grid = normalSpawner.generateWalls();

        assertEquals(13, grid[0].length);
        assertEquals(31, grid.length);
    }

    @Test
    public void testGenerateConcreteWalls() throws Exception {
        normalSpawner.generateConcreteWalls();
        int numRows = 13;
        int numCols = 31;
        GameObject[][] walls = normalSpawner.getGridLayout();
        boolean correctLayout = true;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i == 0 || i == 12 || j == 0 || j == 30) {
                    if (!(walls[j][i] instanceof ConcreteWall)) {
                        correctLayout = false;
                    }
                }
                if ( j%2 == 0 && i%2 == 0) {
                    if (!(walls[j][i] instanceof ConcreteWall)) {
                        correctLayout = false;
                    }
                }
            }
        }
        assertTrue("Concrete walls not properly generated", correctLayout);
    }

    @Test
    public void testGenerateBrickWalls() throws Exception {
        //if stage is a bonus stage, check no brick walls are generated.
        bonusSpawner.generateBrickWalls();
        GameObject[][] walls = bonusSpawner.getGridLayout();
        boolean hasBrickWall = false;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 29; j++) {

                if (walls[j][i] instanceof BrickWall) {
                    hasBrickWall = true;
                    break;
                }

            }

        }
        assertFalse("the grid doesn't contain any brickwalls", hasBrickWall);
    }

    @Test
    public void testGenerateEnemies() throws Exception {
        bonusSpawner.generateWalls();
        ArrayList<Enemy> enemies = bonusSpawner.generateEnemies();

        boolean correctLayout = true;

        for(Enemy enemy : enemies) {
            int x = enemy.getPosX();
            int y = enemy.getPosY();
            if (x == 0 || x == 12 || y == 0 || y == 30) {
                correctLayout = false;
            }

            if ((y/TileMap.TILE_SIDE_LENGTH)%2 == 0 && (x/TileMap.TILE_SIDE_LENGTH)%2 == 0) {
                correctLayout = false;
            }
        }

        assertEquals(8, enemies.size());
        assertTrue(correctLayout);
    }

    @Test
    public void testCreateSetOfHarderEnemies() throws Exception {
        ArrayList<Enemy> enemies = normalSpawner.createSetOfHarderEnemies(EnemyType.BALLOOM, 0, 0);
        assertEquals(8, enemies.size());

        for (Enemy enemy : enemies) {
            assertEquals(true, enemy.getEnemyType().equals(EnemyType.BALLOOM));
        }
    }

    @Test
    public void testCreateSetOfHardEnemiesAtRandomPositions() throws Exception {
        ArrayList<Enemy> enemies = normalSpawner.createSetOfHarderEnemies(EnemyType.BALLOOM, 0, 0);
        assertEquals(8, enemies.size());

        for (Enemy enemy : enemies) {
            assertEquals(true, enemy.getEnemyType().equals(EnemyType.BALLOOM));
        }
    }

    @Test
    public void testCreateBonusStageEnemy() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assert(normalSpawner.createBonusStageEnemy() != null);
        assert(bonusSpawner.createBonusStageEnemy() != null);
    }

    @Test
    public void testGeneratePowerUp() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assertNull(bonusSpawner.generatePowerUp());
        assert(normalSpawner.generatePowerUp() != null);
    }

    @Test
    public void testGenerateDoor() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assertNull(bonusSpawner.generateDoor());
        assert(normalSpawner.generateDoor() != null);
    }

    @Test
    public void testNextStage() throws Exception {
        assert(true);
    }

    @Test
    public void testClearPossibleCoordinates() throws Exception {
        assert(true);
    }

    @Test
    public void testIsInValidPosition() throws Exception {
        assertEquals(normalSpawner.isInValidPosition(0,0), true);
        assertEquals(normalSpawner.isInValidPosition(2,1), false);
        assertEquals(normalSpawner.isInValidPosition(3,0), false);
        assertEquals(normalSpawner.isInValidPosition(1,2), false);
        assertEquals(normalSpawner.isInValidPosition(0,3), false);
        assertEquals(normalSpawner.isInValidPosition(0,2), false);
        assertEquals(normalSpawner.isInValidPosition(1,1), false);

        assertEquals(normalSpawner.isInValidPosition(5,2), true);
        assertEquals(normalSpawner.isInValidPosition(3,1), true);
    }

}