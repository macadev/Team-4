package SystemController;
import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Created by FloMac on 14-11-21.
 * Manages sounds in the game
 */

public class SoundController {

    /**
     * These Audioclips enable which ever sound file they point to to be played
     */

    public static final AudioClip THEME = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/pirates-of-the-caribbean-8-bit.wav")
    );

    public static final AudioClip GAMEOVER = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/gameover.wav")
    );

    public static final AudioClip DEATH = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/death.wav")
    );

    public static final AudioClip PAUSE = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/PAUSE.wav")
    );

    public static final AudioClip POWERUP = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/PowerUp.wav")
    );

    public static final AudioClip BOMBEXPLODE = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/BombExplode.wav")
    );

    public static final AudioClip SELECT = Applet.newAudioClip(
            SoundController.class.getResource("/res/raw/select.wav")
    );


}


