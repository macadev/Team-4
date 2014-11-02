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
    private boolean cameraMoving;

    //Temporary variables
    //TODO: remove after demo
    private Color titleColor;
    private Font titleFont;

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(35, 35, true, 2);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
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
            updateCamera();

            if (!cameraMoving) {
                player.move();
            } else {
                tileMap.moveBlocks();
                player.moveVertically();
                player.moveVirtualPosition(-tileMap.getDeltaX());
            }

            checkCollisions();
            player.draw(g);
            tileMap.drawBlocks(g);

        }
    }

    public void updateCamera() {
        int virtualX = player.getVirtualX();
        if (virtualX >= tileMap.CAMERA_MOVING_LIMIT && virtualX <= 768) {
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
        if (cameraMoving && (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT))
            tileMap.keyPressed(k);
        else
            this.player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        if (cameraMoving && (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT))
            tileMap.keyReleased(k);
        else
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
