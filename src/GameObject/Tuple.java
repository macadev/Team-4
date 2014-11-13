/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

public class Tuple {

    private EnemyType enemyType;
    private int numberPresent;


    public Tuple(EnemyType enemyType, int numberPresent) {
        this.enemyType = enemyType;
        this.numberPresent = numberPresent;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public int getNumberPresent() {
        return numberPresent;
    }

    public void setNumberPresent(int numberPresent) {
        this.numberPresent = numberPresent;
    }
}
