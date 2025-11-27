import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class Main {

    // obtain the current date
    static LocalDate Date = LocalDate.now();

    // helpers
    static DirHandle directoryManager = new DirHandle();
    static settingsReader SettingsReader = new settingsReader();

    public static void main(String[] args) throws IOException {

     SettingsReader.RefreshSettings();

     System.out.println(SettingsReader.getSourceDirectories());
     System.out.println(SettingsReader.getFinalDirectory());

    }
}
