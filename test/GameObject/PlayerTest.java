package GameObject;

import GamePlay.GamePlayState;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    @Before
    public void makePlayer() {
        player = new Player(35, 35, true, MovableObject.NORMALSPEED);
    }

    @Test
    public void testDeath() throws Exception {
        assertTrue("Player is Visible upon creation", player.isVisible());
        player.death();
        assertFalse("Player is no longer visible after dying", player.isVisible());
    }

    @Test
    public void testDetonateLastBomb() throws Exception {

    }

    @Test
    public void testIncrementBombRadius() throws Exception {

    }

    @Test
    public void testIncrementSpeed() throws Exception {

    }

    @Test
    public void testDecrementLifesRemaining() throws Exception {
        int lifesRemaining = player.getLivesRemaining();
        GamePlayState gps = player.getCurrentGamePlayState();
        assertEquals("Upon spawing, the player has 2 lifes", 2, lifesRemaining);
        assertEquals("Upon spawing, the current state is set to in game", GamePlayState.INGAME, gps);
        player.decrementLivesRemaining();
        assertEquals("Decrement lifes remaining reduces lifes by one", lifesRemaining - 1, player.getLivesRemaining());
        player.decrementLivesRemaining();
        player.decrementLivesRemaining();
        gps = player.getCurrentGamePlayState();
        assertEquals("Negative lifes remaining sets the current state to game over", GamePlayState.GAMEOVER, gps);
    }

    @Test
    public void testEnablePowerUp() throws Exception {

    }

    @Test
    public void testDisablePowerUpsOnDeath() throws Exception {
        player.setWallPass(true);
        player.setFlamePass(true);
        player.setBombPass(true);
        player.setDetonatorEnabled(true);
        player.disablePowerUpsOnDeath();
        assertEquals("wall pass should be disabled", false, player.hasWallPass());
        assertEquals("flame pass should be disabled", false, player.hasFlamePass());
        assertEquals("bomb pass should be disabled", false, player.hasBombPass());
        assertEquals("detonator should be disabled", false, player.isDetonatorEnabled());

    }

    @Test
    public void testNextStage() throws Exception {
        player.setPosX(100);
        player.setPosY(50);
        player.nextStage();
        assertEquals("X Position of the player is reset to 35", 35, player.getPosX());
        assertEquals("Y Position of the player is reset to 35", 35, player.getPosY());
    }

    @Test
    public void testKeyPressed() throws Exception {
        //disable the controls if the player is dead
        int[] keys = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                       KeyEvent.VK_SPACE, KeyEvent.VK_X, KeyEvent.VK_Y};
        player.setVisible(false);
        player.keyPressed(KeyEvent.VK_UP);
        assertEquals("Delta Y value should not be modified if player is not visible", 0, player.getDeltaY());
        player.keyPressed(KeyEvent.VK_DOWN);
        assertEquals("Delta Y value should not be modified if player is not visible", 0, player.getDeltaY());
        player.keyPressed(KeyEvent.VK_LEFT);
        assertEquals("Delta X value should not be modified if player is not visible", 0, player.getDeltaX());
        player.keyPressed(KeyEvent.VK_RIGHT);
        assertEquals("Delta X value should not be modified if player is not visible", 0, player.getDeltaX());
        player.keyPressed(KeyEvent.VK_SPACE);
        assertEquals("Pause should not be allowed if player is not visible", GamePlayState.INGAME, player.getCurrentGamePlayState());
        player.setVisible(true);
        player.keyPressed(KeyEvent.VK_UP);
        assertEquals("Delta Y value should be modified if player is visible", -MovableObject.NORMALSPEED, player.getDeltaY());
        player.keyPressed(KeyEvent.VK_DOWN);
        assertEquals("Delta Y value should not be modified if player is not visible", MovableObject.NORMALSPEED, player.getDeltaY());
        player.keyPressed(KeyEvent.VK_LEFT);
        assertEquals("Delta X value should not be modified if player is not visible", -MovableObject.NORMALSPEED, player.getDeltaX());
        player.keyPressed(KeyEvent.VK_RIGHT);
        assertEquals("Delta X value should not be modified if player is not visible", MovableObject.NORMALSPEED, player.getDeltaX());
        player.keyPressed(KeyEvent.VK_SPACE);
        assertEquals("Pause should not be allowed if player is not visible", GamePlayState.PAUSE, player.getCurrentGamePlayState());

    }

    @Test
    public void testKeyReleased() throws Exception {

    }

    @Test
    public void testIncrementBombsAllowed() throws Exception {
        int bombsAllowed = player.getBombsAllowed();
        player.incrementBombsAllowed();
        assertEquals("Bombs allowed increases by 1 upon calling the method", bombsAllowed + 1, player.getBombsAllowed());
        player.setBombsAllowed(10);
        player.incrementBombsAllowed();
        assertEquals("Bombs allowed will not increase beyond 10", 10, player.getBombsAllowed());
    }

    @Test
    public void testAddToScore() throws Exception {
        int score = player.getScore();
        int increment = 100;
        player.addToScore(increment);
        assertEquals("Score increases by specified amount", score + increment, player.getScore());
    }
}