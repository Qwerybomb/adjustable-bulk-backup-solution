import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class Main {

    // obtain the current date
    static LocalDate Date = LocalDate.now();
    static LocalTime Time = LocalTime.now();
    static int Hour = Time.get(ChronoField.CLOCK_HOUR_OF_DAY);
    static int PreviousHour = Hour;
    static int elapsedHours = 24;


    // helpers
    static settingsReader SettingsReader = new settingsReader();

    public static void main(String[] args) throws IOException {

     SettingsReader.RefreshSettings();

     // main run loop
     while (true) {

        Hour = Time.get(ChronoField.CLOCK_HOUR_OF_DAY);

        if (Hour != PreviousHour) {
            System.out.println("beginning backup process");
            elapsedHours++;
            SettingsReader.updateCurHours(elapsedHours);
            PreviousHour = Hour + 1;

            // check if current passed hours = the setting for the backup timer
            if (elapsedHours == SettingsReader.HoursSetting) {

                // reset timer
                elapsedHours = 0;
                SettingsReader.updateCurHours(0);

                // refresh settings and make the final directory with name and path
                SettingsReader.RefreshSettings();
                String dirPath = SettingsReader.getFinalDirectory() + Date.toString();
                DirHandle.CreateDirectory(dirPath);

                for (String s : SettingsReader.getSourceDirectories()) {

                    // create directory inside main backup directory for each source
                    File file = new File(s);
                    DirHandle.CreateDirectory(dirPath + "/" + file.getName());
                    DirHandle.copyDirectory(s, dirPath + "/" + file.getName());

                }
            }
            System.out.println("backupProcess Successful");
        }
     }

    }
}
