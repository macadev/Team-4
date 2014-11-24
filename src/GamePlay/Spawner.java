package GamePlay;

import GameObject.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by danielmacario on 14-11-01.
 */
public class Spawner implements Serializable {

    private Random randomGenerator = new Random();
    private ArrayList<Coordinate> possibleEnemyCoordinates;
    private ArrayList<Coordinate> possiblePowerUpAndDoorCoordinates;
    private StageData stageData;

    private GameObject[][] gridLayout;

    public Spawner(StageData stageData) {
        int numRows = 13;
        int numCols = 31;
        gridLayout = new GameObject[numCols][numRows];
        possibleEnemyCoordinates = new ArrayList<Coordinate>();
        possiblePowerUpAndDoorCoordinates = new ArrayList<Coordinate>();
        this.stageData = stageData;
    }

    public GameObject[][] generateWalls() {
        generateConcreteWalls();
        generateBrickWalls();
        return gridLayout;
    }

    public void generateConcreteWalls() {
        for (int col = 0; col < TileMap.NUM_OF_COLS; col++) {

            for (int row = 0; row < TileMap.NUM_OF_ROWS; row++) {

                //Generate top row of tiles
                if (row == 0) gridLayout[col][row] = new ConcreteWall(col * TileMap.WIDTH_OF_TILE, 0);

                //Generate bottom row of tiles
                if (row == 12) gridLayout[col][row] = new ConcreteWall(col * TileMap.WIDTH_OF_TILE,
                        TileMap.HEIGHT_OF_TILE * (row));
                //Generate first column of tiles
                if (col == 0 && row >= 1 && row < 12)
                    gridLayout[col][row] = new ConcreteWall(col, row * TileMap.HEIGHT_OF_TILE);

                //Generate last column of tiles
                if (col == 30 && row >= 1 && row < 12)
                    gridLayout[col][row] = new ConcreteWall(col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE);

                //Generate alternating tiles present inside the grid
                if (row >= 2 && row <= 10 && (row % 2) == 0 && col >= 2 && col <= 28 && (col % 2) == 0) {
                    gridLayout[col][row] = new ConcreteWall(col * TileMap.WIDTH_OF_TILE, row * TileMap.WIDTH_OF_TILE);
                }
            }
        }
    }

    public void generateBrickWalls() {

        for (int col = 0; col < TileMap.NUM_OF_COLS; col++) {

            for (int row = 0; row < TileMap.NUM_OF_ROWS; row++) {

                boolean isPositionNull = (gridLayout[col][row] == null);

                if (!stageData.isBonusStage() && isPositionNull && getRandomBoolean() && isInValidPosition(row, col)) {
                    gridLayout[col][row] = new BrickWall(col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE, true, false);
                    possiblePowerUpAndDoorCoordinates.add(new Coordinate(row, col));
                } else if (isPositionNull) {
                    possibleEnemyCoordinates.add(new Coordinate(row, col));
                }
            }
        }
    }

    public ArrayList<Enemy> generateEnemies() {
        Coordinate positionOnGrid;
        EnemySet[] enemiesPresent = stageData.getEnemiesPresent();
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        if (stageData.isBonusStage()) {
            EnemyType onlyTypePresent = enemiesPresent[0].getEnemyType();

            for (int i = 0; i < 8; i++) {
                positionOnGrid = getRandomCoordinateFromSet(possibleEnemyCoordinates);
                int row = positionOnGrid.getRow();
                int col = positionOnGrid.getCol();

                enemies.add(new Enemy(onlyTypePresent, col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE));
            }
            return enemies;
        }

        for (EnemySet enemySet : enemiesPresent) {

            EnemyType currentSetType = enemySet.getEnemyType();
            int setSize = enemySet.getNumberPresent();

            for (int i = 0; i < setSize; i++) {
                positionOnGrid = getRandomCoordinateFromSet(possibleEnemyCoordinates);
                int row = positionOnGrid.getRow();
                int col = positionOnGrid.getCol();

                enemies.add(new Enemy(currentSetType, col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE));
            }

        }

        return enemies;
    }

    public ArrayList<Enemy> createSetOfHarderEnemies(EnemyType harderEnemyType, int posX, int posY) {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        for (int i = 0; i < 8; i++) {
            enemies.add(new Enemy(harderEnemyType, posX, posY));
        }
        return enemies;
    }

    public Enemy createBonusStageEnemy() {
        Coordinate positionOnGrid = getRandomCoordinateFromSet(possibleEnemyCoordinates);
        int row = positionOnGrid.getRow();
        int col = positionOnGrid.getCol();

        //In the case of a bonus stage, the enemiesPresent array will only contain one enemySet
        //We retrieve the only type to create new enemies of that same type.
        EnemyType type = stageData.getEnemiesPresent()[0].getEnemyType();
        Enemy bonusEnemy = new Enemy(type, col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE);
        return bonusEnemy;
    }

    public PowerUp generatePowerUp() {

        if (stageData.isBonusStage()) return null;

        PowerUpType powerUpType = stageData.getPowerUpPresent();
        Coordinate positionOnGrid = getRandomCoordinateFromSet(possiblePowerUpAndDoorCoordinates);
        int row = positionOnGrid.getRow();
        int col = positionOnGrid.getCol();
        PowerUp powerUp = new PowerUp(powerUpType, col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE);
        return powerUp;
    }

    public Door generateDoor() {

        if (stageData.isBonusStage()) return null;

        Coordinate positionOnGrid = getRandomCoordinateFromSet(possiblePowerUpAndDoorCoordinates);
        int row = positionOnGrid.getRow();
        int col = positionOnGrid.getCol();
        Door door = new Door(col * TileMap.WIDTH_OF_TILE, row * TileMap.HEIGHT_OF_TILE);
        return door;
    }

    public void nextStage(StageData stageData) {
        this.stageData = stageData;
        clearPossibleCoordinates();
    }

    public Coordinate getRandomCoordinateFromSet(ArrayList<Coordinate> coordinates) {
        int index = randomGenerator.nextInt(coordinates.size());
        Coordinate coordinate = coordinates.remove(index);
        return coordinate;
    }

    public void clearPossibleCoordinates() {
        possibleEnemyCoordinates = new ArrayList<Coordinate>();
        possiblePowerUpAndDoorCoordinates = new ArrayList<Coordinate>();
    }

    /**
     * Returns true if the passed coordinate is not in the area where the player spawns
     * @param row
     * @param col
     * @return
     */
    public boolean isInValidPosition(int row, int col) {
        return (row + col != 3 && row + col != 2);
    }

    public boolean getRandomBoolean() {
        return randomGenerator.nextFloat() <= 0.2;
    }

    /**
     *
     * @param low
     * @param high
     * @return
     */
    public int getRandom(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high - low) + low;

        //number range is low <= x < high
        return result;
    }

}
