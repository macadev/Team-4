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
            checkCollisions();
            updateCamera();
            camera.adjustPosition();
            if (cameraMoving) {
                g.translate(camera.getPosX(), 0);
                player.drawBombs(g);
                tileMap.drawTiles(g);
                player.draw(g);
                g.translate(-camera.getPosX(), 0);
            } else {
                if (secondCameraRegion) {
                    g.translate(-(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE), 0);
                    player.drawBombs(g);
                    tileMap.drawTiles(g);
                    player.draw(g);
                    g.translate(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE, 0);
                } else {
                    player.drawBombs(g);
                    tileMap.drawTiles(g);
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
        GameObject[][] walls = tileMap.getWalls();
        ArrayList<Bomb> bombsPlaced = player.getBombsPlaced();



        for (GameObject[] row : walls) {
            for (GameObject wall : row) {
                if (wall != null) {

                    boolean playerHasWallPass = player.hasWallPass();

                    if (!playerHasWallPass || (playerHasWallPass && (wall instanceof ConcreteWall))) {
                        Rectangle wallRectangle = wall.getBounds();
                        if (playerRectangle.intersects(wallRectangle)) {

                            int x = player.getPosX();
                            int y = player.getPosY();

                            player.restorePreviousXPosition();

                            if (player.getBounds().intersects(wallRectangle)) {
                                player.restorePositionTo(x,y);
                            } else {
                                break;
                            }

                            player.restorePreviousYPosition();

                            if (player.getBounds().intersects(wallRectangle)) {
                                player.restorePositionTo(x,y);
                            } else {
                                break;
                            }

                            player.restorePreviousPosition();
                            break;
                        }
                    }
                }
            }
        }


        if (!player.hasBombPass()) {
            for (Bomb bomb : bombsPlaced) {
                Rectangle bombRectangle = bomb.getBounds();
                if (playerRectangle.intersects(bombRectangle)) {
                    if (!bomb.isFirstCollision()) {
                        player.restorePreviousPosition();
                    }
                } else {
                    bomb.setFirstCollision(false);
                }
            }
        }

        if(!player.hasFlamePass()) {
            ArrayList<Flame> flames = tileMap.getFlames();
            Rectangle flameRectangle;
            for (Flame flame : flames) {
                flameRectangle = flame.getBounds();
                if (playerRectangle.intersects(flameRectangle)) {
                    player.death();
                }
                for (Bomb bomb : bombsPlaced) {
                    Rectangle bombRectangle = bomb.getBounds();
                    if (flameRectangle.intersects(bombRectangle)) {
                        bomb.setVisible(false);
                    }
                }
            }
        }

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
