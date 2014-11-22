/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

import GamePlay.GamePlayState;
import SystemController.SoundController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used to represent the Player object on the Game Grid. The Player class inherits most of its
 * functionality from MovableObject, but it also implements the logic that enables the user to
 * interact with GamePlay. Specifically, the player class defines the logic for: dropping bombs,
 * activating powerups, and detonating bombs. It is also the final link in the keyPressed and
 * keyReleased chain during the GamePlay state, which starts with the GameStateManager.
 */
public class Player extends MovableObject implements Serializable {

    private GamePlayState currentState;
    private TileMap tileMap;
    ArrayList<Bomb> bombsPlaced;
    private int respawnCount = 0;
    private int livesRemaining;

    //powerup logic data
    private int bombsAllowed;
    private boolean bombPass;
    private boolean flamePass;
    private boolean detonatorEnabled;

    /**
     * Initialize a Player object representing the bomberman character on the grid
     * @param posX int representing the x coordinate where the player will be drawn
     * @param posY int representing the y coordinate where the player will be drawn
     * @param visible boolean specifying whether the player object should be drawn
     * @param speed int representing the speed of movement of the player object on the map
     */
    public Player(int posX, int posY, boolean visible, int speed) {
        this.imagePath = "../resources/bomberman9.png";
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
        this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.bombsPlaced = new ArrayList<Bomb>();
        this.bombsAllowed = 3;
        this.wallPass = true;
        this.bombPass = true;
        this.flamePass = true;
        this.detonatorEnabled = true;
    }

    /**
     * Draws the player object on the grid during gameplay.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    public void draw(Graphics2D g) {
        //serialization does not save Image objects
        //If the image object is empty, we reload the sprite
        if (image == null) {
            this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        }

        if (visible /*not dead*/) {
            g.drawImage(image, posX, posY, null);
        } else {
            countDownToRespawn();
        }
    }

    /**
     * Draw the bomb objects on the grid during gameplay.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    public void drawBombs(Graphics2D g) {
        if (bombsPlaced.isEmpty()) return;
        int length = bombsPlaced.size();
        for (int i = 0; i < length; i ++) {
            Bomb bomb = bombsPlaced.get(i);

            //if the bomb is not visible it means it has detonated, which means we need to draw
            //flame objects where the bomb object used to be drawn
            if (!bomb.isVisible()) {
                tileMap.addFlames(bomb.getPosX(), bomb.getPosY());
                bombsPlaced.remove(bomb);
                length = bombsPlaced.size();
            }
            else bomb.draw(g);
        }
    }

    /**
     * Detonates the oldest bomb object placed by the player.
     */
    public void detonateLastBomb() {
        boolean detonated = false;
        int i = 0;
        //detonate the oldest visible bomb in the bombsPlaced array
        while (!detonated && i < bombsPlaced.size()) {
            if (bombsPlaced.get(i).isVisible() == false) {
                i++;
            } else {
                bombsPlaced.get(i).setVisible(false);
                detonated = true;
            }
        }
    }


    /**
     * Place a bomb object on the current tile the player object is standing on.
     */
    private void placeBomb() {
        if (bombsPlaced.size() < bombsAllowed) {
            //The % allows the bombs to snap to the center of the tiles where they are placed
            Bomb bomb = new Bomb(posX - posX % 32, posY - posY % 32);
            bombsPlaced.add(bomb);
        }
    }

    /**
     * Increments the bomb object blast radius by 1.
     * Called upon picking up the Flames powerUp.
     */
    public void incrementBombRadius() {
        tileMap.incrementBombRadius();
    }

    /**
     * Increment the speed of the player from its default
     * of 'NORMALSPEED' to 'FASTSPEED'. Called upon picking up
     * the Speed PowerUp.
     */
    public void incrementSpeed() {
        if (speed == NORMALSPEED) {
            speed = FASTSPEED;
        }
    }

    /**
     * Disable the visibility of the player so that it is not
     * drawn on the grid. Decrease the number of lifes remaining
     * by one.
     */
    public void death() {
        disablePowerUpsOnDeath();
        this.visible = false;
        decrementLifesRemaining();
        this.deltaX = 0;
        this.deltaY = 0;
    }

    /**
     * Count down 60 frames before displaying
     * the player object again. Reset the position
     * of the player object to the top-left corner
     * of the tilemap.
     */
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

    /**
     * Reduces the number of lifes the player has remaining by 1.
     */
    public void decrementLifesRemaining() {
        this.livesRemaining--;
        if (livesRemaining < 0) {
            currentState = GamePlayState.GAMEOVER;
        }
    }

    /**
     * Based on the PowerUpType passed to this method, we modify gameplay logic according to the specific
     * functionality of the corresponding powerUp.
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
                incrementSpeed();
                break;
            case WALLPASS:
                wallPass = true;
                break;
        }
    }

    /**
     * Disable the powerUps on the player object that are lost upon death.
     */
    public void disablePowerUpsOnDeath() {
        bombPass = false;
        flamePass = false;
        wallPass = false;
        detonatorEnabled = false;
        SoundController.DEATH.play();
    }

    /**
     * Reset the position of the player to the top-left corner of the map.
     * Called upon advancing to the next stage (killing all the enemies and
     * touching the door).
     */
    public void nextStage() {
        previousX = 35;
        previousY = 35;
        posX = 35;
        posY = 35;
        //Erase all the existing bombs placed on the completed stage.
        bombsPlaced = new ArrayList<Bomb>();
        if (tileMap != null) {
            tileMap.nextStage();
        }
    }

    /**
     * Move the player object if the arrow keys are pressed.
     * Place a bomb if the x key is pressed. Detonate a bomb if the z key is pressed.
     * Enter the in-game menu if the space bar is pressed.
     * @param key KeyCode used to represent the key pressed on the keyboard.
     */
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
                if (detonatorEnabled && !bombsPlaced.isEmpty()) {
                    detonateLastBomb();
                }
            } else if (key == KeyEvent.VK_Z) {
                placeBomb();
            }
        }
    }

    /**
     * Stop moving the player object upon releasing
     * the arrow keys.
     * @param key
     */
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

    /**
     * Get the TileMap object associated to the player.
     * @return
     */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * Set the TileMap object associated to the player.
     * @param tileMap An instance of the TileMap object.
     */
    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    /**
     * Get the bombs the active bombs placed on the grid by the player.
     * @return
     */
    public ArrayList<Bomb> getBombsPlaced() {
        return bombsPlaced;
    }

    /**
     * Set the active bombs associated to the player object.
     * @param bombsPlaced An arraylist of bomb objects that have been placed by
     *                    the player.
     */
    public void setBombsPlaced(ArrayList<Bomb> bombsPlaced) {
        this.bombsPlaced = bombsPlaced;
    }

    /**
     * Get the GamePlayState object associated to the player object.
     * @return
     */
    public GamePlayState getCurrentGamePlayState() {
        return this.currentState;
    }

    /**
     *
     * @param newState
     */
    public void setCurrentGamePlayState(GamePlayState newState) {
        this.currentState = newState;
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
