package Menu;

import Database.DatabaseController;
import SystemController.GameStateManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submitClicked();
            }
        });

        panel.add(buttonSubmit);

        labelMessage = new JLabel("");
        panel.add(labelMessage);
    }

    public void submitClicked() {
        String rName = realName.getText();
        String uName = userName.getText();
        String pass = fieldPass.getText();
        String rPass = retypePass.getText();

        boolean creationSuccessful = false;
        boolean validateUName = false;
        boolean validatePass = false;

        //Authentication will be done but if-else method put in as a placeholder
        if (rName.isEmpty() || uName.isEmpty() || pass.isEmpty() || rPass.isEmpty() ){
            labelMessage.setText("Please fill out all the information");
        } else if (!(pass.equals(rPass))){
            labelMessage.setText("Passwords do not match");
        } /*else {
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
                labelMessage.setText("Username-Password combination invalid");
            }
        } */
    }

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-z0-9._-]{2,25}$";

    public UsernameValidator(){
        this.pattern = Pattern.compile(USERNAME_PATTERN);
    }

    public boolean validateUser(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

}
