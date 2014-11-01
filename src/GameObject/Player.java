package GameObject;

/**
 * Created by danielmacario on 14-10-31.
 */
public class Player extends MovableObject{

    private int score;

    public Player(int posX, int posY, boolean visible, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.visible = visible;
        this.speed = speed;
    }



}
