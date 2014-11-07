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
    private ArrayList<Flame> flames;
    private int speed;
    private int bombRadius;

    private int deltaX;

    public TileMap() {}

    public TileMap(int speed) {
        this.deltaX = 0;
        this.speed = speed;
        this.spawner = new Spawner();
        this.flames = new ArrayList<Flame>();
        this.bombRadius = 2;
        populateGridWithBlocks();
    }

    public void drawTiles(Graphics2D g) {
        for (StaticObject[] row : walls) {
            for (StaticObject wall : row) {
                if (wall != null)
                    wall.draw(g);
            }
        }

        for (Flame flame : flames) {
            flame.draw(g);
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

    public void addFlames(int posX, int posY) {

        int posXOfExplosion = posX / 32;
        int posYOfExplosion = posY / 32;

        //Place a flame object at the center of the explosion
        flames.add(new Flame(posXOfExplosion * 32, posYOfExplosion * 32, true));



        boolean hasConcreteWallNorth = false;
        boolean hasConcreteWallSouth = false;
        boolean hasConcreteWallEast = false;
        boolean hasConcreteWallWest = false;
        boolean isConcreteWall;
        for (int i = 1; i < bombRadius + 1; i++) {

            if (!hasConcreteWallEast) {

                isConcreteWall = ((walls[posXOfExplosion + i][posYOfExplosion]) instanceof ConcreteWall);
                if (!isConcreteWall) {
                    flames.add(new Flame((posXOfExplosion + i) * 32, (posYOfExplosion) * 32, true));
                } else if (isConcreteWall) {
                    hasConcreteWallEast = true;
                }
            }

            if (!hasConcreteWallWest) {

                isConcreteWall = (walls[posXOfExplosion - i][posYOfExplosion] instanceof ConcreteWall);
                if (!isConcreteWall) {
                    flames.add(new Flame((posXOfExplosion - i) * 32, (posYOfExplosion) * 32, true));
                } else if (isConcreteWall) {
                    hasConcreteWallWest = true;
                }
            }
            //TODO: come up with a better solution that this hack
            if (!hasConcreteWallSouth) {

                isConcreteWall = (walls[posXOfExplosion][posYOfExplosion + i] instanceof ConcreteWall);

                if (!isConcreteWall) {
                    flames.add(new Flame((posXOfExplosion) * 32, (posYOfExplosion + i) * 32, true));
                } else if (isConcreteWall){
                    hasConcreteWallSouth = true;
                }
            }

            if (!hasConcreteWallNorth) {

                isConcreteWall = (walls[posXOfExplosion][posYOfExplosion - i] instanceof ConcreteWall);

                if (!isConcreteWall) {
                    flames.add(new Flame((posXOfExplosion) * 32, (posYOfExplosion - i) * 32, true));
                } else if (isConcreteWall) {
                    hasConcreteWallNorth = true;
                }
            }
        }
    }

    public int getDeltaX() {
        return deltaX;
    }

    public StaticObject[][] getWalls() {
        return walls;
    }

    public ArrayList<Flame> getFlames() {
        return flames;
    }

    public void setFlames(ArrayList<Flame> flames) {
        this.flames = flames;
    }
}
