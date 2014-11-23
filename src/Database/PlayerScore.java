package Database;

import java.util.ArrayList;
import java.sql.*;

/**
 * Created by danielmacario on 14-11-18.
 */
public class PlayerScore {
    private int score;
    private String username;

    public static PlayerScore createPlayer(String username, int score) {
        PlayerScore player = new PlayerScore();
        player.score = score;
        player.username = username;
        return player;
    }
}



