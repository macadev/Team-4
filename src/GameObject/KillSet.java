/**
 * Created by danielmacario on 14-11-17.
 */
package GameObject;

import GamePlay.Coordinate;
import java.io.Serializable;

/**
 * Class used as a container to store the data needed to calculate the score
 * obtained from a bomb blast. We keep track of the location of the bomb that
 * killed the passed enemy, as well as the location of the death. With this information
 * We can calculate the distance between the bomb blast and the enemy, which we use to
 * determine which enemies died first from a bomb blast.
 */
public class KillSet implements Serializable {

    private Coordinate locationOfDeath;
    private Coordinate locationOfBomb;
    private Enemy enemy;
    private int score;
    private int distanceToBombOrigin;

    /**
     * Initialize a KillSet object containing the information regarding the
     * death of an enemy.
     * @param locationOfDeath
     * @param locationOfBomb
     * @param enemy
     */
    public KillSet(Coordinate locationOfDeath, Coordinate locationOfBomb, Enemy enemy) {
        this.locationOfDeath = locationOfDeath;
        this.locationOfBomb = locationOfBomb;
        this.enemy = enemy;
        this.distanceToBombOrigin = locationOfBomb.distanceTo(locationOfDeath);
        this.score = enemy.getScore();
    }

    public Coordinate getLocationOfDeath() {
        return locationOfDeath;
    }

    public void setLocationOfDeath(Coordinate locationOfDeath) {
        this.locationOfDeath = locationOfDeath;
    }

    public Coordinate getLocationOfBomb() {
        return locationOfBomb;
    }

    public void setLocationOfBomb(Coordinate locationOfBomb) {
        this.locationOfBomb = locationOfBomb;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public int getDistanceToBombOrigin() {
        return distanceToBombOrigin;
    }

    public int getScore() {
        return score;
    }
}
