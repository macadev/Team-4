/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

/**
 * Defines the commmon functionality shared by all the objects that can move
 * on the grid.
 */
public class MovableObject extends GameObject {

    protected int speed;
    protected int deltaX;
    protected int deltaY;

    /**
     * Displaces the object on the grid in the specified motion.
     * The size of the displacement depends on the speed.
     */
    public void move() {
        previousX = posX;
        previousY = posY;
        posX += deltaX;
        posY += deltaY;
    }


    public void moveVertically() {
        posY += deltaY;
        previousY = posY;
    }

    /**
     * Retrieve the speed attribute of the movable object
     * @return
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Modify the speed attribute of the movable object
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }
}
