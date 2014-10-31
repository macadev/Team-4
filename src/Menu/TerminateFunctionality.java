/**
 * Created by danielmacario on 14-10-31.
 */
package Menu;

/**
 * Establishes the logic required to quit the game
 * and logout. It is extended by all the menus that allow
 * the player to quit and logout.
 */
public class TerminateFunctionality {

    public void logout() {
        //TODO: redirect to login screen once it has been implemented
    }

    /**
     * Terminate the game
     */
    public void quitGame() {
        System.exit(0);
    }
}
