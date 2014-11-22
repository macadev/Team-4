/**
 * Created by danielmacario on 14-10-31.
 */
package GameObject;

import java.io.Serializable;

/**
 * Defines the commmon functionality shared by all the objects that can move
 * on the grid.
 */
public class MovableObject extends GameObject implements Serializable {

    public static final int SLOWSPEED = 2;
    public static final int NORMALSPEED = 3;
    public static final int FASTSPEED= 4;

    protected int speed;
    protected int deltaX;
    protected int deltaY;
    protected int score;
    protected boolean wallPass;


    /**
     * Displaces the object on the grid based on the values of the deltaX
     * and the deltaY variables.
     */
    public void move() {
        previousX = posX;
        previousY = posY;
        posX += deltaX;
        posY += deltaY;
    }

    /**
     * Displaces the object on the y axis based on the value of the posY variable.
     */
    public void moveVertically() {
        posY += deltaY;
        previousY = posY;
    }

    /**
     * Restores the position of the Movable Object to what it was in the previous frame.
     */
    public void restorePreviousPosition() {
        posX = previousX;
        posY = previousY;
    }

    /**
     * Restores the x position of the Movable Object to what it was in the previous frame.
     */
    public void restorePreviousXPosition() {
        posX = previousX;
    }

    /**
     * Restores the y position of the Movable Object to what it was in the previous frame.
     */
    public void restorePreviousYPosition() {
        posY = previousY;
    }

    /**
     * Restores the position of the object to a specific set of coordinates
     * @param x An integer representing the x coordinate on the grid where the Movable Object
     *          will be rendered.
     * @param y An integer representing the y coordinate on the grid where the Movable Object
     *          will be placed.
     */
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

    /**
     * Get the wallPass attribute associated with the MovableObject
     * @return A boolean specifying whether the object can pass through
     * brick walls or not.
     */
    public boolean hasWallPass() {
        return wallPass;
    }

    /**
     * Set the wallPass atttribute associated with the MovableObject
     * @param wallPass A boolean specifying whether the object can pass
     *                 through brick walls or not.
     */
    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
