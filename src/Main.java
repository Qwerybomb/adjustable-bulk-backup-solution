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
    static int elapsedHours = 0;


    // helpers
    static settingsReader SettingsReader = new settingsReader();

    public static void main(String[] args) throws IOException {

     System.out.println("Successfully initialized");

     SettingsReader.RefreshSettings();
     elapsedHours = SettingsReader.getCurrentHours();

     // main run loop
     while (true) {

        Time = LocalTime.now();
        Hour = Time.get(ChronoField.CLOCK_HOUR_OF_DAY);

        // keep program from using excessive computer resources
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }

         if (Hour != PreviousHour) {

            Date = LocalDate.now(); // date only updates every hour cause why would it need to update every iteration?

            System.out.println("HourElapsed");
            elapsedHours++;
            SettingsReader.RefreshSettings();
            SettingsReader.updateCurHours(elapsedHours);
            PreviousHour = Hour;

            // check if current passed hours = the setting for the backup timer
            if (elapsedHours == SettingsReader.getHoursSetting()) {

                // reset timer
                elapsedHours = 0;
                SettingsReader.updateCurHours(0);

                // refresh settings and make the final directory with name and path
                SettingsReader.RefreshSettings();
                String dirPath = SettingsReader.getFinalDirectory() + "Automatic Backup on "+ Date.toString() + " Hour " + Hour;
                DirHandle.CreateDirectory(dirPath);

                for (String s : SettingsReader.getSourceDirectories()) {

                    // create directory inside main backup directory for each source
                    File file = new File(s);
                    DirHandle.CreateDirectory(dirPath + "/" + file.getName());
                    DirHandle.copyDirectory(s, dirPath + "/" + file.getName());

                }
                System.out.println("backupProcess Successful");
            }
        }
     }

    }
}
