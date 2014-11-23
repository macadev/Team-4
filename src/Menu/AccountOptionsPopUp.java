package Menu;

import Database.DatabaseController;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Shabab Ahmed on 20/11/2014.
 */
public class AccountOptionsPopUp extends JFrame {

    private JTextField realName;
    private JPasswordField fieldPass;
    private JPasswordField retypePass;
    private JLabel labelMessage;
    private JLabel labelMessage2;
    private JLabel labelMessage3;
    private JButton buttonSubmit;
    private JButton buttonExit;
    private JButton buttonDeleteAccount;
    private MenuManager menuManager;

    /**
     *
     * @param menuManager instance is passed.
     */
    public AccountOptionsPopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();

        //Exit Option
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        //Display Size
        setSize(420, 230);
        //Setting it to the middle of the screen
        setLocationRelativeTo(null);
        //Disable resize
        setResizable(false);
    }

    //User Interface
    private void createView(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("New Name:             ");
        label.setBounds(10, 10, 80, 25);
        panel.add(label);

        realName = new JTextField(20);
        realName.setBounds(100, 10, 160, 25);
        panel.add(realName);

        JLabel label2 = new JLabel("New Password:        ");
        label2.setBounds(10, 90, 80, 25);
        panel.add(label2);

        fieldPass = new JPasswordField(20);
        fieldPass.setBounds(100, 90, 160, 25);
        panel.add(fieldPass);

        JLabel label4 = new JLabel("Re-enter Password: ");
        label4.setBounds(10, 130, 80, 25);
        panel.add(label4);

        retypePass = new JPasswordField(20);
        retypePass.setBounds(100, 130, 160, 25);
        panel.add(retypePass);

        buttonSubmit = new JButton("Submit");
        buttonSubmit.setBounds(10, 170, 80, 25);
        buttonExit  = new JButton("Close");
        buttonExit.setBounds(50, 170, 80, 25);
        buttonDeleteAccount = new JButton("Delete Account");
        buttonDeleteAccount.setBounds(10, 170, 80, 25);


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

        buttonDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteAccountClicked();
            }
        });

        panel.add(buttonSubmit);
        panel.add(buttonExit);
        panel.add(buttonDeleteAccount);

        labelMessage = new JLabel("Leave field blank if no update is required");
        labelMessage.setBounds(10, 190, 160, 25);
        panel.add(labelMessage);
        labelMessage2 = new JLabel("");
        labelMessage2.setBounds(10, 210, 160, 25);
        panel.add(labelMessage2);
        labelMessage3 = new JLabel("");
        labelMessage3.setBounds(10, 230, 160, 25);
        panel.add(labelMessage3);
    }
    public void exitClicked(){
        setVisible(false);
        dispose();
    }

    public void submitClicked() {
        labelMessage.setText("");
        labelMessage3.setText("");

        String newRealName = realName.getText();
        String newPassWord = fieldPass.getText();
        String newPassWordDuplicate = retypePass.getText();

        if (newRealName.isEmpty()  && newPassWord.isEmpty() && newPassWordDuplicate.isEmpty() ){
            labelMessage.setText("                              Nothing to Update.                            ");
            labelMessage2.setText("");
            return;
        }
        boolean passWordUpdatedSuccessfully = true;
        if (!newPassWord.isEmpty()){
            passWordUpdatedSuccessfully = submitNewPassWord(newPassWord, newPassWordDuplicate);
        }
        if (passWordUpdatedSuccessfully) {
            if (!newRealName.isEmpty()) {
                submitNewRealName(newRealName);
            }
        }
    }

    public void deleteAccountClicked() {

    }

    public boolean submitNewPassWord(String newPassWord, String newPassWordDuplicate) {
        boolean updateSuccessful = false;

        if (!isValidPassword(newPassWord)) {
            labelMessage.setText("The password is too weak. Enter one of each type from:");
            labelMessage2.setText("Capital letters, Small letters, Digits and Symbols");
            updateSuccessful = false;
        } else if(newPassWord.length() < 8) {
            labelMessage.setText("Password must be at least 8 characters");
            labelMessage2.setText("");
            updateSuccessful = false;
        } else if (!(newPassWord.equals(newPassWordDuplicate))) {
            labelMessage.setText("                             Passwords do not match                        ");
            labelMessage2.setText("");
            updateSuccessful = false;
        } else {
            try {
                updateSuccessful = DatabaseController.updatePassword(newPassWord, menuManager.getPlayerUserName());
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (updateSuccessful) {
                labelMessage.setText("                             Password Updated                        ");
                labelMessage2.setText("");
            } else {
                labelMessage.setText("                             Error: Invalid Entry                        ");
                labelMessage2.setText("");
            }
        }
        return updateSuccessful;
    }

    public void submitNewRealName(String newRealName) {
        boolean updateSuccessful = false;

        try {
            updateSuccessful = DatabaseController.updateRealName(newRealName, menuManager.getPlayerUserName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        if (updateSuccessful) {
            labelMessage3.setText("                             Real Name Updated                        ");
        } else {
            labelMessage3.setText("                              Error: Invalid Entry                        ");
        }
    }


    private boolean isValidPassword(String password) {
        int passwordStrength = 0;
        String[] partialRegexChecks = {".*[a-z]+.*", // Lower Case
                ".*[A-Z]+.*", // Upper Case
                ".*[\\d]+.*", // Digits
                ".*[$&+,:;=?@#|'<>.-^*()%!]+.*" // Symbols
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



}

