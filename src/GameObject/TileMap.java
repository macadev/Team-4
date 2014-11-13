package GameObject;

import GamePlay.Spawner;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
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

    private GameObject[][] walls;
    private ArrayList<Flame> flames;
    private ArrayList<Enemy> enemies;
    private Spawner spawner;
    private int speed;
    private int bombRadius;

    //keep track of the current stage
    private int currentStage;

    private int deltaX;

    public TileMap() {
        this.currentStage = 1;
    }

    public TileMap(int speed) {
        this.currentStage = 1;
        this.deltaX = 0;
        this.speed = speed;
        this.bombRadius = 1;
        this.spawner = new Spawner();
        this.flames = new ArrayList<Flame>();
        populateGridWithBlocks();
        createEnemySet();
    }

    public void drawObjects(Graphics2D g) {
        drawTiles(g);
        drawEnemies(g);
    }

    public void drawTiles(Graphics2D g) {

        //Draw the walls and the enemies
        for (GameObject[] row : walls) {
            for (GameObject object : row) {
                if (object != null && object.isVisible())
                    object.draw(g);
            }
        }

        Flame flame;
        for (Iterator<Flame> iterator = flames.iterator(); iterator.hasNext();) {
            flame = iterator.next();
            flame.incrementTimeOnGrid();
            if (!flame.isVisible()) {
                iterator.remove();
            } else {
                flame.draw(g);
            }
        }
    }

    public void drawEnemies(Graphics2D g) {

        for (Enemy enemy : enemies) {
            if (enemy != null && enemy.isVisible())
                enemy.draw(g);
        }

    }

    public void populateGridWithBlocks() {
        walls = spawner.generateWalls();
    }

    public void createEnemySet() {
        enemies = spawner.generateEnemies();
    }

    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move();
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
        GameObject wall;
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

    public void incrementBombRadius() {
        bombRadius++;
    }

    public StageData getCurrentStage() {

        return Stages.gameStages[this.currentStage];

    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public GameObject[][] getObjects() {
        return walls;
    }

    public ArrayList<Flame> getFlames() {
        return flames;
    }

    public void setFlames(ArrayList<Flame> flames) {
        this.flames = flames;
    }

}
