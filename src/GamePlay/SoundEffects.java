package GamePlay;

/**
 * Created by FloMac on 11/20/14.
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;

/**
 * Created by FloMac on 11/20/14.
 */

public class SoundEffects extends Thread {

    public static String ROOT = new File("").getAbsolutePath();

    public SoundEffects() {
    }

    public void run(){
        this.playGameOver();

    }

    public void playGameOver() {

        File file = new File(ROOT,"/src/resources/Music/smb_gameover.wav");
        try {
            System.out.println("starting to play file");
            AudioClip clip = Applet.newAudioClip(file.toURI().toURL());
            clip.play();

            System.out.println("Done playing file");
        } catch (Exception e){
        }
    }

    public static void main(String[] args) throws Exception {

        SoundEffects soundEffects = new SoundEffects();
        soundEffects.start();


        System.out.println("end");
    }
}