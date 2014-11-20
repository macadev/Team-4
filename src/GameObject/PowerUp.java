package GameObject;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by danielmacario on 14-11-05.
 */
public class PowerUp extends StaticObject implements Serializable {

    private PowerUpType powerUpType;
    private boolean keptAfterDeath;
    private boolean firstCollision;

    /**
     * PowerUp Constructor. We group the entire functionality of all powerups within a single class
     * instead of having 8 different classes with very similar purposes. Based on the PowerUpType passed
     * we decide on the image that will be rendered, and the type is stored so that we can enable the
     * specific functionality of the powerup once the player has collided with it
     * @param powerUpType
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

    public void hitByExplosion() {
        //TODO: spawn harder wave of enemies....
    }

    public boolean isFirstCollision() {
        return firstCollision;
    }

    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
}
