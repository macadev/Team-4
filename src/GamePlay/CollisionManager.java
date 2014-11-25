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

/**
 * Used to control the logic behind all types of collision detection that
 * take place during game play, specifically collision between:
 * - Player and enemies, player and walls, player and powerUp, player and Door, player and flames, and player and bombs.
 * - Enemies and walls, player and flames, enemies and flames, and enemies and bombs.
 * It also control the logic required for resolving collisions.
 */
public class CollisionManager implements Serializable {

    private Player player;
    private TileMap tileMap;
    private ScoreManager scoreManager;
    private String userName;

    /**
     * Initializes a CollisionManager object, containing the entities to be tested for collisions.
     * @param player A player object used to determine the collisions between players and enemies.
     *               Also note that it contains the bombs that have placed on the grid.
     * @param tileMap A tileMap object containing the walls, powerUp, door, and flames present on the
     *                grid.
     * @param userName The username of the current player.
     */
    public CollisionManager(Player player, TileMap tileMap, String userName) {
        this.player = player;
        this.tileMap = tileMap;
        this.scoreManager = new ScoreManager();
        this.userName = userName;
    }

    /**
     * Fundamental function of the collision manager used to trigger the logic necessary to resolve all the collisions
     * between the objects present on the grid.
     * @param objects Two dimensional array containing the wall objects present on the grid.
     * @param playerRectangle The Rectangle object specifying the bounds of the player object.
     * @param enemies ArrayList containing the enemy objects present on the grid.
     * @param bombsPlaced An ArrayList containing the bombs objects placed on the grid.
     * @param flames An ArrayList containing the flame objects present on the grid.
     * @param powerUp The powerUp object present on the grid.
     * @param door The door object present on the grid.
     * @param isBonusStage A boolean specifying whether the current stage is a bonus stage or not.
     */
    public void handleCollisions(GameObject[][] objects,
                                 Rectangle playerRectangle, ArrayList<Enemy> enemies, ArrayList<Bomb> bombsPlaced,
                                 ArrayList<Flame> flames, PowerUp powerUp, Door door, boolean isBonusStage) {

        checkCollisionsWithWalls(objects, playerRectangle, enemies);
        checkCollisionsWithBombs(bombsPlaced, playerRectangle, enemies);
        checkCollisionsWithFlames(bombsPlaced, playerRectangle, enemies, flames, powerUp, door, isBonusStage);

        //If the current stage is a bonus stage, then we don't call collision detection between player/flames
        //and player/enemy. We also don't check for collision with powerUp or door, since these objects
        //are not present in a bonus stage.
        if (!isBonusStage) {
            checkCollisionWithPowerUp(playerRectangle, powerUp);
            checkCollisionWithDoor(playerRectangle, door, enemies);
            checkCollisionsWithEnemies(playerRectangle, enemies);
        }
    }

    /**
     * Determines whether the  enemies or the player are colliding with a wall or not. If a collision occurs,
     * it calls the necessary logic to resolve it.
     * @param objects
     * @param playerRectangle
     * @param enemies
     */
    public void checkCollisionsWithWalls(GameObject[][] objects, Rectangle playerRectangle, ArrayList<Enemy> enemies) {

        boolean playerHasWallPass = player.hasWallPass();

        //Iterate over all the walls present on the grid, and test if a collision occurs between them
        //and the player or the enemies.
        for (GameObject[] row : objects) {
            for (GameObject wall : row) {
                if (wall != null) {

                    Rectangle wallRectangle = wall.getBounds();

                    //We don't check for collisions between the player and the walls
                    //if the player has the wallPass powerUp enabled
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

    /**
     * Determines whether the player is colliding with a powerUp
     * @param playerRectangle
     * @param powerUp
     */
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
                    SoundController.BOMBEXPLODE.play();
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

    public void checkCollisionsWithEnemies(Rectangle playerRectangle, ArrayList<Enemy> enemies) {
        //check for collisions between the player and the enemies
        if (player.isVisible() && !player.isInvincibilityEnabled()) {
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
