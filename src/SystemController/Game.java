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
            System.out.println("Database succesfully initialized");
            db.createNewUser("John", "Johnpass","JohnRealName");
            db.createNewUser("Jane", "Janepass","JaneRealName");
            db.createNewUser("Bob","Bobpass","BobRealName");
            if(db.authenticateUser("John","Johnpass")) {
                System.out.println("Authentication Succesful");
            }
            else {
                System.out.println("User not found");
            }
            if (db.updateInformation("johnNewPass","Johnny","John")); {
                System.out.println("User info updated");
            }
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //test code for database


        JFrame window = new JFrame("Bomberman");
        window.setContentPane(new GameController());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
