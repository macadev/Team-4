package GameObject;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by danielmacario on 14-11-14.
 */
public class Door extends StaticObject implements Serializable {

    boolean firstCollision;

    public Door(int posX, int posY) {
        this.imagePath = "../resources/door.png";
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
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
