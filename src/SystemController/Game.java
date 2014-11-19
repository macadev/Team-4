/**
 * Created by danielmacario on 14-10-29.
 */
package SystemController;

import Database.DatabaseController;
import javax.swing.JFrame;
import java.io.File;

public class Game {

    /**
     * Creates the JFrame object where all the views will be rendered
     * and where the user will interact with the application.
     * @param args
     */
    public static void main(String[] args) {
        try {
            DatabaseController.initializeDatabase();
            System.out.println("Database succesfully initialized");
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
