/**
 * Created by danielmacario on 14-11-05.
 */
package GameObject;

import javax.swing.*;
import java.io.Serializable;

public class PowerUp extends StaticObject implements Serializable {

    private PowerUpType powerUpType;
    private boolean keptAfterDeath;
    private boolean firstCollision;

    /**
     * Initialize a new powerUp object. We group the entire functionality of all powerUps within a single class
     * instead of having 8 different classes with very similar attributes. We decide on the image that will be
     * rendered and whether the powerUp is kept after death based on the PowerUpType passed. The type is stored so
     * that we can enable the specific functionality of the powerUp once the player has collided with it.
     * @param powerUpType The type specifying which powerUp the constructor must create.
     */
    public PowerUp(PowerUpType powerUpType, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        this.firstCollision = true;
        this.powerUpType = powerUpType;

        switch (powerUpType) {
            case BOMBPASS:
                imagePath = "../resources/PowerUps/bombpass.png";
                keptAfterDeath = false;
                break;
            case BOMBS:
                imagePath = "../resources/PowerUps/bombs.png";
                keptAfterDeath = true;
                break;
            case DETONATOR:
                imagePath = "../resources/PowerUps/detonator.png";
                keptAfterDeath = false;
                break;
            case FLAMEPASS:
                imagePath = "../resources/PowerUps/flamepass.png";
                keptAfterDeath = false;
                break;
            case FLAMES:
                imagePath = "../resources/PowerUps/flames.png";
                keptAfterDeath = true;
                break;
            case MYSTERY:
                imagePath = "../resources/PowerUps/mystery.png";
                //kept after death doesn't apply to this powerup
                break;
            case SPEED:
                imagePath = "../resources/PowerUps/speed.png";
                keptAfterDeath = true;
                break;
            case WALLPASS:
                imagePath = "../resources/PowerUps/wallpass.png";
                keptAfterDeath = false;
                break;
        }
        this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    /**
     * Determine whether the player is colliding with this powerUp instance for the first time
     * or not.
     * @return A boolean representing whether this is the first time the player
     * is colliding with the the powerUp object.
     */
    public boolean isFirstCollision() {
        return firstCollision;
    }

    /**
     * Set the first collision attribute of the powerUp object.
     * @param firstCollision boolean representing whether the player object has collided
     *                       with this powerUp instance in the past or not.
     */
    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }

    /**
     * Retrieve the powerUp type used to create this instance of powerUp object. Used when the player collides
     * with the powerUp object to enable the particular logic associated with it.
     * @return PowerUpType is returned.
     */
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
}
