/**
 * Created by danielmacario on 14-10-29.
 */
package SystemController;

import Database.DatabaseController;
import javax.swing.JFrame;
import Menu.Music;

public class Game {

    /**
     * Creates the JFrame object where all the views will be rendered
     * and where the user will interact with the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            DatabaseController.initializeDatabase();
            DatabaseController.printDBContents();
            System.out.println("Database succesfully initialized");
            DatabaseController.createNewUser("owentest","testpass","Owenli");
            DatabaseController.createNewUser("testuser","testpass","testName");
            DatabaseController.setLevelUnlocked("owentest",45);
            DatabaseController.setScore("owentest",3000);
            DatabaseController.setScore("owentest", 2000);
            DatabaseController.getScore("owentest");


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

        //Music music = new Music();
        //music.start();
        SoundController.THEME.loop();


    }

}
