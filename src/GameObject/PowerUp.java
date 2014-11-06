package GameObject;

import javax.swing.*;

/**
 * Created by danielmacario on 14-11-05.
 */
public class PowerUp extends StaticObject {

    private PowerUpType powerUpType;

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
                break;
            case BOMBS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/bombs.png")).getImage();
                break;
            case DETONATOR:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/detonator.png")).getImage();
                break;
            case FLAMEPASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/flamepass.png")).getImage();
                break;
            case FLAMES:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/flames.png")).getImage();
                break;
            case MYSTERY:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/mystery.png")).getImage();
                break;
            case SPEED:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/speed.png")).getImage();
                break;
            case WALLPASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/PowerUps/wallpass.png")).getImage();
                break;
        }

    }

    public void enablePowerUp(Player player, PowerUpType powerUp) {
        switch (powerUp) {
            case BOMBPASS:
                break;
            case BOMBS:
                player.setBombsAllowed(2);
                break;
            case DETONATOR:
                break;
            case FLAMEPASS:
                break;
            case FLAMES:
                break;
            case MYSTERY:
                break;
            case SPEED:
                //TODO: define speed attributes
                player.setSpeed(player.getSpeed() + 1);
                break;
            case WALLPASS:
                player.setWallPass(true);
                break;
        }
    }

    public void hitByExplosion() {

    }

}
