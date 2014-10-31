package SystemController; /**
 * Created by danielmacario on 14-10-29.
 */

import SystemController.GameController;

import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {
        JFrame window = new JFrame("Bomberman");
        window.setContentPane(new GameController());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
