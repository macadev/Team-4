/**
 * Created by danielmacario on 14-11-13.
 */
package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;
import GamePlay.Coordinate;

public class MediumIntelligence extends ArtificialIntelligence {

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

        /**
         * this is in principle what we want, but we need the row and column the player is on
         */

        boolean playerAndEnemyOnSameRow = playerPosY - enemy.getPosY() < 3 && playerPosY - enemy.getPosY() > -3;
        boolean playerAndEnemyOnSameCol = playerPosX - enemy.getPosX() < 3 && playerPosX - enemy.getPosX() > -3;

        if (!(playerAndEnemyOnSameCol || playerAndEnemyOnSameRow)) {
            return;
        }

        int enemyPosX = enemy.getPosX();
        int enemyPosY = enemy.getPosY();

        Coordinate centerOfPlayerObject = new Coordinate(playerPosX + 15, playerPosY + 15);
        Coordinate centerOfEnemyObject = new Coordinate(enemy.getPosX() + 15, enemy.getPosY() + 15);

        int distance = centerOfPlayerObject.distanceTo(centerOfEnemyObject);

        if (distance < 60) {
            System.out.println("chase enabled");
            if (playerAndEnemyOnSameRow) {
                if (playerPosX > enemyPosX) {
                    enemy.setDirectionOfMovement(Direction.EAST);
                    System.out.println("going east");
                } else {
                    enemy.setDirectionOfMovement(Direction.WEST);
                    System.out.println("going west");
                }
            } else {
                if (playerPosY > enemyPosY) {
                    enemy.setDirectionOfMovement(Direction.SOUTH);
                    System.out.println("going south");
                } else {
                    enemy.setDirectionOfMovement(Direction.NORTH);
                    System.out.println("going north");
                }
            }
        }
    }

    public boolean randomTurnOnIntersection(int posX, int posY) {
        boolean playerAtXIntersection = (posX) % 32 <= 3 && (posX/32) % 2 == 1;
        boolean playerAtYIntersection = (posY) % 32 <= 3 && (posY/32) % 2 == 1;
        if (playerAtXIntersection && playerAtYIntersection && random.nextFloat() <= 0.1f) {
            return true;
        } else {
            return false;
        }
    }

}
