/**
 * Created by danielmacario on 14-11-06.
 */
package GameObject;

import GamePlay.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Used to represent the flame object that appears once a bomb explodes.
 */
public class Flame extends StaticObject implements Serializable {

    private int timeOnGrid;
    private int explosionOriginX;
    private int explosionOriginY;

    /**
     * A Flame object is defined by a position on the grid, and a boolean representing whether it is
     * visible or not. We keep track of the time it will remain visible in terms of number of frames
     * it will be rendered.
     * @param posX
     * @param posY
     * @param visible
     * @param explosionOriginX
     * @param explosionOriginY
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

    public int getExplosionOriginX() {
        return explosionOriginX;
    }

    public void setExplosionOriginX(int explosionOriginX) {
        this.explosionOriginX = explosionOriginX;
    }

    public int getExplosionOriginY() {
        return explosionOriginY;
    }

    public void setExplosionOriginY(int explosionOriginY) {
        this.explosionOriginY = explosionOriginY;
    }

    public Coordinate getExplosionOriginAsCoordinate() {
        return new Coordinate(explosionOriginX + 14, explosionOriginY + 14);
    }
}
