package Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-11-04.
 */
public class Login extends MenuTemplate {

    //Creating JTextFiled[] object array
    private JTextField[] information;

    private String[] options = {"Login","Create Account","Exit"};
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public Login (MenuManager menuManager) {

        //Constructor for username and passwords
        this.information = new JTextField[2];
        JTextField username = new JTextField(100);
        JPasswordField password = new JPasswordField(100);
        this.information[0] = username;
        this.information[1] = password;

        this.menuManager = menuManager;
        titleColor = new Color(230, 200, 0);
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);
    }


    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        //draw the title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setPaint(new Color(255,255,255));
        g.drawString("Login Menu!", 80, 70);

        //draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.RED);
            }



            // pass horizontal distance, then vertical distance
            g.drawString(options[i], 95, 120 + i * 15);
            information[0].paint(g);
            for(JTextField tF: information){
                tF.paint(g);
            }
        }
    }

    private void select() {
        if (currentChoice == 0) {
            menuManager.setMenuState(MenuState.MAIN);
        }
        if (currentChoice == 1) {
            //load game
        }
        if (currentChoice == 1) {
            //terminate the game
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) currentChoice  = options.length - 1;
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) currentChoice = 0;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
