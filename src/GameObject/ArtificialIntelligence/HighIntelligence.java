/**
 * Created by danielmacario on 14-11-13.
 */
package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;
import GamePlay.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

public class HighIntelligence extends ArtificialIntelligence implements Serializable {

    public static PathFinder pathFinder;
    private ArrayList<Coordinate> pathToPlayer;
    private boolean chaseEnabled;
    private Coordinate nextDestination;
    private int recalculatePathTimer = 0;

    @Override
    public void chasePlayer(int playerPosX, int playerPosY, int distanceFromEnemyToPlayer, Enemy enemy) {
        //if (recalculatePathTimer < 0 && distanceFromEnemyToPlayer < 85) {
        boolean enemyAtCenterOfTile = enemy.getPosX() % 32 == 0 && enemy.getPosY() % 32 == 0;
//        if (recalculatePathTimer < 0 && distanceFromEnemyToPlayer < 85) {
        if (enemyAtCenterOfTile && distanceFromEnemyToPlayer < 85) {
            pathToPlayer = pathFinder.findPath(playerPosX, playerPosY, enemy.getPosX(), enemy.getPosY(), enemy.hasWallPass());
            System.out.println("is path null?" + (pathToPlayer == null));
            if (pathToPlayer != null) {
                pathToPlayer.remove(0);
                setNextDestination();
                System.out.println("Recalculating chase path");
                chaseEnabled = true;
            }
            recalculatePathTimer = 45;
        }
        recalculatePathTimer--;
    }

    public boolean setNextDestination() {
        if (pathToPlayer == null || pathToPlayer.isEmpty()) {
            return false;
        }
        Coordinate nextPositionOnGrid = pathToPlayer.remove(0);
        int nextRow = nextPositionOnGrid.getRow();
        int nextCol = nextPositionOnGrid.getCol();
        nextDestination = new Coordinate((nextCol + 1) * 32, (nextRow + 1) * 32);
        return true;
    }

    @Override
    public void move(Enemy enemy) {
        int enemyPosX = enemy.getPosX();
        int enemyPosY = enemy.getPosY();

        if (chaseEnabled) {

            if (nextDestination == null) {
                chaseEnabled = false;
                return;
            }

            int nextX = nextDestination.getRow();
            int nextY = nextDestination.getCol();
            boolean enemyIsAtNextCol = (Math.abs(enemyPosX - nextX) <= 1);
            boolean enemyIsAtNextRow = (Math.abs(enemyPosY - nextY) <= 1);
            if (enemyIsAtNextRow && enemyIsAtNextCol) {
                System.out.println("At next destination!");
                enemy.setPosY(nextY);
                enemy.setPosX(nextX);
                boolean nextDestinationExists = setNextDestination();

                if (!nextDestinationExists) {
                    moveEnemyOnBoard(enemy);
                    chaseEnabled = false;
                    return;
                }
            }

            //TODO: decide if we should use these statements
            if (enemyIsAtNextRow) {
                //enemy.setPosY(nextY);
                if (enemyPosX < nextX) {
                    enemy.setDirectionOfMovement(Direction.EAST);
                    System.out.println("HIN going east");
                } else {
                    enemy.setDirectionOfMovement(Direction.WEST);
                    System.out.println("HIN going west");
                }
            } else {
                //enemy.setPosX(nextX);
                if (enemyPosY < nextY) {
                    enemy.setDirectionOfMovement(Direction.SOUTH);
                    System.out.println("HIN going south");
                } else {
                    enemy.setDirectionOfMovement(Direction.NORTH);
                    System.out.println("HIN going north");
                }
            }
        } else {
            Direction directionOfMovement = enemy.getDirectionOfMovement();
            if (randomTurnOnIntersection(enemyPosX, enemyPosY)) {
                enemy.setDirectionOfMovement(Direction.getRandomPerpendicularDirection(directionOfMovement));
                enemy.setPosX(enemyPosX - enemyPosX % 32);
                enemy.setPosY(enemyPosY - enemyPosY % 32);
            }
        }
        moveEnemyOnBoard(enemy);
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

    public static void setPathFinder(PathFinder pathFinder) {
        HighIntelligence.pathFinder = pathFinder;
    }
}
