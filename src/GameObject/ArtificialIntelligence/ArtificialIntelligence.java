/**
 * Created by danielmacario on 14-11-13.
 */
package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;

import java.io.Serializable;
import java.util.Random;

public abstract class ArtificialIntelligence implements Serializable {

    public static final Random random = new Random();

    public abstract void move(Enemy enemy);

    /**
     * Displaced the object in one of the four cardinal directions based on the directionOfMovement attribute
     */
    public void moveEnemyOnBoard(Enemy enemy) {
        int posY = enemy.getPosY();
        int posX = enemy.getPosX();
        int speed = enemy.getSpeed();

        switch (enemy.getDirectionOfMovement()) {
            case NORTH:
                enemy.setPreviousY(posY);
                enemy.setPosY(posY - speed);
                break;
            case SOUTH:
                enemy.setPreviousY(posY);
                enemy.setPosY(posY + speed);
                break;
            case EAST:
                enemy.setPreviousX(posX);
                enemy.setPosX(posX + speed);
                break;
            case WEST:
                enemy.setPreviousX(posX);
                enemy.setPosX(posX - speed);
                break;
        }
    }

    /**
     * Sets the direction of movement to the opposite in terms of cardinal direction. For example, North would become
     * South, East would become West, etc.
     */
    public void reverseDirection(Enemy enemy) {
        Direction directionOfMovement = enemy.getDirectionOfMovement();
        switch (directionOfMovement) {
            case NORTH:
                enemy.setDirectionOfMovement(Direction.SOUTH);
                break;
            case SOUTH:
                enemy.setDirectionOfMovement(Direction.NORTH);
                break;
            case EAST:
                enemy.setDirectionOfMovement(Direction.WEST);
                break;
            case WEST:
                enemy.setDirectionOfMovement(Direction.EAST);
                break;
        }
    }

    public abstract void chasePlayer(int playerPosX, int playerPosY, int distanceBetweenEnemyAndPlayer, Enemy enemy);
}
