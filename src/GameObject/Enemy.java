package GameObject;

import javax.swing.*;
import java.util.Random;

/**
 * Created by danielmacario on 14-11-12.
 */
public class Enemy extends MovableObject {

    private EnemyType enemyType;
    private Direction directionOfMovement;
    private static final int NORTH = 0;
    private static final int SOUTH = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;


    public Enemy(EnemyType type, int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = true;
        this.directionOfMovement = Direction.getRandomDirection();

        switch (type) {
            case BALLOOM:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/balloom.png")).getImage();
                this.score = 100;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                break;
            case DOLL:
                this.score = 400;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                break;
            case MINVO:
                this.score = 800;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                break;
            case OVAPI:
                this.score = 2000;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                break;
            case ONEAL:
                this.score = 200;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                break;
            case PASS:
                this.score = 4000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = false;
                break;
            case PONTAN:
                this.score = 8000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = true;
                break;
            case KONDORIA:
                this.score = 1000;
                //TODO: define 'slowest' speed
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                break;
        }

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void move() {
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

    public void death() {
        this.visible = false;
    }

}

