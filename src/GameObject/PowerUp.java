package GameObject;

/**
 * Created by danielmacario on 14-11-05.
 */
public class PowerUp extends StaticObject {

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

}
