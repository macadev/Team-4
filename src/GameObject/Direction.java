/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

import java.io.Serializable;
import java.util.Random;

/**
 * Defines the motions that a movable object can perform in the game.
 */
public enum Direction implements Serializable {
    NORTH, SOUTH, EAST, WEST;
    public static Random random = new Random();

    public static final Direction[] DIRECTIONS = {NORTH, SOUTH, EAST, WEST};
    public static final Direction[] NSDIRECTIONS = {NORTH, SOUTH};
    public static final Direction[] EWDIRECTIONS = {EAST, WEST};

    /**
     * Get a random Direction. Used to determine the motion of enemies upon spawning,
     * and also when their artifical intelligence requirements are met.
     * @return
     */
    public static Direction getRandomDirection() {
        int index = random.nextInt(DIRECTIONS.length);
        return DIRECTIONS[index];
    }

    /**
     *
     * @param direction
     * @return
     */
    public static Direction getRandomPerpendicularDirection(Direction direction) {
        int index = random.nextInt(2);
        if (direction == NORTH || direction == SOUTH) {
            return EWDIRECTIONS[index];
        } else {
            return NSDIRECTIONS[index];
        }
    }


}
