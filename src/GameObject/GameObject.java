package GameObject;

import java.awt.*;

/**
 * Created by danielmacario on 14-10-31.
 */
public class GameObject {

    //Actual coordinates on the grid
    protected int posX;
    protected int posY;
    protected int previousX;
    protected int previousY;
    protected int width;
    protected int height;

    protected Image image;
    protected boolean visible;

    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(posX + 2, posY + (height) - 5, width - 4, 5);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(posX + 2, posY, width - 4, 5);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(posX + width - 5, posY + 2, 5, height - 4);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(posX, posY + 2, 5, height - 4);
    }

    public Rectangle getBoundsTopRightCorner() {
        return new Rectangle(posX, posY + 4, 5, height - 8);
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
