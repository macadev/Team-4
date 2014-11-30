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
 * Creates a pop up to save the current game with or without a file name.
 */
public class SaveGamePopUp extends JFrame {

    private JTextField fileNameField;
    private JLabel labelMessage;
    private JButton buttonSubmit;
    private MenuManager menuManager;

    /**
     * Creates the view of the SaveGamePopUp, prevents resizing, sets size, sets display position, enables closing.
     * @param menuManager
     */
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

    /**
     * Creates the user interface
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

            /**
             * Listens if saveFile is clicked.
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFileClicked();
            }
        });
        panel.add(buttonSubmit);

        JButton exitButton = new JButton("Close");
        exitButton.addActionListener(new ActionListener() {
            /**
             * Listens if closeWindow is clicked.
             * @param actionEvent
             */
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
     * Hides the window.
     */
    public void closeWindow() {
        setVisible(false);
        dispose();
    }

    /**
     * Checks if fileNameField is empty, saves with sample format if so, otherwise with a custom file name.
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
     * Changes MenuState to MainMenu.
     */
    public void redirectToMainMenu() {
        menuManager.setMenuState(MenuState.MAIN);
    }


}






