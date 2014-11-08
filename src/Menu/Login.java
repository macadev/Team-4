package Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by danielmacario on 14-11-04.
 */
public class Login extends MenuTemplate {




    private String[] options = {"Login","Create Account","Exit"};
    private int currentChoice = 0;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public Login (MenuManager menuManager) {

        this.menuManager = menuManager;
        titleColor = new Color(230, 200, 0);
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);

    }

    //Initializes all UI components
    private void createView(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JLabel label = new JLabel("Please enter your name: ");
        panel.add(label);

        fieldName = new JTextField();
        fieldName.setPreferredSize(new Dimension(150, 30));
        panel.add(fieldName);

        buttonSubmit = new JButton("Submit");
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String name = fieldName.getText();
                if (name.isEmpty()){
                    labelMessage.setText("Where dafuq is your name?");
                } else {
                    labelMessage.setText("Hello there, " + name + ". How are you?");
                }
            }
        });
        panel.add(buttonSubmit);

        labelMessage = new JLabel("Enter your name!");
        panel.add(labelMessage);
    }


    @Override
    public void init() {

    }

    @Override
    public void draw(Graphics2D g) {
        //draw the title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.setPaint(new Color(255,255,255));
        g.drawString("Bomberman Login Menu", 80, 70);

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
