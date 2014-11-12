package GamePlay;

import GameObject.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by danielmacario on 14-11-01.
 */
public class Spawner {

    private Random randomGenerator = new Random();
    private ArrayList<Coordinate> possibleEnemyCoordinates;
    private TileMap tileMap;

    private GameObject[][] gridLayout;

    //private ArrayList<BrickWall> brickWalls;
    //private <Enemy> enemies;

    public Spawner() {
        int numRows = 13;
        int numCols = 31;
        gridLayout = new GameObject[numCols][numRows];
        tileMap = new TileMap();
        possibleEnemyCoordinates = new ArrayList<Coordinate>();
    }

    public GameObject[][] generateWalls() {

        StageData stageData = tileMap.getCurrentStage();
        generateConcreteWalls();
        generateBrickWalls();
        generateEnemies(stageData.getEnemiesPresent());

        return gridLayout;
    }

    public void generateConcreteWalls() {

        for (int col = 0; col < tileMap.NUM_OF_COLS; col++) {

            for (int row = 0; row < tileMap.NUM_OF_ROWS; row++) {

                //Generate top row of tiles
                if (row == 0) gridLayout[col][row] = new ConcreteWall(col * tileMap.WIDTH_OF_TILE, 0);

                //Generate bottom row of tiles
                if (row == 12) gridLayout[col][row] = new ConcreteWall(col * tileMap.WIDTH_OF_TILE,
                        tileMap.HEIGHT_OF_TILE * (row));
                //Generate first column of tiles
                if (col == 0 && row >= 1 && row < 12)
                    gridLayout[col][row] = new ConcreteWall(col, row * tileMap.HEIGHT_OF_TILE);

                //Generate last column of tiles
                if (col == 30 && row >= 1 && row < 12)
                    gridLayout[col][row] = new ConcreteWall(col * tileMap.WIDTH_OF_TILE, row * tileMap.HEIGHT_OF_TILE);

                //Generate alternating tiles present inside the grid
                if (row >= 2 && row <= 10 && (row % 2) == 0 && col >= 2 && col <= 28 && (col % 2) == 0) {
                    gridLayout[col][row] = new ConcreteWall(col * tileMap.WIDTH_OF_TILE, row * tileMap.WIDTH_OF_TILE);
                }
            }
        }
    }

    public void generateBrickWalls() {

        int bricksLeft = 30;
        int randomRow;
        int randomCol;

        for (int col = 0; col < tileMap.NUM_OF_COLS; col++) {

            for (int row = 0; row < tileMap.NUM_OF_ROWS; row++) {

                boolean isPositionNull = (gridLayout[col][row] == null);

                if (isPositionNull && getRandomBoolean() && isInValidPosition(row, col)) {
                    gridLayout[col][row] = new BrickWall(col * tileMap.WIDTH_OF_TILE, row * tileMap.HEIGHT_OF_TILE, true, false);
                } else if (isPositionNull) {
                    possibleEnemyCoordinates.add(new Coordinate(row, col));
                }
            }
        }
    }

    public void generateEnemies(Tuple[] enemiesPresent) {

        for (Tuple enemySet : enemiesPresent) {

            EnemyType currentSetType = enemySet.getEnemyType();
            int setSize = enemySet.getNumberPresent();
            Coordinate positionOnGrid;

            for (int i = 0; i < setSize; i++) {
                positionOnGrid = getRandomEnemyCoordinate();
                int row = positionOnGrid.getRow();
                int col = positionOnGrid.getCol();

                gridLayout[col][row] = new Enemy(currentSetType, col * tileMap.WIDTH_OF_TILE, row * tileMap.HEIGHT_OF_TILE);
            }

        }

    }

    public Coordinate getRandomEnemyCoordinate() {
        int index = randomGenerator.nextInt(possibleEnemyCoordinates.size());
        Coordinate coordinate = possibleEnemyCoordinates.remove(index);
        return coordinate;
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
        return randomGenerator.nextFloat() <= 0.1;
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

    public void generateSetOfHarderEnemies() {

    }

}
