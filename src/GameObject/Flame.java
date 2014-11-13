/**
 * Created by danielmacario on 14-11-06.
 */
package GameObject;

import javax.swing.*;

/**
 * Flame Class. Used to represent the flame object that appears once a bomb explodes.
 */
public class Flame extends StaticObject {

    private int timeOnGrid;

    /**
     * A Flame object is defined by a position on the grid, and a boolean representing whether it is
     * visible or not. We keep track of the time it will remain visible in terms of number of frames
     * it will be rendered.
     * @param posX
     * @param posY
     * @param visible
     */
    public Flame(int posX, int posY, boolean visible) {
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.visible = visible;
        this.image = new ImageIcon(this.getClass().getResource("../resources/flames.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.timeOnGrid = 0;
    }

    /**
     * Increment the number of frames the object has been rendered on the grid. This function
     * keeps track of the number of frames that remain until the Flame object is removed from the grid.
     */
    public void incrementTimeOnGrid() {
        this.timeOnGrid++;
        if (timeOnGrid > 20) {
            this.visible = false;
        }
    }

}
