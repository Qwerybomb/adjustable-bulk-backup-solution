import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;

public class audioHandle {

    private static final HashMap<situation, File> audios = new HashMap<>();

    public static void playSound(situation context) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audios.get(context).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception ex) {
            System.out.println("Failed to run audio");
        }
    }

    public static void setAudio(File audio, situation context) {
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
