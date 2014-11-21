package Menu;
import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;

/**
 * Created by FloMac on 11/20/14.
 */


public class Music extends Thread {

    public static String ROOT = new File("").getAbsolutePath();

    public Music() {
    }

    public void run(){
        this.playMusic();

    }

    public void playMusic() {

        File file = new File(ROOT,"/src/resources/Music/Pirates of the caribbean 8-bit.wav");
        try {

            if (!file.exists()) {
                System.out.println("Cannot find:" + file);
            }

            AudioClip clip = Applet.newAudioClip(file.toURI().toURL());
            clip.loop();

        } catch (Exception e){
        }
    }

    public static void main(String[] args) throws Exception {

        Music music = new Music();
        music.start();


        System.out.println("end");
    }
}