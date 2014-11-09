package SystemController; /**
 * Created by danielmacario on 14-10-29.
 */

import Database.DatabaseController;

import javax.swing.JFrame;

public class Game {

    /**
     * Creates the JFrame object where all the views will be rendered
     * and where the user will interact with the application.
     * @param args
     */
    public static void main(String[] args) {
        DatabaseController db = new DatabaseController();
        try {
            db.initializeDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //test code for database
        System.out.println("Database succesfully initialized");
        JFrame window = new JFrame("Bomberman");
        window.setContentPane(new GameController());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
