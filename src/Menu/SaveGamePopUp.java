package Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by FloMac on 14-11-20.
 */
public class SaveGamePopUp extends JFrame {

    private JTextField fileNameField;
    private JLabel labelMessage;
    private JButton buttonSubmit;
    private MenuManager menuManager;

    public SaveGamePopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();
        //Exit Option
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Display Size
        setSize(350, 100);
        //Setting it to the middle of the screen
        setLocationRelativeTo(null);
        //Disable resize
        setResizable(false);
    }

    //User Interface
    private void createView(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JLabel label = new JLabel("File name: ");
        label.setBounds(10, 10, 80, 25);
        panel.add(label);

        fileNameField = new JTextField(20);
        fileNameField.setBounds(100, 10, 160, 25);
        panel.add(fileNameField);

        buttonSubmit = new JButton("Save Game");
        buttonSubmit.setBounds(10, 80, 80, 25);

        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFileClicked();
            }
        });
        panel.add(buttonSubmit);

        JButton exitButton = new JButton("Close");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                closeWindow();
            }
        });
        panel.add(exitButton);
        labelMessage = new JLabel("");
        panel.add(labelMessage);
    }

    public void closeWindow() {
        setVisible(false);
        dispose();
    }

    public void saveFileClicked() {
        String fileName = fileNameField.getText();
        if (fileName.isEmpty()) {
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            fileName = df.format(today);
        }

        menuManager.saveGame(fileName);
        closeWindow();
    }

    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }


}






