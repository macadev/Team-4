package Menu;

import SystemController.GameStateManager;

import java.awt.*;

/**
 * Created by danielmacario on 14-10-31.
 */
public abstract class MenuTemplate {

    protected MenuManager menuManager;
    protected GameStateManager gsm;

    public abstract void init();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

}
