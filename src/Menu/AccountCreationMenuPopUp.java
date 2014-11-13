package Menu;

import Database.DatabaseController;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.regex.Pattern;

import javax.swing.*;

/**
 * Created by Shabab Ahmed on 10/11/2014.
 */
public class AccountCreationMenuPopUp extends JFrame {

    private JTextField realName;
    private JTextField userName;
    private JPasswordField fieldPass;
    private JPasswordField retypePass;
    private JLabel labelMessage;
    private JLabel labelMessage2;
    private JButton buttonSubmit;
    private JButton buttonExit;
    private MenuManager menuManager;

    public AccountCreationMenuPopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();

        //Exit Option
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        //Display Size
        setSize(370, 230);
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

        JLabel label = new JLabel("Real Name: ");
        label.setBounds(10, 10, 80, 25);
        panel.add(label);

        realName = new JTextField(20);
        realName.setBounds(100, 10, 160, 25);
        //realName.setPreferredSize(new Dimension(150, 30));
        panel.add(realName);

        JLabel label3 = new JLabel("User Name: ");
        label3.setBounds(10, 50, 80, 25);
        panel.add(label3);

        userName = new JTextField(20);
        userName.setBounds(100, 50, 160, 25);
        //userName.setPreferredSize(new Dimension(150, 30));
        panel.add(userName);


        JLabel label2 = new JLabel("Password: ");
        label2.setBounds(10, 90, 80, 25);
        panel.add(label2);

        fieldPass = new JPasswordField(20);
        fieldPass.setBounds(100, 90, 160, 25);
        //fieldPass.setPreferredSize(new Dimension(150, 30));
        panel.add(fieldPass);

        JLabel label4 = new JLabel("Re-enter Password: ");
        label4.setBounds(10, 130, 80, 25);
        panel.add(label4);

        retypePass = new JPasswordField(20);
        retypePass.setBounds(100, 130, 160, 25);
        //retypePass.setPreferredSize(new Dimension(150, 30));
        panel.add(retypePass);

        buttonSubmit = new JButton("Submit");
        buttonSubmit.setBounds(10, 170, 80, 25);
        buttonExit  = new JButton("Go back to Login Menu");
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

        labelMessage = new JLabel("");
        labelMessage.setBounds(10, 190, 160, 25);
        panel.add(labelMessage);
        labelMessage2 = new JLabel("");
        labelMessage2.setBounds(10, 210, 160, 25);
        panel.add(labelMessage2);
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
            labelMessage.setText("The password is too weak. Enter one of each type from:");
            labelMessage2.setText("Capital letters, Small letters, Digits and Symbols");
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
                labelMessage.setText("Error: Username may already be in use or");
                labelMessage2.setText("invalid entry for username and password.");
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

    public boolean isValidUsername(String sI){
        String s = removeAccent(sI);
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

    public String removeAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pat = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pat.matcher(nfdNormalizedString).replaceAll("");
    }

}
