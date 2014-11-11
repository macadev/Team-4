package Menu;

import Database.DatabaseController;

import javax.swing.*;
import java.awt.*;
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
    private JLabel labelMessage;
    private JTextField fieldName;
    private JPasswordField fieldPass;
    private MenuManager menuManager;


    public Login (MenuManager menuManager) {

        this.menuManager = menuManager;
        titleColor = new Color(230, 200, 0);
        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        font = new Font("Arial", Font.PLAIN, 12);
        labelMessage = new JLabel("");
        fieldName = new JPlaceHolderTextField();
        fieldName.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        fieldPass = new JPlaceHolderPasswordField();
        fieldPass.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));

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

            loginClicked();
        }
        if (currentChoice == 1) {
            //menuManager.setMenuState(MenuState.ACCOUNTCREATION);
            ACPopUp acp = new ACPopUp(menuManager);
            acp.setVisible(true);
        }
        if (currentChoice == 2) {
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

    public void loginClicked() {
        String name = fieldName.getText();
        //System.out.println(name);
        String password = fieldPass.getText();
        //System.out.println(password);
        boolean loginSuccessful = false;

        if (name.isEmpty()){
            labelMessage.setText("Username cannot be blank");
        } else if (password.isEmpty()){
            labelMessage.setText("Password cannot be blank");
        } else {
            try {
                loginSuccessful = DatabaseController.authenticateUser(name, password);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (loginSuccessful) {
                labelMessage.setText("Success");
                menuManager.associatePlayerUserName(name);
                redirectToMainMenu();
                setVisible(false);
                dispose();
            } else {
                labelMessage.setText("Username-Password Combination Invalid");
            }
        }
    }

    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }

}

