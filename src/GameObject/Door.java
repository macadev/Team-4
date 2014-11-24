package GameObject;

import javax.swing.*;
import java.io.Serializable;

/**
 * This class creates the object door. It contains logic of spawning harder enemies when hit by a bomb explosion.
 */
public class Door extends StaticObject implements Serializable {

    boolean firstCollision;

    /**
     * Constructor for Door class. Contains the image representing the door.
     * @param posX Position X of the door in the grid
     * @param posY Position Y of the door in the grid
     */
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

    /**
     * Spawns a harder set of enemies when door is hit with a bomb explosion
     */
    public void hitByExplosion() {
        //TODO: spawn harder wave of enemies....
    }

    /**
     * Determine whether the door is colliding with the bomb for the first time
     * or not.
     * @return A boolean representing whether this is the first time the door
     * is colliding with the the Bomb
     */
    public boolean isFirstCollision() {
        return firstCollision;
    }

    /**
     * Set the first collision attribute of the door object.
     * @param firstCollision boolean representing whether this door object has collided
     * with the bomb instance in the past.
     */
    public void setFirstCollision(boolean firstCollision) {
        this.firstCollision = firstCollision;
    }

}
