package Menu;

import SystemController.GameState;
import SystemController.GameStateManager;

import java.awt.*;
import java.util.Hashtable;

/**
 * Created by danielmacario on 14-10-31.
 */
public class MenuManager extends GameState {

    private Hashtable<MenuState, MenuTemplate> menuStates;
    private MenuState currentMenu;

    private MenuState[] menuTypes= {
            MenuState.MAIN,
            MenuState.LOGIN,
            MenuState.ACCOUNTCREATION,
            MenuState.GAMEOVER,
            MenuState.LEADERBOARD,
            MenuState.LOADGAME,
            MenuState.SAVEGAME,
            MenuState.MODIFYACCOUNT
    };

    public MenuManager(GameStateManager gsm) {

        this.gsm = gsm;
        menuStates = new Hashtable<MenuState, MenuTemplate>();
        menuStates.put(MenuState.MAIN, new MainMenu(this, gsm));
        menuStates.put(MenuState.LOADGAME, new LoadGame(this, gsm));
        menuStates.put(MenuState.SAVEGAME, new SaveGame(this, gsm));
        menuStates.put(MenuState.LOGIN, new Login(this));
        currentMenu = MenuState.LOGIN;

    }

    public void setMenuState(MenuState state) {
        currentMenu = state;
        menuStates.get(currentMenu).init();
    }

    public void associatePlayerUserName (String username) {
        gsm.setPlayerUserName(username);
    }

    @Override
    public void draw(Graphics2D g) {
        menuStates.get(currentMenu).draw(g);
    }

    @Override
    public void keyPressed(int k) {
        menuStates.get(currentMenu).keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        menuStates.get(currentMenu).keyReleased(k);
    }


    @Override
    public void init() {

    }
}
