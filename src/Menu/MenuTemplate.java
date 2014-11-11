package Menu;

import SystemController.GameStateManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danielmacario on 14-10-31.
 */
public abstract class MenuTemplate extends JFrame {

    protected MenuManager menuManager;
    protected GameStateManager gsm;

    //JPlaceHolderTextField and JPlaceHolderPasswordField dimensions
    public static final int FIELD_WIDTH = 150;
    public static final int FIELD_HEIGHT = 30;

    //

    public abstract void init();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);


}
