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

}

