package GameObject.ArtificialIntelligence;

import GameObject.Direction;
import GameObject.Enemy;
import GameObject.EnemyType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtificialIntelligenceTest {
    private ArtificialIntelligence lowIntelligence, mediumIntelligence, highIntelligence;

    private Enemy enemy;

    @Before
    public void setup() {

        lowIntelligence = new LowIntelligence();
        mediumIntelligence = new MediumIntelligence();
        highIntelligence = new HighIntelligence();
        enemy = new Enemy(EnemyType.BALLOOM, 35, 35);
    }

    @Test
    public void testMove() throws Exception {


        int posY = enemy.getPosY();
        int posX = enemy.getPosX();
        int speed = enemy.getSpeed();

        Direction direction = enemy.getDirectionOfMovement();

        lowIntelligence.move(enemy);

        if (direction == Direction.NORTH) {
            assertEquals(posY - speed, enemy.getPosY());
            assertEquals(posX, enemy.getPosX());
        } else if (direction == Direction.SOUTH) {
            assertEquals(posY + speed, enemy.getPosY());
            assertEquals(posX, enemy.getPosX());
        } else if (direction == Direction.EAST) {
            assertEquals(posY, enemy.getPosY());
            assertEquals(posX + speed, enemy.getPosX());
        } else if (direction == Direction.WEST) {
            assertEquals(posY, enemy.getPosY());
            assertEquals(posX - speed, enemy.getPosX());
        }

         posY = enemy.getPosY();
         posX = enemy.getPosX();

        mediumIntelligence.move(enemy);
        // test if moved
        assertFalse(posX == enemy.getPosX() && posY == enemy.getPosY());

        posY = enemy.getPosY();
        posX = enemy.getPosX();
        highIntelligence.move(enemy);
        // test if moved
        assertFalse(posX == enemy.getPosX() && posY == enemy.getPosY());
}

    @Test
    public void testMoveEnemyOnBoard() throws Exception {
        int posY = enemy.getPosY();
        int posX = enemy.getPosX();
        int speed = enemy.getSpeed();
        Direction direction = enemy.getDirectionOfMovement();


        lowIntelligence.moveEnemyOnBoard(enemy);
        if (direction == Direction.NORTH) {
            assertEquals(posY - speed, enemy.getPosY());
            assertEquals(posX, enemy.getPosX());
        } else if (direction == Direction.SOUTH) {
            assertEquals(posY + speed, enemy.getPosY());
            assertEquals(posX, enemy.getPosX());
        } else if (direction == Direction.EAST) {
            assertEquals(posY, enemy.getPosY());
            assertEquals(posX + speed, enemy.getPosX());
        } else if (direction == Direction.WEST) {
            assertEquals(posY, enemy.getPosY());
            assertEquals(posX - speed, enemy.getPosX());
        }

    }

    @Test
    public void testReverseDirection() throws Exception {
        Direction direction = enemy.getDirectionOfMovement();

        lowIntelligence.reverseDirection(enemy);

        if (direction == Direction.NORTH) {
            assertEquals(Direction.SOUTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.SOUTH) {
            assertEquals(Direction.NORTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.EAST) {
            assertEquals(Direction.WEST, enemy.getDirectionOfMovement());
        } else if (direction == Direction.WEST) {
            assertEquals(Direction.EAST, enemy.getDirectionOfMovement());
        }

        direction = enemy.getDirectionOfMovement();
        mediumIntelligence.reverseDirection(enemy);

        if (direction == Direction.NORTH) {
            assertEquals(Direction.SOUTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.SOUTH) {
            assertEquals(Direction.NORTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.EAST) {
            assertEquals(Direction.WEST, enemy.getDirectionOfMovement());
        } else if (direction == Direction.WEST) {
            assertEquals(Direction.EAST, enemy.getDirectionOfMovement());
        }

        direction = enemy.getDirectionOfMovement();
        highIntelligence.reverseDirection(enemy);

        if (direction == Direction.NORTH) {
            assertEquals(Direction.SOUTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.SOUTH) {
            assertEquals(Direction.NORTH, enemy.getDirectionOfMovement());
        } else if (direction == Direction.EAST) {
            assertEquals(Direction.WEST, enemy.getDirectionOfMovement());
        } else if (direction == Direction.WEST) {
            assertEquals(Direction.EAST, enemy.getDirectionOfMovement());
        }

    }

}