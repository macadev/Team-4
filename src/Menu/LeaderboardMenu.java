/**
 * Created by FloMac on 11/23/14.
 */
package Menu;

import Database.DatabaseController;
import Database.PlayerScore;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LeaderboardMenu extends MenuTemplate {

    private MenuManager menuManager;
    private ArrayList<String> userNames;
    private ArrayList<PlayerScore> topScores = null;
    private PlayerScore playerScore = null;
    private boolean loadScoresFlag = false;
    private int columnPos = 150;
    private int rowPos = 30;
    private MenuState previousMenuState;

    public LeaderboardMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.loadScoresFlag = true;
    }

    @Override
    public void init() {

    }

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

    private void renderLegend(Graphics2D g) {
        g.drawString("Rank", rowPos - 15, columnPos - 30);
        g.drawString("UserName", rowPos + 35, columnPos - 30);
        g.drawString("Real Name", rowPos + 150, columnPos - 30);
        g.drawString("Score", rowPos + 270, columnPos - 30);
        g.drawString("Games", rowPos + 380, columnPos - 30);
    }

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

    private void fixLength(PlayerScore score) {
        if (score.username.length() > 11) {
            score.username = score.username.substring(0, 9);
        }
        if (score.realName.length() > 11) {
            score.realName = score.realName.substring(0, 9);
        }
    }

    private void renderGoBackMessage(Graphics2D g) {
        g.drawString("Press Enter to go back", rowPos, 380);
    }

    private void goBack() {
        loadScoresFlag = true;
        menuManager.setMenuState(previousMenuState);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            goBack();
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    public MenuState getPreviousMenuState() {
        return previousMenuState;
    }

    public void setPreviousMenuState(MenuState previousMenuState) {
        this.previousMenuState = previousMenuState;
    }
}
