package Menu;


import Database.DatabaseController;
import SystemController.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-11-04.
 */
public class Login extends MenuTemplate {

    private String[] options = {"Login","Create Account","Exit"};
    private int currentChoice = 0;
    private MenuManager menuManager;

    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;

    public Login (MenuManager menuManager) {
        this.menuManager = menuManager;
    }


    @Override
    public void init() {

    }


    @Override
    public void draw(Graphics2D g) {

        //draw the title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("BOMBERMAN", 80, 70);
        g.drawString("Login Menu", 80, 100);

        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(MenuTemplate.BODY_COLOR);
            } else {
                g.setColor(MenuTemplate.BODY_SELECTED_COLOR);
            }

            // pass horizontal distance, then vertical distance
            g.drawString(options[i], 95, 140 + i * 15);

        }
    }

    private void select() {
        if (currentChoice == 0) {
            //LoginPopup lg = new LoginPopup(menuManager);
            //lg.setVisible(true);
            redirectToMainMenu();
            //loginClicked();
        }
        if (currentChoice == 1) {
            AccountCreationMenuPopUp acp = new AccountCreationMenuPopUp(menuManager);
            acp.setVisible(true);
        }
        if (currentChoice == 2) {
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

    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }

}

