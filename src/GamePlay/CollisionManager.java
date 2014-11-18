/**
 * Created by danielmacario on 14-11-13.
 */
package GamePlay;

import GameObject.*;
import java.awt.*;
import java.util.ArrayList;

public class CollisionManager {

    private Player player;

    public CollisionManager(Player player) {
        this.player = player;
    }
    private ScoreManager scoreManager = new ScoreManager();

    public void handleCollisions(GameObject[][] objects,
                                 Rectangle playerRectangle, ArrayList<Enemy> enemies, ArrayList<Bomb> bombsPlaced,
                                 ArrayList<Flame> flames, PowerUp powerUp, Door door) {

        checkCollisionWithPowerUp(playerRectangle, powerUp);
        checkCollisionWithDoor(playerRectangle, door, enemies);
        checkCollisionsWithWalls(objects, playerRectangle, enemies);
        checkCollisionsWithBombs(bombsPlaced, playerRectangle, enemies);
        checkCollisionsWithFlames(bombsPlaced, playerRectangle, enemies, flames, powerUp);
        checkCollisionsWithEnemies(playerRectangle, enemies);

    }

    private void checkCollisionWithPowerUp(Rectangle playerRectangle, PowerUp powerUp) {
        Rectangle powerUpRectangle = powerUp.getBounds();

        if (powerUp.isVisible()) {
            if (playerRectangle.intersects(powerUpRectangle)) {
                player.enablePowerUp(powerUp.getPowerUpType());
                powerUp.setVisible(false);
            }
        }
    }

    private void checkCollisionWithDoor(Rectangle playerRectangle, Door door, ArrayList<Enemy> enemies) {
        if (enemies.size() == 0) {
            Rectangle doorRectangle = door.getBounds();
            if (door.isVisible()) {
                if (playerRectangle.intersects(doorRectangle)){
                    System.out.println("advancing to next stage!");
                    player.nextStage();
                }
            }
        }
    }

    public void checkCollisionsWithWalls(GameObject[][] objects, Rectangle playerRectangle, ArrayList<Enemy> enemies) {
        //Check for collision of the player and the enemies with walls
        for (GameObject[] row : objects) {
            for (GameObject wall : row) {
                if (wall != null) {

                    boolean playerHasWallPass = player.hasWallPass();
                    Rectangle wallRectangle = wall.getBounds();

                    //Check for collision of the player with the targeted wall
                    //Upon resolving the collision, we stop checking this for loop
                    //To reduce computation
                    if (!playerHasWallPass || (playerHasWallPass && (wall instanceof ConcreteWall))) {
                        if (playerRectangle.intersects(wallRectangle)) {
                            restoreToBeforeCollision(player, wallRectangle);
                        }
                    }

                    //Check for collisions of the enemies with the walls
                    boolean enemyHasWallPass;
                    for (Enemy enemy : enemies) {
                        enemyHasWallPass = enemy.hasWallPass();
                        Rectangle enemyRectangle = enemy.getBounds();
                        if (!enemyHasWallPass || (enemyHasWallPass && (wall instanceof ConcreteWall))) {
                            if (enemyRectangle.intersects(wallRectangle)) {
                                enemy.reverseDirection();
                                restoreToBeforeCollision(enemy, wallRectangle);
                            }
                        }
                    }
                }
            }
        }
    }

    public void checkCollisionsWithBombs(ArrayList<Bomb> bombsPlaced, Rectangle playerRectangle, ArrayList<Enemy> enemies) {
        //Check for collisions of the player with the bombs
        for (Bomb bomb : bombsPlaced) {
            Rectangle bombRectangle = bomb.getBounds();
            if (playerRectangle.intersects(bombRectangle)) {
                if (!bomb.isFirstCollision()) {
                    player.restorePreviousPosition();
                }
            } else {
                bomb.setFirstCollision(false);
            }

            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (enemyRectangle.intersects(bombRectangle)) {
                    enemy.reverseDirection();
                    restoreToBeforeCollision(enemy, bombRectangle);
                }
            }

        }
    }

    public void checkCollisionsWithFlames(ArrayList<Bomb> bombsPlaced, Rectangle playerRectangle, ArrayList<Enemy> enemies,
                                          ArrayList<Flame> flames, PowerUp powerUp) {
        //Check for collision between player/bombs/enemies with flames
        Rectangle powerUpRectangle = powerUp.getBounds();
        Rectangle flameRectangle;
        ArrayList<KillSet> enemiesKilled = new ArrayList<KillSet>();


        for (Flame flame : flames) {
            flameRectangle = flame.getBounds();

            if (player.isVisible()) {
                if(!player.hasFlamePass()) {
                    if (playerRectangle.intersects(flameRectangle)) {
                        player.death();
                    }
                }
            }

            for (Bomb bomb : bombsPlaced) {
                Rectangle bombRectangle = bomb.getBounds();
                if (flameRectangle.intersects(bombRectangle)) {
                    bomb.setVisible(false);
                }
            }

            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (enemyRectangle.intersects(flameRectangle)) {
                    enemy.death();
                    Coordinate positionOfDeath = enemy.getCenterOfEnemyAsCoordinate();
                    System.out.println("death x = "+ positionOfDeath.getRow() + " y = " + positionOfDeath.getCol());
                    Coordinate locationOfBomb = flame.getExplosionOriginAsCoordinate();
                    System.out.println("location of bomb x = "+ locationOfBomb.getRow() + " y = " + locationOfBomb.getCol());
                    System.out.println("Distance = " + positionOfDeath.distanceTo(locationOfBomb));
                    if (!enemy.isHitByFlames()) {
                        enemiesKilled.add(new KillSet(positionOfDeath, locationOfBomb, enemy));
                    }
                    enemy.setHitByFlames(true);
                }
            }

            if (powerUpRectangle.intersects(flameRectangle)) {
                if (!powerUp.isFirstCollision()) {
                     powerUp.hitByExplosion();
                }
                powerUp.setFirstCollision(false);
            }
        }
        if (enemiesKilled.size() > 0) System.out.println("SIZE = " + enemiesKilled.size());

        calculateScoreFromKills(enemiesKilled);
    }

    private void calculateScoreFromKills(ArrayList<KillSet> enemiesKilled) {
        if (enemiesKilled.isEmpty()) return;
        int scoreObtained = scoreManager.determineScoreFromKills(enemiesKilled);
        player.addToScore(scoreObtained);
    }

    public void checkCollisionsWithEnemies(Rectangle playerRectangle, ArrayList<Enemy> enemies) {
        //check for collisions between the player and the enemies
        if (player.isVisible()) {
            for (Enemy enemy : enemies) {
                Rectangle enemyRectangle = enemy.getBounds();
                if (playerRectangle.intersects(enemyRectangle)) {
                    player.death();
                }
            }
        }
    }

    public void restoreToBeforeCollision(MovableObject object, Rectangle wallRectangle) {
        int x = object.getPosX();
        int y = object.getPosY();

        object.restorePreviousXPosition();

        if (object.getBounds().intersects(wallRectangle)) {
            object.restorePositionTo(x,y);
        } else {
            return;
        }

        object.restorePreviousYPosition();

        if (object.getBounds().intersects(wallRectangle)) {
            object.restorePositionTo(x,y);
        } else {
            return;
        }

        object.restorePreviousPosition();
        return;
    }
}
