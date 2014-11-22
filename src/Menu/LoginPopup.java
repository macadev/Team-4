package Menu;

import Database.DatabaseController;
import SystemController.GameStateManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginPopUp extends JFrame {
	
    private JTextField fieldName;
    private JPasswordField fieldPass;
    private JLabel labelMessage;
    private JButton buttonSubmit;
    private MenuManager menuManager;

    public LoginPopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();
        //Exit Option
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Display Size
        setSize(350, 150);
        //Setting it to the middle of the screen
        setLocationRelativeTo(null);
        //Disable resize
        setResizable(false);
    }

    //User Interface
    private void createView(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JLabel label = new JLabel("Username: ");
        label.setBounds(10, 10, 80, 25);
        panel.add(label);

        fieldName = new JTextField(20);
        fieldName.setBounds(100, 10, 160, 25);
        //fieldName.setPreferredSize(new Dimension(150, 30));
        panel.add(fieldName);

        JLabel label2 = new JLabel("Password: ");
        label2.setBounds(10, 40, 80, 25);
        panel.add(label2);

        fieldPass = new JPasswordField(20);
        fieldPass.setBounds(100, 40, 160, 25);
        //fieldPass.setPreferredSize(new Dimension(150, 30));
        panel.add(fieldPass);


        buttonSubmit = new JButton("Login");
        buttonSubmit.setBounds(10, 80, 80, 25);

        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginClicked();
            }
        });

        panel.add(buttonSubmit);

        labelMessage = new JLabel("");
        panel.add(labelMessage);
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

