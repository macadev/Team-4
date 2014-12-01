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

        assertEquals("Walls were not properly generated",13, grid[0].length);
        assertEquals("Walls were not properly generated",31, grid.length);
    }

    /**
     * Tests if concrete walls are created on the proper locations.
     * @throws Exception
     */
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

    /**
     * Tests if the brickwalls are not created in bonus levels.
     * @throws Exception
     */
    @Test
    public void testGenerateBrickWalls() throws Exception {

        normalSpawner.generateBrickWalls();
        assertTrue("possibleEnemyCoordinates should not be empty " +
                "after calling generateConcreteWalls", normalSpawner.getPossibleEnemyCoordinates().size() > 0);
        assertTrue("possiblePowerUpAndDoorCoordinates should not be empty " +
                "after calling generateConcreteWalls", normalSpawner.getPossiblePowerUpAndDoorCoordinates().size() > 0);

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

    /**
     * Tests if enemies are generated at the proper location, in proper amount.
     * @throws Exception
     */
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

        assertEquals("8 enemies should be generated", 8, enemies.size());
        assertTrue("Enemy was generated on the wrong tile", correctLayout);
    }

    /**
     * Tests if harder enemies are generated with the right type.
     * @throws Exception
     */
    @Test
    public void testCreateSetOfHarderEnemies() throws Exception {
        ArrayList<Enemy> enemies = normalSpawner.createSetOfHarderEnemies(EnemyType.BALLOOM, 0, 0);
        assertEquals(8, enemies.size());

        for (Enemy enemy : enemies) {
            assertEquals("Enemy generated is of the wrong type", true, enemy.getEnemyType().equals(EnemyType.BALLOOM));
        }
    }
    /**
     * Tests if harder enemies are generated with the right type, and amount.
     * @throws Exception
     */
    @Test
    public void testCreateSetOfHardEnemiesAtRandomPositions() throws Exception {
        ArrayList<Enemy> enemies = normalSpawner.createSetOfHarderEnemies(EnemyType.BALLOOM, 0, 0);
        assertEquals("The number of generated enemies is incorrect"8, enemies.size());

        for (Enemy enemy : enemies) {
            assertEquals("Enemy generated is of the wrong type", true, enemy.getEnemyType().equals(EnemyType.BALLOOM));
        }
    }

    /**
     * Tests if the proper enemies are created for bonus stages
     * @throws Exception
     */
    @Test
    public void testCreateBonusStageEnemy() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assert("There are non-bonus enemies", normalSpawner.createBonusStageEnemy() != null);
        assertTrue("There non-bonus enemies",bonusSpawner.createBonusStageEnemy() != null);
    }

    @Test
    public void testGeneratePowerUp() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assertNull("There should be no powerUp",bonusSpawner.generatePowerUp());
        assertTrue("There should be a powerUp", normalSpawner.generatePowerUp() != null);
    }

    /**
     * Tests if a door is generated for normal and bonus levels
     * @throws Exception
     */
    @Test
    public void testGenerateDoor() throws Exception {
        bonusSpawner.generateWalls();
        normalSpawner.generateWalls();
        assertNull("There should be no door", bonusSpawner.generateDoor());
        assertTrue("There should be a door", normalSpawner.generateDoor() != null);
    }

    /**
     * Tests if possibleEnemyCoordinates is empty after calling clearPossibleCoordinates.
     * Tests if possiblePowerUpAndDoorCoordinates is empty after calling generateConcreteWalls.
     * @throws Exception
     */
    @Test
    public void testClearPossibleCoordinates() throws Exception {

        // Populates the possibleEnemyCoordinates and possiblePowerUpAndDoorCoordinates
        // ArrayLists.
        normalSpawner.generateBrickWalls();
        normalSpawner.clearPossibleCoordinates();
        assertTrue("possibleEnemyCoordinates be empty after calling clearPossibleCoordinates",
                normalSpawner.getPossibleEnemyCoordinates().size() == 0);
        assertTrue("possiblePowerUpAndDoorCoordinates should be empty " +
                "after calling generateConcreteWalls", normalSpawner.getPossiblePowerUpAndDoorCoordinates().size() == 0);
    }

    /**
     * Tests if normalSpawner returns valid spawn coordinates.
     * @throws Exception
     */
    @Test
    public void testIsInValidPosition() throws Exception {
        assertEquals(normalSpawner.isNotInSpawnArea(0, 0), true);
        assertEquals(normalSpawner.isNotInSpawnArea(2, 1), false);
        assertEquals(normalSpawner.isNotInSpawnArea(3, 0), false);
        assertEquals(normalSpawner.isNotInSpawnArea(1, 2), false);
        assertEquals(normalSpawner.isNotInSpawnArea(0, 3), false);
        assertEquals(normalSpawner.isNotInSpawnArea(0, 2), false);
        assertEquals(normalSpawner.isNotInSpawnArea(1, 1), false);
        assertEquals(normalSpawner.isNotInSpawnArea(5, 2), true);
        assertEquals(normalSpawner.isNotInSpawnArea(3, 1), true);
    }

}