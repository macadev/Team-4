package GameObject.ArtificialIntelligence;

import GameObject.Player;
import GameObject.TileMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathFinderTest {

    @Test
    public void testUpdateGraphTimer() throws Exception {

    }

    @Test
    public void testFindPath() throws Exception {
        Player player = new Player(35, 35, true, 2);
        TileMap tileMap = new TileMap(player, 11, "testUser");
        PathFinder pathFinder = new PathFinder();
        pathFinder.findPath(32,160,32,96);
    }

    @Test
    public void testAStar() throws Exception {

    }

    @Test
    public void testEstimateDistance() throws Exception {

    }

    @Test
    public void testUpdateGraph() throws Exception {

    }

    @Test
    public void testGetRefreshGraph() throws Exception {

    }

    @Test
    public void testSetRefreshGraph() throws Exception {

    }

    @Test
    public void testCreateMap() throws Exception {

    }
}