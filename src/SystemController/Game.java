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
        try {
            DatabaseController.initializeDatabase();
            System.out.println("Database succesfully initialized");
<<<<<<< HEAD
            db.createNewUser("John", "Johnpass","JohnRealName");
            db.createNewUser("Jane", "Janepass","JaneRealName");
            db.createNewUser("Bob","Bobpass","BobRealName");
            if(db.authenticateUser("John","Johnpass")) {
=======
            DatabaseController.createNewUser("m", "a","John");
            DatabaseController.createNewUser("testNewUser", "testNewPass","John");
            DatabaseController.createNewUser("testNewUser2", "testNewPass2","Jane");
            if (DatabaseController.authenticateUser("m","a")) {
>>>>>>> origin/master
                System.out.println("Authentication Succesful");
            }
            else {
                System.out.println("User not found");
            }
<<<<<<< HEAD
            if (db.updateInformation("johnNewPass","Johnny","John")); {
                System.out.println("User info updated");
            }
        }

        catch (ClassNotFoundException e) {
=======

            DatabaseController.printDBContents();

        } catch (ClassNotFoundException e) {
>>>>>>> origin/master
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
