/**
 * Created by danielmacario on 14-10-29.
 */
package SystemController;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
 * GameController manages the single thread of the application.
 * It also trickles user inputs to the corresponding game state the user
 * is in. It also sets up the top-most visual layer of the application.
 */
public class GameController extends JPanel implements Runnable, KeyListener {

    //Screen setting variables
    public static final int WIDTH = 480;
    public static final int HEIGHT = 416;

    // Game thread variables
    private Thread thread;
    private boolean running;
    private int FPS = 30;
    private long targetTime = 1000 / FPS;

    // Rendering variables
    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;

    /**
     * Initialize the screen setting to be used by the JFrame container
     * that will render the views.
     */
    public GameController() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        setBackground(Color.BLACK);
        requestFocus();
    }

    /**
     * Starts the thread used to listen for keyboard inputs and other events
     * that control the logic of the game
     */
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    /**
     * Method called to start the main thread of the application.
     * It controls the game loop that refreshes the screen at a set frame rate
     */
    public void run() {
        long start;
        long elapse;
        long wait;

        init();

        //Game loop - it refreshes the views and updates the models
        //based on the user inputs
        while (running) {

            g.setPaint(new Color(10, 176,0));
            g.fillRect(0, 0, image.getWidth(), image.getHeight() );

            start = System.nanoTime();

            //Tell the current view to update its model and render its view
            updateAndDraw();

            elapse  = System.nanoTime() - start;
            wait = Math.abs(targetTime - elapse / 1000000);

            //This block is set to cap the refresh rate at 30 fps
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Creates the instance of the GameStateManager that will control
     * in which fundamental state the game is in: MENU or GAMEPLAY
     */
    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        g.setPaint(new Color(0,0,200));
        g.fillRect(0, 0, image.getWidth(), image.getHeight() );
        running = true;
        gsm = new GameStateManager();
    }

    /**
     * Update the current model, and draw its corresponding view.
     * The GSM will trickle down the draw signal to the current
     * menu or the gameplay state
     */
    private void updateAndDraw() {
        gsm.draw(g);
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}

    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }



}
