package GameObject.ArtificialIntelligence;

import GameObject.Enemy;

/**
 * Created by danielmacario on 14-11-13.
 */
public class LowIntelligence extends ArtificialIntelligence {

    @Override
    public void move(Enemy enemy) {
        moveEnemyOnBoard(enemy);
    }

}
