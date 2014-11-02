package GameObject;
import javax.swing.*;

/**
 * Created by danielmacario on 14-11-01.
 */
public class ConcreteWall extends StaticObject {

    public ConcreteWall(int posX, int posY, boolean visible) {
        this.posX = posX;
        this.posY = posY;
        this.visible = visible;
        this.image = new ImageIcon(this.getClass().getResource("../resources/concreteBlock.png")).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

}
