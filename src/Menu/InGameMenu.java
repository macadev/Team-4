package Menu;

import GameObject.Player;
import GamePlay.GamePlayState;
import SystemController.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class InGameMenu extends MenuTemplate{

    private String[] options = {"Resume Gameplay","Save Game", "Start New Game", "Quit Game", "Exit to Main Menu", "View Leaderboard" };
    private int currentChoice = 0;
    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;
    private GameStateManager gsm;


    public InGameMenu(MenuManager menuManager, GameStateManager gsm) {
	    this.menuManager = menuManager;
        this.gsm = gsm;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics2D g) {
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setPaint(titleColor);
        g.drawString("In-Game Menu", 80, 70);


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
            gsm.setState(GameStateManager.GAMEPLAY, null);
	    }
	    if (currentChoice == 1) {
            //save game
            //menuManager.setMenuState(MenuState.SAVEGAME);
            menuManager.saveGame();
        }
        if (currentChoice == 2) {
            //start new game
            menuManager.setMenuState(MenuState.LOADGAME);
        }
        if (currentChoice == 3) {
            //quit game
            menuManager.setMenuState(MenuState.GAMEOVER);
        }
        if (currentChoice == 4) {
            //exit to main menu
            menuManager.setMenuState(MenuState.MAIN);
        }
        if (currentChoice == 5) {
            //view leaderboard
            menuManager.setMenuState(MenuState.LEADERBOARD);
        }
	        
	}
	

}
