package Menu;

import SystemController.GameStateManager;

import java.awt.*;

/**
 * Created by danielmacario on 14-10-31.
 */
public abstract class MenuTemplate {

    protected Color titleColor = new Color(230, 200, 0);
    protected Font titleFont = new Font("Gill Sans Ultra Bold", Font.PLAIN, 28);
    protected Font font = new Font("Gill Sans Ultra Bold", Font.PLAIN, 12);
    protected MenuManager menuManager;
    protected GameStateManager gsm;

    public abstract void init();
    public abstract void draw(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

}
