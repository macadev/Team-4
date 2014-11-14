package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import GameObject.*;
import SystemController.GameState;
import SystemController.GameStateManager;
import Menu.MenuState;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GamePlayManager extends GameState implements ActionListener {

    private TileMap tileMap;
    private Player player;
    private Camera camera;
    private boolean cameraMoving;
    private boolean secondCameraRegion;

    //TODO: remove after demo, these are for temporary pause feature
    private Color titleColor;
    private Font titleFont;

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(35, 35, true, MovableObject.NORMALSPEED);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
        this.player.setTileMap(tileMap);
        this.camera = new Camera(player.getPosX(), player);
    }

    @Override
    public void draw(Graphics2D g) {
        GamePlayState currentState = player.getCurrentGamePlayState();

        if (currentState == GamePlayState.PAUSE) {

            gsm.setState(gsm.MENUSTATE, MenuState.INGAME);

        } else if (currentState == GamePlayState.INGAME) {

            //Move the player each time we render again
            //Movement depends on the deltaX and deltaY
            //values of the MovableObject class
            //updateCamera();

            player.move();
            tileMap.moveEnemies(player.getPosX(), player.getPosY(), player.isVisible());

            checkCollisions();
            updateCamera();
            camera.adjustPosition();
            if (cameraMoving) {
                g.translate(camera.getPosX(), 0);
                player.drawBombs(g);
                tileMap.drawObjects(g);
                player.draw(g);
                g.translate(-camera.getPosX(), 0);
            } else {
                if (secondCameraRegion) {
                    g.translate(-(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE), 0);
                    player.drawBombs(g);
                    tileMap.drawObjects(g);
                    player.draw(g);
                    g.translate(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE, 0);
                } else {
                    player.drawBombs(g);
                    tileMap.drawObjects(g);
                    player.draw(g);
                }
            }

        }
    }

    public void updateCamera() {
        int playerPosX = player.getPosX();
        int secondCameraThreshold = 733;
        if (playerPosX > tileMap.CAMERA_MOVING_LIMIT && playerPosX < secondCameraThreshold) {
            cameraMoving = true;
        } else {
            cameraMoving = false;
            secondCameraRegion = false;
            if (playerPosX >= secondCameraThreshold) {
                secondCameraRegion = true;
            }
        }
    }

    public void checkCollisions() {
        Rectangle playerRectangle = player.getBounds();
        GameObject[][] objects = tileMap.getObjects();
        ArrayList<Bomb> bombsPlaced = player.getBombsPlaced();
        ArrayList<Enemy> enemies = tileMap.getEnemies();
        boolean playerCollisionResolved = false;


        //Check for collision of the player and the enemies with walls
        for (GameObject[] row : objects) {
            for (GameObject wall : row) {
                if (wall != null) {

                    boolean playerHasWallPass = player.hasWallPass();
                    Rectangle wallRectangle = wall.getBounds();

                    //Check for collision of the player with the targeted wall
                    //Upon resolving the collision, we stop checking this for loop
                    //To reduce computation
                    if (!playerCollisionResolved) {

                        if (!playerHasWallPass || (playerHasWallPass && (wall instanceof ConcreteWall))) {
                            if (playerRectangle.intersects(wallRectangle)) {
                                restoreToBeforeCollision(player, wallRectangle);
                                playerCollisionResolved = true;
                            }
                        }
                    }

                    //Check for collisions of the enemies with the walls
                    boolean enemyHasWallPass;
                    for (Enemy enemy : enemies) {
                        enemyHasWallPass = enemy.hasWallPass();
                        Rectangle enemyRectangle = enemy.getBounds();
                        if (!enemyHasWallPass || (enemyHasWallPass && (wall instanceof ConcreteWall))) {
                            if (enemyRectangle.intersects(wallRectangle)) {
                                enemy.reverseDirection();
                                restoreToBeforeCollision(enemy, wallRectangle);
                            }
                        }
                    }
                }
            }
        }

        //Check for collisions of the player with the bombs
        for (Bomb bomb : bombsPlaced) {
            Rectangle bombRectangle = bomb.getBounds();
            if (playerRectangle.intersects(bombRectangle)) {
                if (!bomb.isFirstCollision()) {
                    player.restorePreviousPosition();
                }
            } else {
                bomb.setFirstCollision(false);
            }

            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (enemyRectangle.intersects(bombRectangle)) {
                    enemy.reverseDirection();
                    restoreToBeforeCollision(enemy, bombRectangle);
                }
            }

        }


        //Check for collision between player/bombs/enemies with flames
        ArrayList<Flame> flames = tileMap.getFlames();
        Rectangle flameRectangle;
        for (Flame flame : flames) {
            flameRectangle = flame.getBounds();

            if(!player.hasFlamePass()) {
                if (playerRectangle.intersects(flameRectangle)) {
                    player.death();
                }
            }

            for (Bomb bomb : bombsPlaced) {
                Rectangle bombRectangle = bomb.getBounds();
                if (flameRectangle.intersects(bombRectangle)) {
                    bomb.setVisible(false);
                }
            }

            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (enemyRectangle.intersects(flameRectangle)) {
                    enemy.death();
                    player.addToScore(enemy.getScore());
                }
            }

        }


        //check for collisions between the player and the enemies
        if (player.isVisible()) {
            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (playerRectangle.intersects(enemyRectangle)) {
                    player.death();
                }

            }
        }



    }

    public void restoreToBeforeCollision(MovableObject object, Rectangle wallRectangle) {
        int x = object.getPosX();
        int y = object.getPosY();

        object.restorePreviousXPosition();

        if (object.getBounds().intersects(wallRectangle)) {
            object.restorePositionTo(x,y);
        } else {
            return;
        }

        object.restorePreviousYPosition();

        if (object.getBounds().intersects(wallRectangle)) {
            object.restorePositionTo(x,y);
        } else {
            return;
        }

        object.restorePreviousPosition();
        return;
    }

    @Override
    public void keyPressed(int k) {
        this.player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        this.player.keyReleased(k);
    }

    @Override
    public void init() {

        //Populate the blocks arrayLists present in the game;
        //populateGridWithBlocks();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }
}
