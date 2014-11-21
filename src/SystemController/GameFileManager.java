package SystemController;

import GameObject.Bomb;
import GamePlay.GamePlayManager;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-11-19.
 */
public class GameFileManager {

    private static String userName;

    public GameFileManager(String username) {
        this.userName = username;
    }

    public static void saveGame(GamePlayManager gamePlayManager, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream("savedgames/" + userName + "/" + fileName +".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gamePlayManager);
            out.close();
            fileOut.close();
            System.out.println("Successfully saved the game");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static GamePlayManager loadGame(String fileName) {
        GamePlayManager loadedGame;
        try {
            FileInputStream fileIn = new FileInputStream("savedgames/" + userName + "/" + fileName + ".ser");
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
