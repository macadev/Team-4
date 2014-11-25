/**
 * Created by danielmacario on 14-10-29.
 */
package SystemController;

import Database.DatabaseController;
import javax.swing.JFrame;

import Database.PlayerScore;


public class Game {

    /**
     * Creates the JFrame object where all the views will be rendered
     * and where the user will interact with the application.
     * @param args
     */
    public static void main(String[] args) {
        SoundController.THEME.loop();
        try {
            DatabaseController.initializeDatabase();
            DatabaseController.printDBContents();
            System.out.println("Database succesfully initialized");
            DatabaseController.createNewUser("owentest","testpass","Owenli");
            DatabaseController.createNewUser("testuser","testpass","testName");
            DatabaseController.createNewUser("testuser2","testpass2","testName2");
            DatabaseController.createNewUser("testuser3","testpass3","testName3");
            DatabaseController.createNewUser("testuser4","testpass4","testName4");
            DatabaseController.createNewUser("testuser5","testpass5","testName5");
            DatabaseController.setLevelUnlocked("owentest",45);
            DatabaseController.setScore("owentest",3000);
            DatabaseController.setScore("testuser",2000);
            DatabaseController.setScore("testuser2",5000);
            DatabaseController.setScore("testuser3",500);
            DatabaseController.setScore("testuser4",200);
            DatabaseController.incrementGamesPlayed("owentest");
            DatabaseController.incrementGamesPlayed("owentest");
            DatabaseController.incrementGamesPlayed("owentest");
            DatabaseController.incrementGamesPlayed("testuser3");
            DatabaseController.incrementGamesPlayed("testuser3");

            DatabaseController.getTopScoresSet();
            System.out.println("first set");
            PlayerScore.checkPlayerExists(DatabaseController.getPlayerObject("testuser"));

            DatabaseController.getLevelUnlocked("owentest");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            DatabaseController.printDBContents();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JFrame window = new JFrame("Bomberman");
        window.setContentPane(new GameController());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);



    }

}
