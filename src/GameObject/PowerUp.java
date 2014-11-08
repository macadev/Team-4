package GameObject;

import javax.swing.*;

/**
 * Created by danielmacario on 14-11-05.
 */
public class PowerUp extends StaticObject {

    private PowerUpType powerUpType;
    private boolean keptAfterDeath;

    /**
     * PowerUp Constructor. We group the entire functionality of all powerups within a single class
     * instead of having 8 different classes with very similar purposes. Based on the PowerUpType passed
     * we decide on the image that will be rendered, and the type is stored so that we can enable the
     * specific functionality of the powerup once the player has collided with it
     * @param powerUpType
     */
    public PowerUp(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;

        switch (powerUpType) {
            case BOMBPASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/bombpass.png")).getImage();
                keptAfterDeath = false;
                break;
            case BOMBS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/bombs.png")).getImage();
                keptAfterDeath = true;
                break;
            case DETONATOR:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/detonator.png")).getImage();
                keptAfterDeath = false;
                break;
            case FLAMEPASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/flamepass.png")).getImage();
                keptAfterDeath = false;
                break;
            case FLAMES:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/flames.png")).getImage();
                keptAfterDeath = true;
                break;
            case MYSTERY:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/mystery.png")).getImage();
                //kept after death doesn't apply to this powerup
                break;
            case SPEED:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/speed.png")).getImage();
                keptAfterDeath = true;
                break;
            case WALLPASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/wallpass.png")).getImage();
                keptAfterDeath = false;
                break;
        }

    }

    /**
     * Based on the PowerUpType associated with this instance, we modify gameplay logic according to the specific
     * functionality of each powerup
     * @param player
     * @param powerUp
     */
    public void enablePowerUp(Player player, PowerUpType powerUp) {
        switch (powerUp) {
            case BOMBPASS:
                player.setBombPass(true);
                break;
            case BOMBS:
                player.incrementBombsAllowed();
                break;
            case DETONATOR:
                player.setDetonatorEnabled(true);
                break;
            case FLAMEPASS:
                player.setFlamePass(true);
                break;
            case FLAMES:
                player.incrementBombRadius();
                break;
            case MYSTERY:
                break;
            case SPEED:
                //TODO: define speed attributes
                player.incrementSpeed();
                break;
            case WALLPASS:
                player.setWallPass(true);
                break;
        }
    }

    public void hitByExplosion() {

    }

}