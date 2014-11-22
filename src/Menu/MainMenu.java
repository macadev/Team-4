package Menu;

import SystemController.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-10-31.
 */
public class MainMenu extends MenuTemplate {

    private String[] options = {"Start Game",
            "Load Game",
            "View Leaderboards",
            "Modify Account Information",
            "Logout",
            "Quit"};
    private int currentChoice = 0;
    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;

    public MainMenu (MenuManager menuManager, GameStateManager gsm) {
        this.menuManager = menuManager;
        this.gsm = gsm;

    }

    @Override
    public void init() {

    }

    @Override
    public void draw(Graphics2D g) {

        //draw the title
        g.setColor(titleColor);
        g.setFont(titleFont);
        //g.setPaint(titleColor);
        g.drawString("BOMBERMAN", 80, 70);
        g.drawString("Main Menu", 80, 100);

        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(MenuTemplate.BODY_COLOR);
            } else {
                g.setColor(MenuTemplate.BODY_SELECTED_COLOR);
            }
            // pass horizontal distance, then vertical distance
            g.drawString(options[i], X_OFFSET, Y_OFFSET + i * 15);
        }

    }


    private void select() {
        if (currentChoice == 0) {
            //gsm.startNewGame();
            LevelSelectionPopUp lsp = new LevelSelectionPopUp(menuManager);
            lsp.setVisible(true);
        }
        if (currentChoice == 1) {
            LoadGamePopUp acp = new LoadGamePopUp(menuManager);
            acp.setVisible(true);
        }
        if (currentChoice == 2) {
            //view leaderboards
        }
        if (currentChoice == 3) {
           // menuManager.setMenuState(MenuState.MODIFYACCOUNT);
            AccountOptionsPopUp modAccPop = new AccountOptionsPopUp(menuManager);
            modAccPop.setVisible(true);
        }
        if (currentChoice == 4) {
            //logout
            menuManager.setMenuState(MenuState.LOGIN);
        }
        if (currentChoice == 5) {
            //terminate the game
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
