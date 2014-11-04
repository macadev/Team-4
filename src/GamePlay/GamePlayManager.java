package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import GameObject.*;
import SystemController.GameState;
import SystemController.GameStateManager;

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
        this.player = new Player(35, 35, true, 3);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
        this.camera = new Camera(player.getPosX(), player);
    }

    @Override
    public void draw(Graphics2D g) {
        GamePlayState currentState = player.getCurrentGamePlayState();

        if (currentState == GamePlayState.PAUSE) {

            //This section is just for the demo.
            //TODO: remove after demo
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            g.setFont(titleFont);
            g.setPaint(new Color(255,255,255));
            g.drawString("Game Paused", 80, 70);

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
                player.draw(g);
                player.drawBombs(g);
                tileMap.drawBlocks(g);
                g.translate(-camera.getPosX(), 0);
            } else {
                if (secondCameraRegion) {
                    g.translate(-(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE), 0);
                    player.draw(g);
                    player.drawBombs(g);
                    tileMap.drawBlocks(g);
                    g.translate(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE, 0);
                } else {
                    player.draw(g);
                    player.drawBombs(g);
                    tileMap.drawBlocks(g);
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
        StaticObject[][] walls = tileMap.getWalls();
        ArrayList<Bomb> bombsPlaced = player.getBombsPlaced();

        for (StaticObject[] row : walls) {
            for (StaticObject wall : row) {
                if (wall != null) {
                    Rectangle wallRectangle = wall.getBounds();
                    if (playerRectangle.intersects(wallRectangle)) {

                        int playerPosX = player.getPosX();
                        int playerPosY = player.getPosY();
                        int wallPosX = wall.getPosX();
                        int wallPosY = wall.getPosY();

                        if (playerPosY <= wallPosY && (playerPosX >= wallPosX && playerPosX <= (wallPosX + 32))) {
                            //Collision on top of box
                            player.restorePreviousYPosition();
                        } else if (playerPosY >= wallPosY /*migh want to check this twice*/  && (playerPosX >= wallPosX && playerPosX <= wallPosX + 32)) {
                            //Collision on bottom of box
                            player.restorePreviousYPosition();
                        } else if (playerPosY >= wallPosY && playerPosY <= (wallPosY + 32) && (playerPosX >= wallPosX)) {
                            //Collision on right side of box
                            player.restorePreviousXPosition();
                        } else {
                            //collision on left side of box
                            player.restorePreviousXPosition();
                        }

                        //player.restorePreviousPosition();
                    }
                }
            }
        }

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
    public void update() {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }
}
