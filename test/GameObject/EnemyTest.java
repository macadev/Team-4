package GameObject;

import org.junit.Before;
import org.junit.Test;

import javax.management.StringValueExp;

import static org.junit.Assert.*;

public class EnemyTest {

    private Enemy enemy;
    @Before
    public void makeEnemy() {
        enemy = new Enemy(EnemyType.BALLOOM, 35, 35);
        enemy=new Enemy(EnemyType.ONEAL,35,35);
        enemy=new Enemy(EnemyType.DOLL,35,35);
        enemy=new Enemy(EnemyType.MINVO,35,35);
        enemy=new Enemy(EnemyType.KONDORIA,35,35);
        enemy=new Enemy(EnemyType.OVAPI,35,35);
        enemy=new Enemy(EnemyType.PASS,35,35);
        enemy=new Enemy(EnemyType.PONTAN,35,35);

    }

    @Test
    public void testMove() throws Exception {
    }

    @Test
    public void testReverseDirection() throws Exception {
    }

    @Test
    public void testChasePlayer() throws Exception {

    }

    @Test
    public void testDeath() throws Exception {
        assertTrue("Enemy is visible upon existence", enemy.isVisible());
        enemy.death();
        assertFalse("Enemy is no longer visible after dying", enemy.isVisible());
    }

    @Test
    public void testIsHitByFlames() throws Exception {

    }
}