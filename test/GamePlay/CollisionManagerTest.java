package GamePlay;

import GameObject.*;
import GameObject.Player;
import GameObject.TileMap;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollisionManagerTest {

    private CollisionManager collisionManager;
    private Player player;
    private TileMap tileMap;

    @Before
    public void createNecessaryObjectsForTesting() {
        player = new Player(32, 32, true, MovableObject.NORMALSPEED);
        tileMap = new TileMap(player, 1, "test username");
        collisionManager = new CollisionManager(player, tileMap, "test username");
    }

    @Test
    public void testCheckCollisionsWithWalls() throws Exception {
        player.setPreviousX(33);
        player.setPreviousY(33);
        GameObject[][] walls = tileMap.getObjects();
        walls[1][2] = new BrickWall(64,32, true, false);
        tileMap.setEnemies(new ArrayList<Enemy>());

        // Test collision handling when no collision occurs.
        collisionManager.checkCollisionsWithWalls(walls, player.getBounds(), tileMap.getEnemies());
        assertEquals("If player does not collide, we do not restore the previous X coordinate", 32, player.getPosX());
        assertEquals("If player does not collide, we do not restore the previous Y coordinate", 32, player.getPosY());
        player.setPosX(36);

        // Test collision resolved through X shifting.
        collisionManager.checkCollisionsWithWalls(walls, player.getBounds(), tileMap.getEnemies());
        assertEquals("If a collision occurs, player's position is restored to the previous" +
                "X value if doing so revolves the collision", 33, player.getPosX());
        player.setPosY(31);

        // Test collision resolved through Y shifting.
        collisionManager.checkCollisionsWithWalls(walls, player.getBounds(), tileMap.getEnemies());
        assertEquals("If a collision occurs, player's position is restored to the previous" +
                "Y value if doing so revolves the collision", 33, player.getPosY());
        player.setPosX(36);
        player.setWallPass(true);

        //Test collision with brick walls when wallpass is enabled
        collisionManager.checkCollisionsWithWalls(walls, player.getBounds(), tileMap.getEnemies());
        assertEquals("If the player has WallPass enabled, collisions with brick walls do not" +
                "restore its position", 36, player.getPosX());
    }

    @Test
    public void testCheckCollisionWithPowerUp() throws Exception {
        //powerUp is visible
        PowerUp powerUp = new PowerUp(PowerUpType.BOMBPASS, 32, 32);
        collisionManager.checkCollisionWithPowerUp(player.getBounds(), powerUp);
        assertTrue("If player collides with powerUp, the powerUp effect is activated on the player object", player.hasBombPass());

        //powerUp is null
        powerUp = null;
        player.setBombPass(false);
        collisionManager.checkCollisionWithPowerUp(player.getBounds(), powerUp);
        assertFalse("If the powerUp is null, player attributes are not modified", player.hasBombPass());

        //powerUp is not visible
        powerUp = new PowerUp(PowerUpType.BOMBPASS, 32, 32);
        powerUp.setVisible(false);
        assertFalse("If player collides with powerUp but the PowerUp is not visible, then the attribute" +
                "is not enabled on the player object", player.hasBombPass());
    }

    @Test
    public void testCheckCollisionWithDoor() {
        //door is null
        Door door = null;
        collisionManager.checkCollisionWithDoor(player.getBounds(), door, tileMap.getEnemies());
        assertEquals("If the door is null, player will not advance to the next stage", 32, player.getPosX());

        //door exists, and there are still enemies on the map
        collisionManager.checkCollisionWithDoor(player.getBounds(), door, tileMap.getEnemies());
        assertEquals("if the door exists and there are enemies on the map, a collision with" +
                "the door doesn't advance the player to the next stage, which means it position" +
                "remains the unchanged", 32, player.getPosX());

        //door exists, and there are no enemies on the map.
        door = new Door(32, 32);
        tileMap.setEnemies(new ArrayList<Enemy>());
        collisionManager.checkCollisionWithDoor(player.getBounds(), door, tileMap.getEnemies());
        assertEquals("if the door exists and there are no enemies on the map, a collision with" +
                "the door advances the player to the next stage which resets its x position to 35", 35, player.getPosX());
    }

    @Test
    public void testCheckCollisionsWithBombs() throws Exception {
        Bomb bomb = new Bomb(64, 32);
        ArrayList<Bomb> bombs = new ArrayList<Bomb>();
        bombs.add(bomb);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        tileMap.setEnemies(enemies);
        player.setBombsPlaced(bombs);
        player.setPreviousX(32);
        player.setPosX(35);
        player.setBombPass(false);

        //first player/bomb collision.
        collisionManager.checkCollisionsWithBombs(bombs, player.getBounds(), enemies);
        assertEquals("Player's first collision with a bomb will not restore its position", 35, player.getPosX());

        //Player steps off bomb, enables collision detection
        player.setPosX(32);
        collisionManager.checkCollisionsWithBombs(bombs, player.getBounds(), enemies);
        assertFalse("Bombs' first collision attribute is set to false after player steps off it", bomb.isFirstCollision());

        //Player tries to walk over bomb again
        player.setPosX(35);
        collisionManager.checkCollisionsWithBombs(bombs, player.getBounds(), enemies);
        assertEquals("Player's new collision after stepping off bomb will result in its position" +
                "being reset", 32, player.getPosX());

        //collision between enemy and bomb
        Enemy enemy = new Enemy(EnemyType.BALLOOM, 35, 32);
        enemy.setPreviousX(32);
        enemy.setDirectionOfMovement(Direction.EAST);
        enemies.add(enemy);
        collisionManager.checkCollisionsWithBombs(bombs, player.getBounds(), enemies);
        assertEquals("Enemy's direction is reversed upon colliding with a bomb", Direction.WEST, enemy.getDirectionOfMovement());
        assertEquals("Enemy's position is reset upon colliding with a bomb", 32, enemy.getPosX());
    }

    @Test
    public void testCheckCollisionsWithFlames() throws Exception {

    }

    @Test
    public void testCheckCollisionsWithEnemies() throws Exception {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        Enemy enemy = new Enemy(EnemyType.BALLOOM, 35, 32);
        enemy.setPreviousX(32);
        enemy.setDirectionOfMovement(Direction.EAST);
        enemies.add(enemy);

        //if the player is already dead, a collision does not kill him again
        player.setVisible(false);
        int livesBeforeCollision = player.getLivesRemaining();
        collisionManager.checkCollisionsWithEnemies(player.getBounds(), enemies);
        assertEquals("If the player is not visible when it collides with an enemy, his/her number of lives should not" +
                "be reduced", livesBeforeCollision, player.getLivesRemaining());

        player.setVisible(true);
        player.setInvincibilityEnabled(true);
        livesBeforeCollision = player.getLivesRemaining();
        collisionManager.checkCollisionsWithEnemies(player.getBounds(), enemies);
        assertEquals("If the player is invincible when it collides with an enemy, his/her number of lives should not" +
                "be reduced", livesBeforeCollision, player.getLivesRemaining());

        player.setInvincibilityEnabled(false);
        livesBeforeCollision = player.getLivesRemaining();
        collisionManager.checkCollisionsWithEnemies(player.getBounds(), enemies);
        assertEquals("If the player is visible and it is not invincible when it collides with an enemy, his/her number of lives should" +
                "be reduced", livesBeforeCollision - 1, player.getLivesRemaining());

    }

    @Test
    public void testSpawnSetOfHarderEnemies() throws Exception {

    }

    @Test
    public void testRestoreToBeforeCollision() throws Exception {

        player.setPreviousX(22);
        BrickWall wall = new BrickWall(58, 32, true, false);

        //Collision resolved by shifting in the x axis
        collisionManager.restoreToBeforeCollision(player, wall.getBounds());
        assertEquals("Collisions in the x axis are resolved by restoring the previous x coordinate", 22, player.getPosX());
        assertEquals("Collisions in the x axis do not restore the y coordinate to its previous value", 32, player.getPosY());

        //Collision resolved by shifting in the y axis
        wall.setPosX(32);
        wall.setPosY(64);
        player.setPosX(32);
        player.setPosY(38);
        player.setPreviousY(31);
        collisionManager.restoreToBeforeCollision(player, wall.getBounds());
        assertEquals("Collisions in the y axis are resolved by restoring restoring the previous y coordinate", 31, player.getPosY());
        assertEquals("Collisions in the y axis do not restore the x coordinate to its previous value", 32, player.getPosX());

        //Collision that need full restoration in other to be resolved.
        player.setPosX(32);
        player.setPosY(32);
        //player.setPreviousX();
        wall.setPosX(57);
        wall.setPosY(57);

    }
}