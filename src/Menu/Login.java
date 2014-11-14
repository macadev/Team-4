package Menu;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-11-04.
 */
public class Login extends MenuTemplate {

    private String[] options = {"Login","Create Account","Exit"};
    private int currentChoice = 0;

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
        g.setPaint(new Color(255,255,255));
        g.drawString("BOMBERMAN", 80, 70);
        g.drawString("Login Menu", 80, 100);

        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.YELLOW);
            }

            // pass horizontal distance, then vertical distance
            g.drawString(options[i], 95, 140 + i * 15);

        }
    }

    private void select() {
        if (currentChoice == 0) {

//            LoginPopup loginPopUp = new LoginPopup(menuManager);
//            loginPopUp.setVisible(true);
            menuManager.setMenuState(MenuState.MAIN);

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
}
