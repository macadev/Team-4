package GamePlay;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    Coordinate coordinate1;
    Coordinate coordinate2;
    Coordinate coordinate3;
    Object coordinate4;

    @Before
    public void setup() throws Exception {
        coordinate1 = new Coordinate(0, 0);
        coordinate2 = new Coordinate(1, 1);
        coordinate3 = new Coordinate(0, 0);
        coordinate4 = new Object();
    }


    @Test
    public void testEquals() throws Exception {
        assertEquals(coordinate1.equals(coordinate1), true);
        assertEquals(coordinate1.equals(coordinate4), false);
        assertEquals(coordinate1.equals(coordinate2), false);
        assertEquals(coordinate1.equals(coordinate3), true);
    }

    @Test
    public void testHashCode() throws Exception {
        int firstHash = coordinate1.hashCode();
        int secondHash = coordinate3.hashCode();

        assertEquals(firstHash, -4);
        assertEquals(firstHash, secondHash);
    }


    @Test
    public void testDistanceTo() throws Exception {
        int result = coordinate1.distanceTo(coordinate2);
        assertEquals(result, (int) Math.sqrt((0 - 1) * (0 - 1) + (0 - 1) * (0 - 1)));
    }
}