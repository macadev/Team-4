package GamePlay;

import GameObject.*;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-11-01.
 */
public class Spawner {

    private ArrayList<ConcreteWall> concreteWalls;
    private TileMap tileMap;
    //private ArrayList<BrickWall> brickWalls;
    //private <Enemy> enemies;

    public Spawner() {
        concreteWalls = new ArrayList<ConcreteWall>();
        tileMap = new TileMap();
    }

    public ArrayList<ConcreteWall> generateConcreteWalls() {

        for (int i = 0; i < tileMap.NUM_OF_COLS; i++) {
            //Generate top row of tiles
            concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 0, true));

            //Generate bottom row of tiles
            concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE,
                    tileMap.HEIGHT_OF_TILE * (tileMap.NUM_OF_ROWS - 1), true));

            //Generate first column of tiles
            if (i >= 2 && i <= 12)
                concreteWalls.add(new ConcreteWall(0, i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, true));

            //Generate last column of tiles
            if (i >= 2 && i <= 12)
                concreteWalls.add(new ConcreteWall(tileMap.TOTAL_WIDTH_OF_COLUMNS - tileMap.WIDTH_OF_TILE,
                        i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, true));

            //Generate alternating tiles present inside the grid
            if (i % 2 == 1) {
                concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 2 * tileMap.WIDTH_OF_TILE, true));
                concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 4 * tileMap.WIDTH_OF_TILE, true));
                concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 6 * tileMap.WIDTH_OF_TILE, true));
                concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 8 * tileMap.WIDTH_OF_TILE, true));
                concreteWalls.add(new ConcreteWall(i * tileMap.WIDTH_OF_TILE - tileMap.WIDTH_OF_TILE, 10 * tileMap.WIDTH_OF_TILE, true));
            }

        }

        return concreteWalls;
    }

    public void generateBrickWalls() {

    }

    public void generateEnemies() {

    }

    public void generateSetOfHarderEnemies() {

    }

}
