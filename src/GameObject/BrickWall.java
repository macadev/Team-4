package GameObject;

import javax.swing.*;

/**
 * Created by danielmacario on 14-11-02.
 */
public class BrickWall extends StaticObject {

    private boolean hasPowerUp;

    public BrickWall(int posX, int posY, boolean visible, boolean hasPowerUp) {
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.visible = visible;
        this.image = new ImageIcon(this.getClass().getResource("../resources/brickBlock2.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.hasPowerUp = hasPowerUp;
    }

    public void disappear() {
        this.visible = false;
    }

}
