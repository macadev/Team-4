package GamePlay;

import GameObject.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by danielmacario on 14-11-01.
 */
public class Spawner {

    private ArrayList<ConcreteWall> concreteWalls;
    private TileMap tileMap;

    private StaticObject[][] gridLayout;

    //private ArrayList<BrickWall> brickWalls;
    //private <Enemy> enemies;

    public Spawner() {
        int numRows = 13;
        int numCols = 31;
        gridLayout = new StaticObject[numCols][numRows];

        tileMap = new TileMap();
        concreteWalls = new ArrayList<ConcreteWall>();
    }

    public StaticObject[][] generateWalls() {

        generateConcreteWalls();
        generateBrickWalls();

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
        while (bricksLeft > 0) {

            randomCol = getRandom(1, 30);
            //TODO: change x = 1, so that the first row can be populated as well
            randomRow = getRandom(1, 12);

            if (gridLayout[randomCol][randomRow] == null) {
                gridLayout[randomCol][randomRow] = new BrickWall(randomCol * tileMap.WIDTH_OF_TILE,
                        randomRow * tileMap.HEIGHT_OF_TILE, true, false);
                bricksLeft--;
            }

        }


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

    public void generateEnemies() {

    }

    public void generateSetOfHarderEnemies() {

    }

}
