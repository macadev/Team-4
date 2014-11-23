package Menu;

/**
 * Created by FloMac on 14-11-20.
 */
import GamePlay.GamePlayManager;
import SystemController.GameFileManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LoadGamePopUp extends JFrame {

    private JScrollPane scrollpane;
    private MenuManager menuManager;

    public LoadGamePopUp(MenuManager menuManager) {
        super("Load Game Menu");
        this.menuManager = menuManager;
        setSize(200, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        displayFiles();
        setVisible(true);
    }

    public void displayFiles() {

        ArrayList<String> userSavedFiles = getFileNames(menuManager.getPlayerUserName());
        JPanel view = new JPanel(new GridLayout(0,1,0,10));
        view.setSize(100, 100);
        JButton exitButton = new JButton("Close");
        exitButton.setBorder( new LineBorder(Color.BLACK) );
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                closeWindow();
            }
        });
        view.add(exitButton);

        if (userSavedFiles == null) {
            JLabel label = new JLabel("No saved Files exist");
            //label.setBounds(10, 10, 80, 25);
            view.add(label);
        } else {
            for (final String fileName : userSavedFiles) {
                final JButton fileButton = new JButton(String.valueOf(fileName));
                fileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fileButtonClicked(fileName);
                    }
                });

                view.add(fileButton);
            }
        }

        scrollpane = new JScrollPane(view,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollpane, BorderLayout.CENTER);
    }

    public void fileButtonClicked(String fileName) {
        GamePlayManager loadedGame = GameFileManager.loadGame(fileName);
        menuManager.setUpLoadedGame(loadedGame);
        closeWindow();
    }

    public void closeWindow() {
        setVisible(false);
        dispose();
    }

    public static ArrayList<String> getFileNames(String username) {
        //Access the directory where the the user files are saved
        String folderPath = "savedgames/" + username + "/";
        File f = new File(folderPath);

        if (!f.exists()) return null;
        File[] files = f.listFiles();
        ArrayList<String> filePaths = new ArrayList<String>();

        String fileName;
        for (File file : files) {
            try {
                fileName = file.getPath().replace("\\", "/").replace(folderPath, "").replace(".ser", "");
                filePaths.add(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePaths;
    }

}
