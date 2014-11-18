/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

import GamePlay.GamePlayState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Used to represent the Player object on the Game Grid. The Player class inherits most of its
 * functionality from MovableObject, but it also implements the logic that enables the user to
 * interact with GamePlay. Specifically, the player class defines the logic for: dropping bombs,
 * activating powerups, and detonating bombs. It is also the final link in the keyPressed and
 * keyReleased chain during the GamePlay state, which starts with the GameStateManager.
 */
public class Player extends MovableObject {
    private GamePlayState currentState;
    private TileMap tileMap;
    ArrayList<Bomb> bombsPlaced;
    private int respawnCount = 0;
    private int livesRemaining;

    //powerup logic data
    private int bombsAllowed;
    private boolean wallPass;
    private boolean bombPass;
    private boolean flamePass;
    private boolean detonatorEnabled;

    //player constructor
    public Player(int posX, int posY, boolean visible, int speed) {
        this.score = 0;
        this.livesRemaining = 2;
        this.currentState = GamePlayState.INGAME;
        this.deltaX = 0;
        this.deltaY = 0;
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = visible;
        this.speed = speed;
        this.image = new ImageIcon(this.getClass().getResource("../resources/bomberman9.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.bombsPlaced = new ArrayList<Bomb>();
        this.bombsAllowed = 3;
        this.wallPass = false;
        this.bombPass = false;
        this.flamePass = true;
        this.detonatorEnabled = true;
    }

    public void draw(Graphics2D g) {
        if (visible /*not dead*/) {
            g.drawImage(image, posX, posY, null);
        } else {
            countDownToRespawn();
        }
    }

    public void drawBombs(Graphics2D g) {
        if (bombsPlaced.isEmpty()) return;
        int length = bombsPlaced.size();
        for (int i = 0; i < length; i ++) {
            Bomb bomb = bombsPlaced.get(i);

            if (!bomb.isVisible()) {
                tileMap.addFlames(bomb.getPosX(), bomb.getPosY());
                bombsPlaced.remove(bomb);
                length = bombsPlaced.size();
            }
            else bomb.draw(g);

        }
    }

    public void detonateLastBomb() {

        boolean detonated = false;
        int i = 1;
        while (!detonated) {
            if (bombsPlaced.get(bombsPlaced.size()-1).isVisible() == false) {
                i++;
            } else {
                bombsPlaced.get(bombsPlaced.size()-1).setVisible(false);
                detonated = true;
            }
        }
    }

    public void incrementBombRadius() {

        tileMap.incrementBombRadius();

    }

    private void placeBomb() {
        if (bombsPlaced.size() < bombsAllowed) {
            //The % allows the bombs to snap to the center of the tiles where they are placed
            Bomb bomb = new Bomb(posX - posX % 32, posY - posY % 32);
            bombsPlaced.add(bomb);
        }
    }

    public void incrementSpeed() {
        if (speed == NORMALSPEED) {
            speed = FASTSPEED;
        }
    }

    public void death() {
        disablePowerUpsOnDeath();
        this.visible = false;
        decrementLifesRemaining();
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public void countDownToRespawn() {
        if (this.respawnCount == 60) {
            visible = true;
            posX = 35;
            posY = 35;
            respawnCount = 0;
        } else {
            respawnCount++;
        }
    }

    public void decrementLifesRemaining() {
        this.livesRemaining--;
        if (livesRemaining < 0) currentState = GamePlayState.GAMEOVER;
    }

    /**
     * Based on the PowerUpType associated with this instance, we modify gameplay logic according to the specific
     * functionality of each powerUp
     * @param powerUpType
     */
    public void enablePowerUp(PowerUpType powerUpType) {
        switch (powerUpType) {
            case BOMBPASS:
                bombPass = true;
                break;
            case BOMBS:
                bombsAllowed++;
                break;
            case DETONATOR:
                detonatorEnabled = true;
                break;
            case FLAMEPASS:
                flamePass = true;
                break;
            case FLAMES:
                incrementBombRadius();
                break;
            case MYSTERY:
                break;
            case SPEED:
                //TODO: define speed attributes
                incrementSpeed();
                break;
            case WALLPASS:
                wallPass = true;
                break;
        }
    }

    public void disablePowerUpsOnDeath() {
        bombPass = false;
        flamePass = false;
        wallPass = false;
        detonatorEnabled = false;
    }

    public void nextStage() {
        if (tileMap != null) {
            tileMap.nextStage();
        }
        posX = 35;
        posY = 35;
    }

    public void keyPressed(int key) {
        //disable the controls if the player is dead
        if (visible) {
            if (key == KeyEvent.VK_UP) {
                deltaY = -speed;
            } else if (key == KeyEvent.VK_DOWN) {
                deltaY = speed;
            } else if (key == KeyEvent.VK_LEFT) {
                deltaX = -speed;
            } else if (key == KeyEvent.VK_RIGHT) {
                deltaX = speed;
            } else if (key == KeyEvent.VK_SPACE) {
                currentState = GamePlayState.PAUSE;
            } else if (key == KeyEvent.VK_X) {
                placeBomb();
            } else if (key == KeyEvent.VK_Z) {
                if (detonatorEnabled && !bombsPlaced.isEmpty()) {
                    detonateLastBomb();
                }
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

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
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

    public void incrementBombsAllowed() {
        if (bombsAllowed < 10) {
            bombsAllowed++;
        }
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean hasBombPass() {
        return bombPass;
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public boolean isDetonatorEnabled() {
        return detonatorEnabled;
    }

    public void setDetonatorEnabled(boolean detonatorEnabled) {
        this.detonatorEnabled = detonatorEnabled;
    }

    public boolean hasFlamePass() {
        return flamePass;
    }

    public void setFlamePass(boolean flamePass) {
        this.flamePass = flamePass;
    }

    public void addToScore(int enemyScore) {
        this.score += enemyScore;
    }

    public int getLifesRemaining() {
        return livesRemaining;
    }
}
