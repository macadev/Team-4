package SystemController;
import java.applet.Applet;
import java.applet.AudioClip;

/**
 * Created by FloMac on 14-11-21.
 */
public class SoundController {

    public static final AudioClip THEME = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/pirates-of-the-caribbean-8-bit.wav")
    );

    public static final AudioClip GAMEOVER = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/gameover.wav")
    );

    public static final AudioClip DEATH = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/death.wav")
    );

    public static final AudioClip PAUSE = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/PAUSE.wav")
    );

    public static final AudioClip POWERUP = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/PowerUp.wav")
    );

    public static final AudioClip BOMBEXPLODE = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/BombExplode.wav")
    );

    public static final AudioClip SELECT = Applet.newAudioClip(
            SoundController.class.getResource("/resources/Music/select.wav")
    );


}


