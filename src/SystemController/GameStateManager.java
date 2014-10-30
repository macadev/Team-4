package SystemController;

import GamePlay.GamePlayManager;
import Menu.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int GAMEPLAY = 1;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();

        gameStates.add(new MenuState(this));
        gameStates.add(new GamePlayManager(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }


}