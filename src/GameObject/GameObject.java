package GameObject;

import java.awt.*;

/**
 * Created by danielmacario on 14-10-31.
 */
public class GameObject {

    //Actual coordinates on the grid
    protected int posX;
    protected int posY;

    //coordinates used once the objects start moving on the grid
    //to keep track of the position in reference to the original ones
    protected int virtualX;
    protected int virtualY;

    protected int previousX;
    protected int previousY;
    protected int width;
    protected int height;

    protected Image image;
    protected boolean visible;

    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVirtualX() {
        return virtualX;
    }

    public void setVirtualX(int virtualX) {
        this.virtualX = virtualX;
    }

    public int getVirtualY() {
        return virtualY;
    }

    public void setVirtualY(int virtualY) {
        this.virtualY = virtualY;
    }

    public int getPreviousX() {
        return previousX;
    }

    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void setPreviousY(int previousY) {
        this.previousY = previousY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
