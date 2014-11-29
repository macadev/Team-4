package Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * The SaveGamePopUp is accessed through the in game menu and allows the user to save his current progress in the game.
 * It allows the user to name the save game file or a default time stamp is give if the name is left blank.
 * Methods from JFrame are inherited to be used in the popup.
 */
public class SaveGamePopUp extends JFrame {

    private JTextField fileNameField;
    private JLabel labelMessage;
    private JButton buttonSubmit;
    private MenuManager menuManager;

    /**
     * Constructor for SaveGamePopUp.
     * @param menuManager Object menuManager is passed to navigate between the different game states and menus.
     */
    public SaveGamePopUp(MenuManager menuManager){
        this.menuManager = menuManager;
        createView();

        //Sets up window attributes
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350, 100);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Creates the window and everything displayed in it in the popup.
     */
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

    /**
     * Adds functionality to close the popup.
     */
    public void closeWindow() {
        setVisible(false);
        dispose();
    }

    /**
     * Saves the file when save file is clicked. If the file name is empty, it gives a time stamp as the name for the
     * saved file. Otherwise saves the file with user entered name.
     */
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

    /**
     * Redirects the user to the main menu.
     */
    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }


}






