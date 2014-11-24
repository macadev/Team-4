package Database;

import java.util.ArrayList;
import java.sql.*;

/**
 * Created by danielmacario on 14-11-18.
 */
public class PlayerScore {
    public   int score;
    public  String username;

    public int getPlayerScore() {
        return score;
    }

    public static PlayerScore createPlayer(String username, int score) {
        PlayerScore player = new PlayerScore();
        player.score = score;
        player.username = username;
        return player;
    }

    public static void checkPlayerExists(PlayerScore playerTest) {
        System.out.println(playerTest.username);
        System.out.println(playerTest.score);
        System.out.println("player exists");
    }
}



