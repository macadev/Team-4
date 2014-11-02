package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import GameObject.ConcreteWall;
import SystemController.GameState;
import GameObject.TileMap;
import SystemController.GameStateManager;
import GameObject.Player;

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
        this.player = new Player(50, 50, true, 2);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
    }

    public void updateCamera() {
        if (player.getVirtualX() >= tileMap.CAMERA_MOVING_LIMIT) {
            cameraMoving = true;
        } else {
            cameraMoving = false;
        }
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
    public void draw(Graphics2D g) {
        GamePlayState currentState = player.getCurrentGamePlayState();

        if (currentState == GamePlayState.PAUSE) {

            //This section is just for the demo.
            //TODO: remove after demo
            titleColor = new Color(230, 200, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            g.setColor(titleColor);
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
                player.moveVirtualPosition(-tileMap.getDeltaX());
            }

            player.draw(g);
            tileMap.drawBlocks(g);
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
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }
}
