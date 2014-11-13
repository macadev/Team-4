/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

import java.util.Random;

/**
 * Defines the motions that a movable object can perform in the game
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    private static Random random = new Random();

    /**
     * Get a random Direction. Used to determine the motion of enemies upon spawning.
     * @return
     */
    public static Direction getRandomDirection() {
        Direction[] directions = {NORTH, SOUTH, EAST, WEST};
        int index = random.nextInt(directions.length);
        return directions[index];
    }


}
