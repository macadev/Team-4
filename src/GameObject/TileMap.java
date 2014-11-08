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
    private ArrayList<Flame> flames;
    private Spawner spawner;
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
                if (wall != null && wall.isVisible())
                    wall.draw(g);
            }
        }

        for (Flame flame : flames) {
            flame.incrementTimeOnGrid();
            if (flame.isVisible()) flame.draw(g);
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

        boolean isConcreteWall;
        boolean isBrickWall;
        int posXofFlame;
        int posYofFlame;
        int posXofWall;
        int posYofWall;
        StaticObject wall;
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : directions) {
            for (int i = 1; i < bombRadius + 1; i++) {

                if (direction == Direction.EAST) {
                    posXofWall = posXOfExplosion + i;
                    posYofWall = posYOfExplosion;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;
                } else if (direction == Direction.WEST) {
                    posXofWall = posXOfExplosion - i;
                    posYofWall = posYOfExplosion;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;
                } else if (direction == Direction.NORTH) {
                    posXofWall = posXOfExplosion;
                    posYofWall = posYOfExplosion - i;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;
                } else {
                    posXofWall = posXOfExplosion;
                    posYofWall = posYOfExplosion + i;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;
                }

                isConcreteWall = wall instanceof ConcreteWall;
                isBrickWall = wall instanceof BrickWall;
                System.out.println(isBrickWall);

                if (isBrickWall || isConcreteWall) {
                    if (isBrickWall) {
                        walls[posXofWall][posYofWall] = null;
                    }
                    break;
                } else if (!isConcreteWall) {
                    flames.add(new Flame(posXofFlame, posYofFlame, true));
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
