import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
            System.out.println("Failed to run audio");
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

 class Audio {

    private final AudioInputStream audioInputStream;

    Audio(File file) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    Audio(InputStream stream) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    Audio(URL url) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(url);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }
}
