package SystemController;

import GameObject.Bomb;
import GamePlay.GamePlayManager;

import java.io.*;

/**
 * Created by danielmacario on 14-11-19.
 */
public class GameFileManager {

    private String username;

    public GameFileManager(String username) {
        this.username = username;
    }

    public void saveGame(GamePlayManager gamePlayManager) {

        try {
            FileOutputStream fileOut = new FileOutputStream("savedgames/mellamomelon/savedGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gamePlayManager);
            out.close();
            fileOut.close();
            System.out.printf("Successfully saved the game");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public GamePlayManager loadGame() {
        GamePlayManager loadedGame;
        try {

            File f = new File("savedgames/mellamomelon"); // current directory
            File[] files = f.listFiles();
            for (File file : files) {
                System.out.println(file.getPath());
            }

            FileInputStream fileIn = new FileInputStream("savedgames/mellamomelon/savedGame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedGame = (GamePlayManager) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Game loaded successfully!");
        } catch(IOException i) {
            i.printStackTrace();
            return null;
        } catch(ClassNotFoundException c) {
            System.out.println("Load Game Failed");
            c.printStackTrace();
            return null;
        }



        return loadedGame;
    }
}
