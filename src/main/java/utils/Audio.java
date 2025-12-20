package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Audio {

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
