package GameObject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BombTest {

    private Bomb bomb;

    @Before
    public void makeBomb() {
        bomb = new Bomb(35, 35);
    }

    @Test
    public void testTimeExplosion() throws Exception {

    }

    @Test
    public void testExplode() throws Exception {
        assertTrue("Bomb is visible when placed", bomb.isVisible());
        bomb.explode();
        assertFalse("Bomb is no longer visible after exploding", bomb.isVisible());
    }

    @Test
    public void testIsFirstCollision() throws Exception {

    }
}