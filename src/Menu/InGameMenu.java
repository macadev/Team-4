package Menu;

import GameObject.Player;
import GamePlay.GamePlayState;
import SystemController.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * This class is used to represent the options available to the player when the game is paused. This menu to appear
 * when the player presses the space bar during game play. The InGameMenu class inherits most of its functionality from
 * the MenuTemplate class. Specifically, the InGameMenu class defines the logic for: continuing the same game, starting
 * another- new game,quitting the game entirely, go the main menu for additional options or view their score on the
 * leaderboard.
 */
public class InGameMenu extends MenuTemplate{ //this menu will appear when the user pauses the game

    private String[] options = {"Resume Gameplay","Save Game", "Start New Game", "Quit Game", "Exit to Main Menu", "View Leaderboard" };
    //options offered in the in-game menu as stated in the SRS
    private int currentChoice = 0; // index of the options array
    private Color titleColor = MenuTemplate.TITLE_COLOR;
    private Font titleFont = MenuTemplate.TITLE_FONT;
    private Font font = MenuTemplate.BODY_FONT;
    private GameStateManager gsm;


    /** Constructor for the InGameMenu class. Creates a InGameMenu object which takes both the menuManager and gsm
     * as a parameter.
     * @param menuManager Object navigates between the different menus
     * depending on the currentChoice as chosen by the player.
     *  @param gsm Object navigates between the different game-states
     * depending on the currentChoice as chosen by the user.
     */
    public InGameMenu(MenuManager menuManager, GameStateManager gsm) {
	    this.menuManager = menuManager;
        this.gsm = gsm;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

    /**
     * Draws the InGameMenu with the options when the game is paused
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
	@Override
	public void draw(Graphics2D g) { // method to ensure same formatting for every menu
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
            //resume game play
            gsm.setState(GameStateManager.GAMEPLAY, null);
	    }
	    if (currentChoice == 1) {
            //save game
            SaveGamePopUp svg = new SaveGamePopUp(menuManager);
            svg.setVisible(true);
        }
        if (currentChoice == 2) {
            //start new game
            LevelSelectionPopUp lsp = new LevelSelectionPopUp(menuManager);
            lsp.setVisible(true);
        }
        if (currentChoice == 3) {
            //quit game
            System.exit(0);
        }
        if (currentChoice == 4) {
            //exit to main menu
            menuManager.setMenuState(MenuState.MAIN);
        }
        if (currentChoice == 5) {
            //view leaderboard
            menuManager.setPreviousLeaderboardMenuState(MenuState.INGAME);
            menuManager.setMenuState(MenuState.LEADERBOARD);
        }
	}
}
