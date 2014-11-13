/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

import javax.swing.*;
import java.util.Random;

/**
 * Used to represent the enemy objects present in the game. Note that there are 8 differnt enemy types in the game.
 * Instead of creating 8 different classes to represent each enemy, we have a single dynamic structure that creates
 * an enemy object based on the EnemyType passed to the constructor.
 *
 * Enemies can have 4 different speed attributes, the capability of passing through walls, and also 3 different
 * levels of intelligence.
 */
public class Enemy extends MovableObject {

    private EnemyType enemyType;
    private Direction directionOfMovement;
    private Intelligence intelligence;

    /**
     * Dynamically generate the enemy object based on the EnemyType passed as argument.
     * @param type EnemyType to be created
     * @param posX x position of the enemy on the grid
     * @param posY y position of the enemy on the grid
     */
    public Enemy(EnemyType type, int posX, int posY) {

        this.enemyType = type;
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = true;
        //Randomly select a direction of movement upon creation
        this.directionOfMovement = Direction.getRandomDirection();

        //Depending on the EnemyTyped passed, we select a score attribute, image, level of intelligence, speed,
        //and whether the enemy can pass through walls or not.
        switch (type) {
            case BALLOOM:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/balloom.png")).getImage();
                this.score = 100;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = Intelligence.LOW;
                break;
            case DOLL:
                this.score = 400;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = Intelligence.LOW;
                break;
            case MINVO:
                this.score = 800;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = Intelligence.MEDIUM;
                break;
            case OVAPI:
                this.score = 2000;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = Intelligence.MEDIUM;
                break;
            case ONEAL:
                this.score = 200;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = Intelligence.MEDIUM;
                break;
            case PASS:
                this.score = 4000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = false;
                this.intelligence = Intelligence.HIGH;
                break;
            case PONTAN:
                this.score = 8000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = true;
                this.intelligence = Intelligence.HIGH;
                break;
            case KONDORIA:
                this.score = 1000;
                //TODO: define 'slowest' speed
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = Intelligence.HIGH;
                break;
        }

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    /**
     * Displaced the object in one of the four cardinal directions based on the directionOfMovement attribute
     */
    public void move() {

//        if (intelligence == Intelligence.LOW) {
//            if ((posX) % 30 <= 1 && (posX) % 30 <= 1 && (posX/30) % 2 == 1) {
//                directionOfMovement = Direction.NORTH;
//            }
//        }

        switch (directionOfMovement) {
            case NORTH:
                previousY = posY;
                posY -= speed;
                break;
            case SOUTH:
                previousY = posY;
                posY += speed;
                break;
            case EAST:
                previousX = posX;
                posX += speed;
                break;
            case WEST:
                previousX = posX;
                posX -= speed;
                break;
        }
    }

    /**
     * Sets the direction of movement to the opposite in terms of cardinal direction. For example, North would become
     * South, East would become West, etc.
     */
    public void reverseDirection() {
        switch (directionOfMovement) {
            case NORTH:
                directionOfMovement = Direction.SOUTH;
                break;
            case SOUTH:
                directionOfMovement = Direction.NORTH;
                break;
            case EAST:
                directionOfMovement = Direction.WEST;
                break;
            case WEST:
                directionOfMovement = Direction.EAST;
                break;
        }
    }

    /**
     * Disable the visibility of the object. This method gets called once the enemy has been killed
     * by a bomb explosion.
     */
    public void death() {
        this.visible = false;
    }

}

