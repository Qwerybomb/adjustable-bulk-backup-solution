import utils.Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.HashMap;

public class audioHandle {

    private static final HashMap<situation, Audio> audios = new HashMap<>();

    public static void playSound(situation context) {
        try {
            AudioInputStream audioInputStream = audios.get(context).getAudioInputStream();
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception ex) {
            log.appendLog("failed to run audio with exception" + ex.getCause(), false);
            ex.printStackTrace();
        }
    }

    public static void setAudio(Audio audio, situation context) {
        if (audios.containsKey(context)) {
            audios.replace(context, audio);
        } else {
            audios.put(context, audio);
        }
    }

    public enum situation {
        Error(),
        Success();
    }
}

