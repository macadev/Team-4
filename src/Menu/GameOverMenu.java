package Menu;

import SystemController.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * Created by Vasu on 16/11/2014.
 */
public class GameOverMenu extends MenuTemplate {

    private String[] options = {"Start New Game","Load Saved Game", "View Leaderboard", "Return to Main Menu"};
    // menu options as highlighted in the SRS
    private int currentChoice = 0; // index of options array
    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;


    public GameOverMenu (MenuManager menuManager) {
            this.menuManager = menuManager;
        }

        @Override
        public void init() {
            // TODO Auto-generated method stub
        }

        @Override
        public void draw(Graphics2D g) { //the code in this method is used in every menu for consistency
            //draw the title
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.setPaint(titleColor);
            g.drawString("Game Over Menu", 80, 70);

            //draw menu options
            g.setFont(font);
            for (int i = 0; i < options.length; i++) {
                if (i == currentChoice) {
                    g.setColor(MenuTemplate.BODY_COLOR);
                } else {
                    g.setColor(MenuTemplate.BODY_SELECTED_COLOR);
                }
                // pass horizontal distance, then vertical distance
                g.drawString(options[i], X_OFFSET, Y_OFFSET + i * 15);
            }
        }

            @Override
        public void keyPressed(int k) {
            // TODO Auto-generated method stub
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
            // TODO Auto-generated method stub

        }

        public void select() {
            if (currentChoice == 0) {
                //start new game
                LevelSelectionPopUp lsp = new LevelSelectionPopUp(menuManager); // level selection pops up
                lsp.setVisible(true);
            }
            if (currentChoice == 1) {
                //load saved game
                LoadGamePopUp acp = new LoadGamePopUp(menuManager); //options of saved game pops up
                acp.setVisible(true);
            }
            if (currentChoice == 2) {
                //view leaderboard
                menuManager.setMenuState(MenuState.LEADERBOARD);
            }
            if (currentChoice == 3) {
                //return to main menu
                menuManager.setMenuState(MenuState.MAIN);
            }
        }
    }

