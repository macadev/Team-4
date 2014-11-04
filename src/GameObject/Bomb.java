package GameObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danielmacario on 14-11-02.
 */
public class Bomb extends StaticObject {

    private int radius;
    //stored in miliseconds
    private int time;
    private boolean firstCollision;

    public Bomb(int posX, int posY) {
       this.time = 0;
       this.posX = posX;
       this.posY = posY;
       this.visible = true;
       this.image = new ImageIcon(this.getClass().getResource("../resources/bomb2.png")).getImage();
       this.width = image.getWidth(null);
       this.height = image.getHeight(null);
       this.firstCollision = true;
       detonationCountDown();
    }

    public void explode() {
        this.visible = false;
    }

    public void detonationCountDown() {
        if (time > 100) explode();
        time++;
    }

    public void draw(Graphics2D g) {
        if (visible) g.drawImage(image, posX, posY, null);
    }

    public boolean isFirstCollision() {
        return firstCollision;
    }

    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }
}
