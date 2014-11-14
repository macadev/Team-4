/**
 * Created by danielmacario on 14-11-13.
 */
package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;

public class HighIntelligence extends ArtificialIntelligence {

    @Override
    public void move(Enemy enemy) {
        moveEnemyOnBoard(enemy);

        int posX = enemy.getPosX();
        int posY = enemy.getPosY();
        Direction directionOfMovement = enemy.getDirectionOfMovement();

        if (randomTurnOnIntersection(posX, posY)) {
            enemy.setDirectionOfMovement(Direction.getRandomPerpendicularDirection(directionOfMovement));
            enemy.setPosX(posX - posX % 32);
            enemy.setPosY(posY - posY % 32);

        }
    }

    @Override
    public void chasePlayer(int playerPosX, int playerPosY, Enemy enemy) {
        
    }

    public boolean randomTurnOnIntersection(int posX, int posY) {
        boolean playerAtXIntersection = (posX) % 32 <= 3 && (posX/32) % 2 == 1;
        boolean playerAtYIntersection = (posY) % 32 <= 3 && (posY/32) % 2 == 1;
        if (playerAtXIntersection && playerAtYIntersection && random.nextFloat() <= 0.5f) {
            return true;
        } else {
            return false;
        }
    }

}
