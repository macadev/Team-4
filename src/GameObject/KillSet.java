package GameObject;

import GamePlay.Coordinate;

/**
 * Created by danielmacario on 14-11-17.
 */
public class KillSet {

    private Coordinate locationOfDeath;
    private Coordinate locationOfBomb;
    private Enemy enemy;
    private int score;
    private int distanceToBombOrigin;

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
