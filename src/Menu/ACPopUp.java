package Menu;

import Database.DatabaseController;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by Shabab Ahmed on 10/11/2014.
 */
public class ACPopUp extends JFrame {

    private JTextField realName;
    private JTextField userName;
    private JPasswordField fieldPass;
    private JPasswordField retypePass;
    private JLabel labelMessage;
    private JButton buttonSubmit;
    private JButton buttonExit;
    private MenuManager menuManager;

    public ACPopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();

        //Exit Option
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        //Display Size
        setSize(1000, 100);
        //Setting it to the middle of the screen
        setLocationRelativeTo(null);
        //Disable resize
        setResizable(false);


    }

    //User Interface
    private void createView(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JLabel label = new JLabel("Real Name: ");
        panel.add(label);

        realName = new JTextField();
        realName.setPreferredSize(new Dimension(150, 30));
        panel.add(realName);

        JLabel label3 = new JLabel("User Name: ");
        panel.add(label3);

        userName = new JTextField();
        userName.setPreferredSize(new Dimension(150, 30));
        panel.add(userName);


        JLabel label2 = new JLabel("Password: ");
        panel.add(label2);

        fieldPass = new JPasswordField();
        fieldPass.setPreferredSize(new Dimension(150, 30));
        panel.add(fieldPass);

        JLabel label4 = new JLabel("Re-enter Password: ");
        panel.add(label4);

        retypePass = new JPasswordField();
        retypePass.setPreferredSize(new Dimension(150, 30));
        panel.add(retypePass);

        buttonSubmit = new JButton("Submit");
        buttonExit  = new JButton("Go back to Login Menu");


        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submitClicked();
            }
        });

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exitClicked();
            }
        });

        panel.add(buttonSubmit);
        panel.add(buttonExit);

        labelMessage = new JLabel("");
        panel.add(labelMessage);
    }
    public void exitClicked(){
        menuManager.setMenuState(MenuState.LOGIN);
        setVisible(false);
        dispose();
    }
    public void submitClicked() {
        String rName = realName.getText();
        String uName = userName.getText();
        String pass = fieldPass.getText();
        String rPass = retypePass.getText();

        boolean creationSuccessful = false;

        if (rName.isEmpty() || uName.isEmpty() || pass.isEmpty() || rPass.isEmpty() ){
            labelMessage.setText("Please fill out all the information");
        } else if (!(pass.equals(rPass))) {
            labelMessage.setText("Passwords do not match");
        } else if (uName.length() < 6){
            labelMessage.setText("Username must be at least 6 characters");
        } else if (!isValidPassword(pass)) {
            labelMessage.setText("The password is too weak. Enter one of each type from: Capital letters, Small letters, Digits and Symbols");
        } else if(pass.length() < 8) {
            labelMessage.setText("Password must be at least 8 characters");
        } else if(!isValidUsername(uName)){ //Needs to consider latin characters
            labelMessage.setText("Username has to be alphanumeric");
        } else {
            try {
                creationSuccessful = DatabaseController.createNewUser(uName, pass, rName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (creationSuccessful) {
                labelMessage.setText("Account Created");
                System.out.println();
                menuManager.associatePlayerUserName(uName);
                redirectToMainMenu();
                setVisible(false);
                dispose();
            } else {
                labelMessage.setText("Error. Username may already be in use or invalid entry for username and password.");
            }
        }
    }

    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }

    private boolean isValidPassword(String password) {
        int passwordStrength = 0;
        String[] partialRegexChecks = {".*[a-z]+.*", // lower
                ".*[A-Z]+.*", // upper
                ".*[\\d]+.*", // digits
                ".*[$&+,:;=?@#|'<>.-^*()%!]+.*" // symbols
        };


        if (password.matches(partialRegexChecks[0])) {
            passwordStrength += 25;
        }
        if (password.matches(partialRegexChecks[1])) {
            passwordStrength += 25;
        }
        if (password.matches(partialRegexChecks[2])) {
            passwordStrength += 25;
        }
        if (password.matches(partialRegexChecks[3])) {
            passwordStrength += 25;
        }

        if (passwordStrength != 100){
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidUsername(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

}
