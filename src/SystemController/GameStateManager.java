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
        gameStates = new ArrayList<GameState>();
        gameStates.add(new MenuManager(this));
        gameStates.add(new GamePlayManager(this));
        currentState = MENUSTATE;
    }

    /**
     * Toggle the state of the game between Menu and GamePlay
     * @param state
     */
    public void setState(int state) {
        //current state is set to 0 by default
        currentState = state;
        gameStates.get(currentState).init();
    }

    /**
     * Call the draw method within the current game state Controller
     * at a given time
     * @param g
     */
    public void draw(Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }

    /**
     * Pass user input to the corresponding game state controller at a give
     * time
     * @param k
     */
    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    /**
     * Pass user input to the corresponding game state controller at a give
     * time
     * @param k
     */
    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }

}
