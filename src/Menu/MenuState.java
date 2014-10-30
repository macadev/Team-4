package Menu;

import SystemController.GameState;
import SystemController.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-10-29.
 */
public class MenuState extends GameState {

    private String[] options = {"Start", "Help", "Quit"};
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        titleColor = new Color(230, 200, 0);
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {

        //draw the title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setPaint(new Color(255,255,255));
        g.drawString("Bomberman!", 80, 70);

        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    private void select() {
        if (currentChoice == 0) {
            gsm.setState(GameStateManager.GAMEPLAY);
        }
        if (currentChoice == 1) {
            //help
        }
        if(currentChoice == 2) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) currentChoice  = options.length - 1;
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) currentChoice = 0;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
