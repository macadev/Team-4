/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

public class StageData {

    Tuple[] enemiesPresent;
    PowerUpType powerUpPresent;
    boolean isBonusStage;

    public StageData(Tuple[] enemiesPresent, PowerUpType powerUpPresent, boolean isBonusStage) {
        this.enemiesPresent = enemiesPresent;
        this.powerUpPresent = powerUpPresent;
        this.isBonusStage = isBonusStage;
    }

    public Tuple[] getEnemiesPresent() {
        return enemiesPresent;
    }

    public void setEnemiesPresent(Tuple[] enemiesPresent) {
        this.enemiesPresent = enemiesPresent;
    }

    public PowerUpType getPowerUpPresent() {
        return powerUpPresent;
    }

    public void setPowerUpPresent(PowerUpType powerUpPresent) {
        this.powerUpPresent = powerUpPresent;
    }

    public boolean isBonusStage() {
        return isBonusStage;
    }

    public void setBonusStage(boolean isBonusStage) {
        this.isBonusStage = isBonusStage;
    }
}
