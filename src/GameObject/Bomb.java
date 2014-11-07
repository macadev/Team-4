package GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-11-02.
 */
public class Bomb extends StaticObject {

    //Used to allow the player to walk over the bomb
    //only when he is standing over it during the initial
    //placement
    private boolean firstCollision;

    /**
     * Action listener used to trigger the bomb explosion after a specific amount
     * of time has passed since its placement
     */
    private ActionListener listener = new ActionListener(){
        public void actionPerformed(ActionEvent event){
            explode();
        }
    };

    /**
     * Bomb constructor. Contains the image that represents the structure
     * and it defines the logic that is modified after placement.
     * @param posX
     * @param posY
     */
    public Bomb(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.visible = true;
        this.image = new ImageIcon(this.getClass().getResource("../resources/bomb2.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.firstCollision = true;
        initiateCountdownToDetonation();
    }

    /**
     * Toggle the visibility of the object to false.
     * The bomb will be deleted in the next refresh of the screen
     */
    public synchronized void explode() {
        this.visible = false;
    }

    /**
     * Starts the timer of 2500 miliseconds after which an explosion
     * event will be emitted to trigger the bomb explosion logic.
     */
    public void initiateCountdownToDetonation() {
        Timer bombTimer = new Timer(2500, listener);
        bombTimer.start();
    }

    /**
     * draw the bomb on the game grid
     * @param g
     */
    public void draw(Graphics2D g) {
        if (visible) g.drawImage(image, posX, posY, null);
    }

    /**
     * determine wether the player is colliding with the bomb for the first time
     * or not
     * @return
     */
    public boolean isFirstCollision() {
        return firstCollision;
    }

    /**
     * Set the first collision attribute of the bomb object
     * @param firstCollision
     */
    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }
}
