package GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danielmacario on 14-11-02.
 */
public class Bomb extends StaticObject {

    private int radius;
    //stored in miliseconds
    private int time;
    private boolean firstCollision;

    private ActionListener listener = new ActionListener(){
        public void actionPerformed(ActionEvent event){
            explode();
        }
    };

    public Bomb(int posX, int posY) {
        this.time = 0;
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        this.image = new ImageIcon(this.getClass().getResource("../resources/bomb2.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.firstCollision = true;
        initiateCountdownToDetonation();
    }

    public synchronized void explode() {
        this.visible = false;
    }

    public void initiateCountdownToDetonation() {
        Timer bombTimer = new Timer(1500, listener);
        bombTimer.start();
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
