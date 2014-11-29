/**
 * Created by danielmacario on 14-11-13.
 */
package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;
import GamePlay.Coordinate;

import java.io.Serializable;

public class MediumIntelligence extends ArtificialIntelligence implements Serializable {

    /**
     *
     * @param enemy Enemy object to be moved on the grid.
     */
    @Override
    public void move(Enemy enemy) {
        moveEnemyOnBoard(enemy);

        int posX = enemy.getPosX();
        int posY = enemy.getPosY();
        Direction directionOfMovement = enemy.getDirectionOfMovement();

        boolean shouldTurn = random.nextFloat() <= 0.1f;
        if (randomTurnOnIntersection(posX, posY, shouldTurn)) {
            enemy.setDirectionOfMovement(Direction.getRandomPerpendicularDirection(directionOfMovement));
            enemy.setPosX(posX - posX % 32);
            enemy.setPosY(posY - posY % 32);

        }
    }

    /**
     * This methods implements the functionality of the enemy following the player if certain conditions are met.
     * The conditions that have to be met are the following: If the enemy and the player are on the same row or the
     * same column. Also the enemy object must not be more than 65 units away from the player.
     * @param playerPosX x coordinate of the player object on the grid.
     * @param playerPosY y coordinate of the player object on the grid.
     * @param distanceFromEnemyToPlayer Integer representing distance of the enemy from the player.
     * @param enemy Enemy object that will start chasing the player.
     */
    @Override
    public void chasePlayer(int playerPosX, int playerPosY, int distanceFromEnemyToPlayer, Enemy enemy) {

        boolean playerAndEnemyOnSameRow = playerPosY - enemy.getPosY() < 3 && playerPosY - enemy.getPosY() > -3;
        boolean playerAndEnemyOnSameCol = playerPosX - enemy.getPosX() < 3 && playerPosX - enemy.getPosX() > -3;

        if (!(playerAndEnemyOnSameCol || playerAndEnemyOnSameRow)) {
            return;
        }

        int enemyPosX = enemy.getPosX();
        int enemyPosY = enemy.getPosY();

        if (distanceFromEnemyToPlayer < 65) {
            if (playerAndEnemyOnSameRow) {
                enemy.setPosY(enemyPosY - enemyPosY % 32);
                if (playerPosX > enemyPosX) {
                    enemy.setDirectionOfMovement(Direction.EAST);
                    System.out.println("going east");
                } else {
                    enemy.setDirectionOfMovement(Direction.WEST);
                    System.out.println("going west");
                }
            } else {
                enemy.setPosX(enemyPosX - enemyPosX % 32);
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

    /**
     * This method implements the functionality of the enemy making the random turn when at an intersection.
     * For the random turn to happen the enemy has to be at an intersection(no concrete walls around) and chance of it
     * happening is at a random probability of 10%
     * @param posX Integer representing x coordinate of the enemy.
     * @param posY  Integer representing y coordinate of the enemy.
     * @param shouldTurn Boolean representing whether the random chance of turning is true or false.
     * @return
     */
    public boolean randomTurnOnIntersection(int posX, int posY, boolean shouldTurn) {
        boolean enemyAtXIntersection = (posX) % 32 <= 3 && (posX/32) % 2 == 1;
        boolean enemyAtYIntersection = (posY) % 32 <= 3 && (posY/32) % 2 == 1;
        if (enemyAtXIntersection && enemyAtYIntersection && shouldTurn) {
            return true;
        } else {
            return false;
        }
    }



}
