package Menu;

import Database.DatabaseController;
import SystemController.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Shabab Ahmed on 20/11/2014.
 */
public class AccountOptionsMenu extends MenuTemplate {

private String[] options = {"Update Information", "Delete Account", "Return to Main Menu"};
private int currentChoice = 0;
private Color titleColor = MenuTemplate.TITLE_COLOR;
private Font titleFont = MenuTemplate.TITLE_FONT;
private Font font = MenuTemplate.BODY_FONT;

public AccountOptionsMenu (MenuManager menuManager, GameStateManager gsm) {
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
        g.setPaint(titleColor);
        g.drawString("BOMBERMAN", 80, 70);
        g.drawString("Account Options Menu", 80, 100);

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
            //Update Information
        }
        if (currentChoice == 1) {
            //Delete Account
        }
        if (currentChoice == 2) {
            menuManager.setMenuState(MenuState.MAIN);
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