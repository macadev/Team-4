package Database;

import java.awt.*;
import java.util.ArrayList;
import java.sql.*;

/**
 * Created by Owen Li on 14-11-18.
 */
public class PlayerScore {

    public int score;
    public String username;
    public int gamesPlayed;
    public String realName;

    public static PlayerScore createPlayer(String username, int score, String realName, int gamesPlayed) {
        PlayerScore player = new PlayerScore();
        player.score = score;
        player.username = username;
        player.gamesPlayed = gamesPlayed;
        player.realName = realName;
        return player;
    }
}



