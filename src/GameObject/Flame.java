/**
 * Created by danielmacario on 14-11-06.
 */
package GameObject;

import GamePlay.Coordinate;

import javax.swing.*;
import java.io.Serializable;

/**
 * Used to represent the flame object that appears once a bomb explodes.Flames increase the explosion range of all
 * bombs by 1 square in each direction
 */
public class Flame extends StaticObject implements Serializable {

    private int timeOnGrid;
    private int explosionOriginX;
    private int explosionOriginY;

    /**
     * A Flame object is defined by a position on the grid, and a boolean representing whether it is
     * visible or not. We keep track of the time it will remain visible in terms of number of frames
     * it will be rendered.
     * @param posX x position of the flame on the grid
     * @param posY y position of the flame on the grid
     * @param visible boolean representing whether the flame can be seen or not
     * @param explosionOriginX x position of where the explosion occurred
     * @param explosionOriginY y position of where the explosion occurred
     */
    public Flame(int posX, int posY, boolean visible, int explosionOriginX, int explosionOriginY) {
        this.imagePath = "../resources/flames.png";
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.visible = visible;
        this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
        this.timeOnGrid = 0;
        this.explosionOriginX = explosionOriginX;
        this.explosionOriginY = explosionOriginY;
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

    /**
     * Attains the x position of where the explosion occurred
     * @return the x-position of explosion
     */
    public int getExplosionOriginX() {
        return explosionOriginX;
    }

    /**
     * Assigns the x position of where the explosion will occur
     * @param explosionOriginX ,sets the previous explosion x-position to the new assigned int value
     */
    public void setExplosionOriginX(int explosionOriginX) {
        this.explosionOriginX = explosionOriginX;
    }

    /**
     * Attains the y position of where the explosion occurred
     * @return the y position of explosion
     */
    public int getExplosionOriginY() {
        return explosionOriginY;
    }

    /**
     *  * Assigns the x-coordinate position of where the explosion will occur
     * @param explosionOriginY, sets the previous explosion y-coordinate to the new assigned int value
     */
    public void setExplosionOriginY(int explosionOriginY) {
        this.explosionOriginY = explosionOriginY;
    }

    /**
     * Attains the origin of the explosion by the combining the explosion X and explosion Y parts
     * @return the coordinate based on both x+14 and y+14 as these are the centres 
     */
    public Coordinate getExplosionOriginAsCoordinate() {
        return new Coordinate(explosionOriginX + 14, explosionOriginY + 14);
    }
}
