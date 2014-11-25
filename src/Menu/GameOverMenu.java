package Menu;

import SystemController.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


 /**
  * This class is used to represent the options available to the player when the game is over/lost.The GameOverMenu class
  *  inherits most of its functionality from the MenuTemplate class.
  *  Specifically, the GameOverMenu class defines the logic for: starting a new game, loading a previously saved game,
  *  view their score on the leaderboard or return to the main menu for additional options.
  */

public class GameOverMenu extends MenuTemplate {

    private String[] options = {"Start New Game","Load Saved Game", "View Leaderboard", "Return to Main Menu"};
    // menu options as highlighted/mentioned in the SRS
    private int currentChoice = 0; // index of options array
    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;


    /** Creates a GameOverMenu object which takes the menuManager as a parameter.
     * @param menuManager Object navigates between the different game states and menus
     * depending on the currentChoice as chosen by the user.
     */
    public GameOverMenu (MenuManager menuManager) {
            this.menuManager = menuManager;
        }

        @Override
        public void init() {
            // TODO Auto-generated method stub
        }

     /**
      * Draws the GameOverMenu with the options when the game ends
      * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
      */

        @Override
        public void draw(Graphics2D g) { //the code in this method is used in every menu for consistency
            //draw the title
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.setPaint(titleColor);
            g.drawString("Game Over Menu", 80, 70); //

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
     /**
      * Implements the functionality for the user to scroll through the options with the direction
      * buttons on the keyboard.
      * @param k KeyCode used to represent the key pressed on the keyboard.
      */
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
     /**
      * Implements the functionality of each choice on the screen
      */

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
                menuManager.setPreviousLeaderboardMenuState(MenuState.GAMEOVER);
                menuManager.setMenuState(MenuState.LEADERBOARD);
            }
            if (currentChoice == 3) {
                //return to main menu
                menuManager.setMenuState(MenuState.MAIN);
            }
        }
    }

