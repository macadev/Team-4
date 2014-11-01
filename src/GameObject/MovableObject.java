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
     * @param motion
     */
    public void move(Motion motion) {
        switch (motion) {
            case UP:
                this.posY = posY + speed;
                break;
            case DOWN:
                this.posY = posY - speed;
                break;
            case LEFT:
                this.posX = posX - speed;
                break;
            case RIGHT:
                this.posX = posX + speed;
                break;
        }
    }

}
