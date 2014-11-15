package GameObject;

import javax.swing.*;

/**
 * Created by danielmacario on 14-11-14.
 */
public class Door extends StaticObject {

    boolean firstCollision;

    public Door(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        this.image = new ImageIcon(this.getClass().getResource("../resources/door.png")).getImage();
        this.firstCollision = true;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void hitByExplosion() {
        //TODO: spawn harder wave of enemies....
    }

    public boolean isFirstCollision() {
        return firstCollision;
    }

    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }

}
