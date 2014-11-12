package Menu;
import SystemController.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Shabab Ahmed on 8th November 2014.
 */


//TODO: possibly delete this class entirely

public class AccountCreationMenu extends MenuTemplate{

    private String[] options = {"Fill in details","Exit"};
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;


    //Constructor
    public AccountCreationMenu (MenuManager menuManager, GameStateManager gsm) {

        this.menuManager = menuManager;
        this.gsm = gsm;
        titleColor = new Color(230, 200, 0);
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);

    }


    //Intiallizer
    @Override
    public void init() {
    }

    @Override
    public void draw(Graphics2D g) {
        //Title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setPaint(new Color(255,255,255));
        g.drawString("Account Creation", 80, 70);

        //Options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }
        g.drawString(options[i], 95, 120 + i * 15);

        }
    }

    private void select() {
        if (currentChoice == 0) {
            AccountCreationMenuPopUp acp = new AccountCreationMenuPopUp(menuManager);
            acp.setVisible(true);
        }
        if (currentChoice == 1) {
            //Exit the Game
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
