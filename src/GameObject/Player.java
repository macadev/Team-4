package GameObject;

import GamePlay.GamePlayState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-10-31.
 */
public class Player extends MovableObject {

    private int score;
    private GamePlayState currentState;

    public Player(int posX, int posY, boolean visible, int speed) {
        this.currentState = GamePlayState.INGAME;
        this.deltaX = 0;
        this.deltaY = 0;
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = visible;
        this.speed = speed;
        this.image = new ImageIcon(this.getClass().getResource("../resources/bomberman3.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, posX, posY, null);
    }

    public void restorePreviousPosition() {
        posX = previousX;
        posY = previousY;
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
