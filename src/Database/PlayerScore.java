package Database;

/**
 * Created by danielmacario on 14-11-18.
 */
public class PlayerScore {

    private int score;
    private String username;
    private String realName;

    public PlayerScore(int score, String username, String realName) {
        this.score = score;
        this.username = username;
        this.realName = realName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
