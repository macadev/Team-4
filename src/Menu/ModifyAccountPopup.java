package Menu;

import Database.DatabaseController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by Shabab Ahmed on 20/11/2014.
 */
public class ModifyAccountPopup extends JFrame {

        private JTextField realName;
        private JPasswordField fieldPass;
        private JPasswordField retypePass;
        private JLabel labelMessage;
        private JLabel labelMessage2;
        private JButton buttonSubmit;
        private JButton buttonExit;
        private MenuManager menuManager;

        public ModifyAccountPopup(MenuManager menuManager){
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

        labelMessage = new JLabel("Leave field blank if no update is required");
        labelMessage.setBounds(10, 190, 160, 25);
        panel.add(labelMessage);
        labelMessage2 = new JLabel("");
        labelMessage2.setBounds(10, 210, 160, 25);
        panel.add(labelMessage2);
    }
    public void exitClicked(){
        menuManager.setMenuState(MenuState.MODIFYACCOUNT);
        setVisible(false);
        dispose();
    }
    public void submitClicked() {
        String rName = realName.getText();
        String pass = fieldPass.getText();
        String rPass = retypePass.getText();
        String uName = "steven222";

        boolean updateSuccessful = false;

        if (rName.isEmpty()  && pass.isEmpty() && rPass.isEmpty() ){
            labelMessage.setText("                              Nothing to Update!                            ");
            labelMessage2.setText("");
        }else if(!pass.isEmpty()){
            if (!isValidPassword(pass)) {
                labelMessage.setText("The password is too weak. Enter one of each type from:");
                labelMessage2.setText("Capital letters, Small letters, Digits and Symbols");
            } else if(pass.length() < 8) {
                labelMessage.setText("Password must be at least 8 characters");
                labelMessage2.setText("");
            } else if (!(pass.equals(rPass))) {
                labelMessage.setText("                             Passwords do not match                        ");
                labelMessage2.setText("");
            } else {
//                try {
//                    //updateSuccessful = DatabaseController.updateInformation(pass, rName, uName);
//                } catch (ClassNotFoundException e1) {
//                    e1.printStackTrace();
//                }

                if (updateSuccessful) {
                    labelMessage.setText("Information Updated");
                    labelMessage2.setText("");
                    setVisible(false);
                    dispose();
                } else {
                    labelMessage.setText("Error: Invalid Entry");
                    labelMessage2.setText("");
                }
            }
        } else {
//            try {
//                updateSuccessful = DatabaseController.updateInformation(pass, rName, uName);
//            } catch (ClassNotFoundException e1) {
//                e1.printStackTrace();
//            }

            if (updateSuccessful) {
                labelMessage.setText("Information Updated");
                labelMessage2.setText("");
                setVisible(false);
                dispose();
            } else {
                labelMessage.setText("Error: Invalid Entry");
                labelMessage2.setText("");
            }
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

