package GameObject;

/**
 * Created by danielmacario on 14-10-31.
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
        posX += deltaX;
        posY += deltaY;
    }

}
