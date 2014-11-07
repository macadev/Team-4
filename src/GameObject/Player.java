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
    private int bombsAllowed;
    ArrayList<Bomb> bombsPlaced;
    private GamePlayState currentState;
    private boolean wallPass;
    private TileMap tileMap;

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
        this.bombsAllowed = 1;
        this.wallPass = false;
    }

    public void drawBombs(Graphics2D g) {
        if (bombsPlaced.isEmpty()) return;
        int length = bombsPlaced.size();
        for (int i = 0; i < length; i ++) {
            Bomb bomb = bombsPlaced.get(i);
            synchronized (bomb) {
                if (!bomb.isVisible()) {
                    tileMap.addFlames(bomb.getPosX(), bomb.getPosY());
                    bombsPlaced.remove(bomb);
                    length = bombsPlaced.size();
                }
                else bomb.draw(g);
            }
        }
    }

    public void restorePreviousPosition() {
        posX = previousX;
        posY = previousY;
    }

    public void restorePreviousXPosition() {
        posX = previousX;
    }

    public void restorePreviousYPosition() {
        posY = previousY;
    }

    public void restorePositionTo(int x, int y) {
        posX = x;
        posY = y;
    }

    private void placeBomb() {
        if (bombsPlaced.size() < bombsAllowed) {
            //The % allows the bombs to snap to the center of the tiles where they are placed
            Bomb bomb = new Bomb(posX - posX % 32, posY - posY % 32);
            bombsPlaced.add(bomb);
        }
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

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public ArrayList<Bomb> getBombsPlaced() {
        return bombsPlaced;
    }

    public void setBombsPlaced(ArrayList<Bomb> bombsPlaced) {
        this.bombsPlaced = bombsPlaced;
    }

    public GamePlayState getCurrentGamePlayState() {
        return this.currentState;
    }

    public int getBombsAllowed() {
        return bombsAllowed;
    }

    public void setBombsAllowed(int bombsAllowed) {
        this.bombsAllowed = bombsAllowed;
    }
}
