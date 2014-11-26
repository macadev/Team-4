package GamePlay;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameObject.Enemy;
import GameObject.EnemyType;
import GameObject.KillSet;


import static org.junit.Assert.*;

public class ScoreManagerTest {

    private ArrayList<KillSet> kills = new ArrayList<KillSet>();
    private ScoreManager sm = new ScoreManager();

    @Before
    public void setup() throws Exception {
        kills.add(new KillSet(new Coordinate(0, 0), new Coordinate(0, 0), new Enemy(EnemyType.BALLOOM, 35, 35)));
        kills.add(new KillSet(new Coordinate(0, 0), new Coordinate(0, 0), new Enemy(EnemyType.BALLOOM, 35, 35)));

    }

    @Test
    public void testDetermineScoreFromKills() throws Exception {
        int result = sm.determineScoreFromKills(kills);
        assertEquals(result, 300);
    }

    @Test
    public void testCalculateScore() throws Exception {
        int result = sm.calculateScore(kills);
        assertEquals(result, 300);
    }

    @Test
    public void testDetermineScoreForManyKills() throws Exception {
        int result = sm.determineScoreForManyKills(kills);
        assertEquals(result, 300);
    }

    @Test
    public void testDetermineScoreForTwoKills() throws Exception {
        int result = sm.determineScoreForTwoKills(kills.get(0), kills.get(1));
        assertEquals(result, 300);
    }
}