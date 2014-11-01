package GameObject;

import GamePlay.GamePlayState;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-10-31.
 */
public class Player extends MovableObject{

    private int score;
    private GamePlayState currentState;

    public Player(int posX, int posY, boolean visible, int speed) {
        this.currentState = GamePlayState.INGAME;
        this.deltaX = 0;
        this.deltaY = 0;
        this.posX = posX;
        this.posY = posY;
        this.visible = visible;
        this.speed = speed;
        this.image = new ImageIcon(this.getClass().getResource("../resources/enemyTest.png")).getImage();
        System.out.println(this.image.getHeight(null));
    }

    public void keyPressed(int key) {

        if (key == KeyEvent.VK_UP) {
            deltaY = -speed;
        } else if (key == KeyEvent.VK_DOWN) {
            deltaY = speed;
        } else if (key == KeyEvent.VK_LEFT) {
            deltaX = -speed;
        } else if (key == KeyEvent.VK_RIGHT) {
            deltaX = speed;
        } else if (key == KeyEvent.VK_SPACE) {

            if (currentState == GamePlayState.INGAME) {
                currentState = GamePlayState.PAUSE;
            } else {
                currentState = GamePlayState.INGAME;
            }
        }

    }

    public void keyReleased(int key) {

        if (key == KeyEvent.VK_LEFT) {
            deltaX = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
            deltaX = 0;
        } else if (key == KeyEvent.VK_UP) {
            deltaY = 0;
        } else if (key == KeyEvent.VK_DOWN) {
            deltaY = 0;
        }
    }

    public GamePlayState getCurrentGamePlayState() {
        return this.currentState;
    }

}
