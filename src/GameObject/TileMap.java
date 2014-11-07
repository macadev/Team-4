package GameObject;

import GamePlay.Spawner;
import SystemController.GameController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-10-29.
 */
public class TileMap {

    public static final int TOTAL_WIDTH_OF_COLUMNS = 992;
    public static final int TOTAL_HEIGHT_OF_ROWS = 416;
    public static final int NUM_OF_COLS = 31;
    public static final int NUM_OF_ROWS = 13;
    public static final int WIDTH_OF_TILE = 32;
    public static final int HEIGHT_OF_TILE = 32;
    public static final int CAMERA_MOVING_LIMIT = 224;

    private StaticObject[][] walls;
    private Spawner spawner;
    private int speed;

    private int deltaX;

    public TileMap() {}

    public TileMap(int speed) {
        this.deltaX = 0;
        this.speed = speed;
        this.spawner = new Spawner();
        populateGridWithBlocks();
    }

    public void drawBlocks(Graphics2D g) {
        for (StaticObject[] row : walls) {
            for (StaticObject wall : row) {
                if (wall != null)
                    wall.draw(g);
            }
        }
    }

    public void populateGridWithBlocks() {
        walls = spawner.generateWalls();
    }


    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT) {
            deltaX = speed;
        } else if (k == KeyEvent.VK_RIGHT) {
            deltaX = - speed;
        }
    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            deltaX = 0;
        } else if (k == KeyEvent.VK_RIGHT) {
            deltaX = 0;
        }
    }

    public int getDeltaX() {
        return deltaX;
    }

    public StaticObject[][] getWalls() {
        return walls;
    }

}
