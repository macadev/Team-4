/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

/**
 * Defines the commmon functionality shared by all the objects that can move
 * on the grid.
 */
public class MovableObject extends GameObject {

    public static final int SLOWSPEED = 2;
    public static final int NORMALSPEED = 3;
    public static final int FASTSPEED= 4;

    protected int speed;
    protected int deltaX;
    protected int deltaY;
    protected int score;
    protected boolean wallPass;


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

    public void restorePreviousPosition() {
        posX = previousX;
        posY = previousY;
    }

    public void restorePreviousXPosition() {
        posX = previousX;
    }

    public void restorePreviousYPosition() {
        posY = previousY;
    }

    public void restorePositionTo(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * Retrieve the speed attribute of the movable object
     * @return an integer representing the speed of the MovableObject
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Modify the speed attribute of the movable object
     * @param speed Set the speed of the movable object instance
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

    public boolean hasWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }
}
