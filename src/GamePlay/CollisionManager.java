/**
 * Created by danielmacario on 14-11-13.
 */
package GamePlay;

import Database.DatabaseController;
import GameObject.*;
import SystemController.SoundController;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class CollisionManager implements Serializable {

    private Player player;
    private TileMap tileMap;
    private ScoreManager scoreManager;
    private String userName;
    public CollisionManager(Player player, TileMap tileMap, String userName) {
        this.player = player;
        this.tileMap = tileMap;
        this.scoreManager = new ScoreManager();
        this.userName = userName;
    }

    public void handleCollisions(GameObject[][] objects,
                                 Rectangle playerRectangle, ArrayList<Enemy> enemies, ArrayList<Bomb> bombsPlaced,
                                 ArrayList<Flame> flames, PowerUp powerUp, Door door, boolean isBonusStage) {

        checkCollisionsWithWalls(objects, playerRectangle, enemies);
        checkCollisionsWithBombs(bombsPlaced, playerRectangle, enemies);
        checkCollisionsWithFlames(bombsPlaced, playerRectangle, enemies, flames, powerUp, door, isBonusStage);
        if (!isBonusStage) {
            checkCollisionWithPowerUp(playerRectangle, powerUp);
            checkCollisionWithDoor(playerRectangle, door, enemies);
            checkCollisionsWithEnemies(playerRectangle, enemies, isBonusStage);
        }


    }

    private void checkCollisionWithPowerUp(Rectangle playerRectangle, PowerUp powerUp) {
        if (powerUp == null) return;
        Rectangle powerUpRectangle = powerUp.getBounds();

        if (powerUp.isVisible()) {
            if (playerRectangle.intersects(powerUpRectangle)) {
                player.enablePowerUp(powerUp.getPowerUpType());
                powerUp.setVisible(false);
                SoundController.POWERUP.play();
            }
        }
    }

    private void checkCollisionWithDoor(Rectangle playerRectangle, Door door, ArrayList<Enemy> enemies) {
        if (door == null) return;

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
        boolean playerHasWallPass = player.hasWallPass();
        for (GameObject[] row : objects) {
            for (GameObject wall : row) {
                if (wall != null) {

                    Rectangle wallRectangle = wall.getBounds();

                    //Check for collision of the player with the targeted wall
                    //Upon resolving the collision, we stop checking this for loop
                    //To reduce computation
                    if (!playerHasWallPass || (wall instanceof ConcreteWall)) {
                        if (playerRectangle.intersects(wallRectangle)) {
                            restoreToBeforeCollision(player, wallRectangle);
                        }
                    }

                    //Check for collisions of the enemies with the walls
                    boolean enemyHasWallPass;
                    for (Enemy enemy : enemies) {
                        enemyHasWallPass = enemy.hasWallPass();
                        Rectangle enemyRectangle = enemy.getBounds();
                        if (!enemyHasWallPass || (wall instanceof ConcreteWall)) {
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

            if (!player.hasBombPass()) {
                if (playerRectangle.intersects(bombRectangle)) {
                    if (!bomb.isFirstCollision()) {
                        player.restorePreviousPosition();
                    }
                } else {
                    bomb.setFirstCollision(false);
                }
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
                                          ArrayList<Flame> flames, PowerUp powerUp, Door door, boolean isBonusStage) {
        //Check for collision between player/bombs/enemies with flames
        Rectangle flameRectangle;
        ArrayList<KillSet> enemiesKilled = new ArrayList<KillSet>();


        for (Flame flame : flames) {
            flameRectangle = flame.getBounds();

            if (player.isVisible() && !player.isInvincibilityEnabled() && !isBonusStage) {
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
                    Coordinate locationOfBomb = flame.getExplosionOriginAsCoordinate();
                    if (!enemy.isHitByFlames()) {
                        enemiesKilled.add(new KillSet(positionOfDeath, locationOfBomb, enemy));
                    }
                    enemy.setHitByFlames(true);
                }
            }

            if (!isBonusStage) {
                //check whether powerup is visible on the map, and if we haven't already spawned
                //a harder set of enemies. For this logic, we are copying the same behaviour seen
                //in the bomber game suggested by the specifications.
                if (powerUp.isVisible()) {
                    Rectangle powerUpRectangle = powerUp.getBounds();
                    if (powerUpRectangle.intersects(flameRectangle)) {
                        powerUp.setVisible(false);
                        spawnSetOfHarderEnemies(powerUp.getPosX(), powerUp.getPosY());
                    }
                }

                Rectangle doorRectangle = door.getBounds();
                if (doorRectangle.intersects(flameRectangle)) {
                    if (!tileMap.isHarderSetAlreadyCreated()) {
                        spawnSetOfHarderEnemies(door.getPosX(), door.getPosY());
                    }
                }
            }
        }

        if (enemiesKilled.size() > 0) System.out.println("SIZE = " + enemiesKilled.size());

        calculateScoreFromKills(enemiesKilled);
    }

    public void spawnSetOfHarderEnemies(int posX, int posY) {
        if (tileMap.isHarderSetAlreadyCreated()) return;
        tileMap.spawnSetOfHarderEnemies(posX, posY);
        tileMap.setHarderSetAlreadyCreated(true);
    }

    private void calculateScoreFromKills(ArrayList<KillSet> enemiesKilled) {
        if (enemiesKilled.isEmpty()) return;
        int scoreObtained = scoreManager.determineScoreFromKills(enemiesKilled);

        try {
            System.out.println("UPDATING SCORE IN DB!!");
            DatabaseController.setScore(userName, scoreObtained);
        } catch (Exception e){
            e.printStackTrace();
        }

        player.addToScore(scoreObtained);
    }

    public void checkCollisionsWithEnemies(Rectangle playerRectangle, ArrayList<Enemy> enemies, boolean isBonusStage) {
        //check for collisions between the player and the enemies
        if (player.isVisible() && !player.isInvincibilityEnabled() && !isBonusStage) {
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
