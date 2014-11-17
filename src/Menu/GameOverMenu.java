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
        private int currentChoice = 0;

        public GameOverMenu (MenuManager menuManager) {
            this.menuManager = menuManager;
        }

        @Override
        public void init() {
            // TODO Auto-generated method stub
        }

        @Override
        public void draw(Graphics2D g) {
            g.setColor(TITLE_COLOR);
            g.setFont(TITLE_FONT);
            g.setPaint(new Color(255,255,255));
            //g.drawString("", 80, 70);
           // g.drawString("BOMBERMAN", 80, 70);
            //g.drawString("Start New Game", 80, 100);

            //draw menu options
            g.setFont(TITLE_FONT);
            for(int i = 0; i < options.length; i++) {
                if (i == currentChoice) {
                    g.setColor(BODY_SELECTED_COLOR);
                } else {
                    g.setColor(BODY_COLOR);
                }
                // pass horizontal distance, then vertical distance
                g.drawString(options[i], 95, 120 + i * 15);
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
                menuManager.setMenuState(MenuState.LOADGAME);
            }
            if (currentChoice == 1) {
                menuManager.setMenuState(MenuState.SAVEGAME);
            }
            if (currentChoice == 2) {
                menuManager.setMenuState(MenuState.LEADERBOARD);
            }
            if (currentChoice == 3) {
                menuManager.setMenuState(MenuState.MAIN);
            }


        }


    }

