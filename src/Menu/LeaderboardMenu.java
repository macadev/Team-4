/**
 * Created by FloMac on 11/23/14.
 */
package Menu;

import Database.DatabaseController;
import Database.PlayerScore;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The LeaderboardMenu class displays the high scores of the top ten users. It is updated with the progress of each
 * user in the game. This menu inherits methods from MenuTemplate. If there are less than ten users in the
 * database, it displays however many there are.
 */
public class LeaderboardMenu extends MenuTemplate {


    private MenuManager menuManager;
    private ArrayList<String> userNames;
    private ArrayList<PlayerScore> topScores = null;
    private PlayerScore playerScore = null;
    private boolean loadScoresFlag = false;
    private int columnPos = 150;
    private int rowPos = 30;
    private MenuState previousMenuState;

    /**
     * Intializes a LeaderboardMenu.
     * @param menuManager MenuManager instance is passed to navigate and redirect to
     *                    other menus.
     */
    public LeaderboardMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.loadScoresFlag = true;
    }

    /**
     * Draws the leader board.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    @Override
    public void draw(Graphics2D g) {
        if (loadScoresFlag) {
            try {
                topScores = DatabaseController.getTopScoresSet();
                playerScore = DatabaseController.getPlayerObject(menuManager.getPlayerUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadScoresFlag = false;
        }

        //draw the title
        g.setColor(MenuTemplate.TITLE_COLOR);
        g.setFont(MenuTemplate.TITLE_FONT);
        g.setPaint(MenuTemplate.TITLE_COLOR);
        g.drawString("Leaderboards", 150, 70);

        g.setColor(MenuTemplate.BODY_SELECTED_COLOR);
        g.setFont(MenuTemplate.BODY_FONT);
        renderLegend(g);
        renderTopTen(g);
        renderPlayerScore(g);
        renderGoBackMessage(g);
    }

    /**
     * Draws the legend for the leader board indicating what each column represents.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void renderLegend(Graphics2D g) {
        g.drawString("Rank", rowPos - 15, columnPos - 30);
        g.drawString("UserName", rowPos + 35, columnPos - 30);
        g.drawString("Real Name", rowPos + 150, columnPos - 30);
        g.drawString("Score", rowPos + 270, columnPos - 30);
        g.drawString("Games", rowPos + 380, columnPos - 30);
    }

    /**
     * Draws the users with the top ten scores.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void renderTopTen(Graphics2D g) {
        //draw high scores

        userNames = new ArrayList<String>();
        for (PlayerScore score: topScores) {
            userNames.add(score.username);
        }

        PlayerScore boardData = null;
        for (int i = 0 ; i < 10; i++) {
            if (i >= topScores.size()) break;
            boardData = topScores.get(i);
            g.drawString(String.valueOf(i + 1), rowPos - 10, columnPos + i * 15);

            fixLength(boardData);

            g.drawString(boardData.username, rowPos + 20, columnPos + i * 15);
            g.drawString(boardData.realName, rowPos + 150, columnPos + i * 15);
            g.drawString(String.valueOf(boardData.score), rowPos + 270, columnPos + i * 15);
            g.drawString(String.valueOf(boardData.gamesPlayed), rowPos + 380, columnPos + i * 15);
        }
    }

    /**
     *
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void renderPlayerScore(Graphics2D g) {
        g.drawString("Your rank:", rowPos, 310);

        int rank = userNames.indexOf(playerScore.username) + 1;
        fixLength(playerScore);
        g.drawString(String.valueOf(rank), rowPos - 10, 340);
        g.drawString(playerScore.username, rowPos + 20, 340);
        g.drawString(playerScore.realName, rowPos + 150, 340);
        g.drawString(String.valueOf(playerScore.score), rowPos + 270, 340);
        g.drawString(String.valueOf(playerScore.gamesPlayed), rowPos + 380, 340);
    }

    /**
     *
     * @param score
     */
    private void fixLength(PlayerScore score) {
        if (score.username.length() > 11) {
            score.username = score.username.substring(0, 9);
        }
        if (score.realName.length() > 11) {
            score.realName = score.realName.substring(0, 9);
        }
    }

    /**
     * Draws out a message to tell the user to press enter to go back to the previous message
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void renderGoBackMessage(Graphics2D g) {
        g.drawString("Press Enter to go back", rowPos, 380);
    }

    /**
     * Returns the user to the menu from which they had accessed the leaderboard menu.
     */
    private void goBack() {
        loadScoresFlag = true;
        menuManager.setMenuState(previousMenuState);
    }

    /**
     * Calls the goBack function when enter is pressed to return the user to the previous menu.
     * @param k User input is passed.
     */
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            goBack();
        }
    }

    /**
     * Gets the previous menu state.
     * @return Previous menu state is retrieved
     */
    public MenuState getPreviousMenuState() {
        return previousMenuState;
    }

    /**
     * Sets the previous menu state.
     * @param previousMenuState Variable containing the previous menustate is passed
     */
    public void setPreviousMenuState(MenuState previousMenuState) {
        this.previousMenuState = previousMenuState;
    }

}
