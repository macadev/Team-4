package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import GameObject.*;
import SystemController.GameState;
import SystemController.GameStateManager;
import GameObject.ConcreteWall;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GamePlayManager extends GameState implements ActionListener {

    private TileMap tileMap;
    private Player player;
    private Camera camera;
    private boolean cameraMoving;

    //TODO: remove after demo, these are for temporary pause feature
    private Color titleColor;
    private Font titleFont;

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(35, 35, true, 2);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
        this.camera = new Camera(0, 0, player);
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
                tileMap.drawBlocks(g);
                g.translate(-camera.getPosX(), 0);
            } else {
                player.draw(g);
                tileMap.drawBlocks(g);
            }

        }
    }

    public void updateCamera() {
        int playerPosX = player.getPosX();
        if (playerPosX > tileMap.CAMERA_MOVING_LIMIT && playerPosX < 768) {
            cameraMoving = true;
        } else {
            cameraMoving = false;
        }
    }

    public void checkCollisions() {
        Rectangle playerRectangle = player.getBounds();
        StaticObject[][] walls = tileMap.getConcreteWalls();


        for (StaticObject[] row : walls) {
            for (StaticObject wall : row) {
                if (wall != null) {
                    Rectangle wallRectangle = wall.getBounds();
                    if (playerRectangle.intersects(wallRectangle)) {
                        player.restorePreviousPosition();
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
    public void update() {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }
}
