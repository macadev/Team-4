package Menu;

import Database.DatabaseController;
import GamePlay.GamePlayManager;
import SystemController.GameFileManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danielmacario on 14-11-22.
 */
public class LevelSelectionPopUp extends JFrame {

    private JScrollPane scrollpane;
    private MenuManager menuManager;

    public LevelSelectionPopUp(MenuManager menuManager) {
        super("Select Level Menu");
        this.menuManager = menuManager;
        setSize(200, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        displayUnlockedLevels();
        setVisible(true);
    }

    public void displayUnlockedLevels() {

        int numOfUnlockedLevels = getNumberOfUnlockedLeves();

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

        for (int levelNumber = 1; levelNumber <= numOfUnlockedLevels; levelNumber++) {

            final JButton levelButton = new JButton(String.valueOf(levelNumber));
            final int finalLevelNumber = levelNumber;

            levelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    levelSelected(finalLevelNumber);
                }
            });

            view.add(levelButton);
        }

        scrollpane = new JScrollPane(view,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollpane, BorderLayout.CENTER);

    }

    public int getNumberOfUnlockedLeves() {
        int levelUnlocked = 1;
        try {
            levelUnlocked = DatabaseController.getLevelUnlocked(menuManager.getPlayerUserName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return levelUnlocked;
    }

    public void levelSelected(int levelNumber) {
        System.out.println(levelNumber);
        menuManager.startGameFromSelectedStage(levelNumber);
        closeWindow();
    }

    public void closeWindow() {
        setVisible(false);
        dispose();
    }
}
