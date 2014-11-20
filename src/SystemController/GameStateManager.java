/**
 * Created by danielmacario on 14-10-29.
 */
package SystemController;

import GamePlay.GamePlayManager;
import Menu.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Controller used to switch between the two states of the application:
 * Menu or GamePlay. Also acts as a channel of communication for telling
 * views to render new elements and passing user inputs.
 */
public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;
    private MenuManager menuManager;
    private GamePlayManager gamePlayManager;
    private GameFileManager fileManager;
    private String playerUserName;

    /**
     * This integers are used as constants to represent the state the game can be in at
     * a given time
     */
    public static final int MENUSTATE = 0;
    public static final int GAMEPLAY = 1;

    /**
     * Class constructor. Creates an ArrayList used to contain instances of the
     * fundamental controllers of the game: the MenuManager and the GamePlayManager
     */
    public GameStateManager() {
        menuManager = new MenuManager(this);
        gamePlayManager = new GamePlayManager(this);
        fileManager = new GameFileManager(playerUserName);
        currentState = MENUSTATE;
    }

    /**
     * Toggle the state of the game between Menu and GamePlay
     * @param state
     */
    public void setState(int state, MenuState menuState) {
        //current state is set to 0 by default
        if (state == GAMEPLAY) {
            if (gamePlayManager.isGameOver()) {
                gamePlayManager = new GamePlayManager(this);
            }
            currentState = state;
        } else {
            currentState = MENUSTATE;
            menuManager.setMenuState(menuState);
        }
    }

    public void loadGame() {
        GamePlayManager loadedFile = fileManager.loadGame();
        if (loadedFile != null) {
            loadedFile.setGamePlayStateToInGame();
            gamePlayManager = loadedFile;
            gamePlayManager.setGameStateManager(this);
            currentState = GAMEPLAY;
        }
    }

    public void saveGame() {
        fileManager.saveGame(gamePlayManager);
    }

    /**
     * Call the draw method within the current game state Controller
     * at a given time
     * @param g
     */
    public void draw(Graphics2D g) {
        if (currentState == GAMEPLAY) {
            gamePlayManager.draw(g);
        } else {
            menuManager.draw(g);
        }
    }

    /**
     * Pass user input to the corresponding game state controller at a give
     * time
     * @param k
     */
    public void keyPressed(int k) {
        if (currentState == GAMEPLAY) {
            gamePlayManager.keyPressed(k);
        } else {
            menuManager.keyPressed(k);
        }
    }

    /**
     * Pass user input to the corresponding game state controller at a give
     * time
     * @param k
     */
    public void keyReleased(int k) {
        if (currentState == GAMEPLAY) {
            gamePlayManager.keyReleased(k);
        } else {
            menuManager.keyReleased(k);
        }
    }

    public String getPlayerUserName() {
        return playerUserName;
    }

    public void setPlayerUserName(String playerUserName) {
        this.playerUserName = playerUserName;
    }
}
