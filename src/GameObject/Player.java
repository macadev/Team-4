package GameObject;

import GamePlay.GamePlayState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-10-31.
 */
public class Player extends MovableObject {

    private int score;
    ArrayList<Bomb> bombsPlaced;
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
        this.image = new ImageIcon(this.getClass().getResource("../resources/bomberman5.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.bombsPlaced = new ArrayList<Bomb>();
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, posX, posY, null);
    }

    public void drawBombs(Graphics2D g) {
        if (bombsPlaced.isEmpty()) return;
        for (Bomb bomb : bombsPlaced) {
            if (!bomb.isVisible()) bombsPlaced.remove(bomb);
            else bomb.draw(g);
        }
    }

    public void timeBombDetonation() {
        if (bombsPlaced.isEmpty()) return;
        for (Bomb bomb : bombsPlaced) {
            bomb.detonationCountDown();
        }
    }

    public void restorePreviousPosition() {
        posX = previousX;
        posY = previousY;
    }

    private void placeBomb() {
        Bomb bomb = new Bomb(posX, posY);
        bombsPlaced.add(bomb);
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

        } else if (key == KeyEvent.VK_X) {
            placeBomb();
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
