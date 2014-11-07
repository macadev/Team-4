/**
 * Created by danielmacario on 14-11-01.
 */
package GameObject;

import javax.swing.*;

/**
 * ConcreteWall Class. Used to represent the ConcreteWall objects placed on the grid at fixed
 * positions during GamePlay. Note that this object falls in the category of static objects,
 * and it provides no other logic than the one needed for collision detection.
 */
public class ConcreteWall extends StaticObject {

    /**
     * ConcreteWall constructor. Contains position attributes of the object.
     * @param posX x position of the ConcreteWall on the grid
     * @param posY y position of the ConcreteWall on the grid
     */
    public ConcreteWall(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.image = new ImageIcon(this.getClass().getResource("../resources/concreteBlock.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

}
