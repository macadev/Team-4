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

    }

    @Test
    public void testGenerateConcreteWalls() throws Exception {
        normalSpawner.generateConcreteWalls();
        GameObject[][] walls = normalSpawner.getGridLayout();

        boolean correctLayout = true;

        // iterate over the grid checking that all the tiles that should be concrete
        // walls are in fact concrete walls. Note that their position is constant for all
        // the stages of the game.
        for (int row = 0; row < TileMap.NUM_OF_ROWS; row++) {
            for (int col = 0; col < TileMap.NUM_OF_COLS; col++) {
                if (row == 0 || row == 12 || col == 0 || col == 30) {
                    if (!(walls[col][row] instanceof ConcreteWall)) {
                        correctLayout = false;
                    }
                }
                if (col % 2 == 0 && row % 2 == 0) {
                    if (!(walls[col][row] instanceof ConcreteWall)) {
                        correctLayout = false;
                    }
                }
            }
        }
        assertTrue("The concrete walls should be at their defined constant positions", correctLayout);
    }

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
        assertFalse("If the stage is a bonus stage, then generateBrickWalls should not create any brick walls", hasBrickWall);
    }

    @Test
    public void testGenerateEnemies() throws Exception {
        bonusSpawner.generateWalls();
        ArrayList<Enemy> enemies = bonusSpawner.generateEnemies();

        boolean enemiesAreAllTheSameType = true;

        EnemyType uniqueType = enemies.get(0).getEnemyType();
        for(Enemy enemy : enemies) {
            if (!(enemy.getEnemyType() == uniqueType)) {
                enemiesAreAllTheSameType = false;
            }
        }

        assertTrue("For a bonus stage, all the enemies created should be of the same type", enemiesAreAllTheSameType);

        normalSpawner.generateWalls();
        enemies = normalSpawner.generateEnemies();

        assertTrue("The spawner should generate the amount of enemies specified in the StageData object, " +
                "for stage one it should generate 6 enemies", enemies.size() == 6);

        boolean specificTypeIsCorrect = true;
        for(Enemy enemy : enemies) {
            if ((enemy.getEnemyType() != EnemyType.BALLOOM)) {
                specificTypeIsCorrect = false;
            }
        }

        assertTrue("The spawner should generate the type of enemies specified in the StageData object, " +
                "for stage one it should generate only ballooms", specificTypeIsCorrect);

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

    }

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