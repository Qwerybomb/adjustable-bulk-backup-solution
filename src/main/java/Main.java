import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Main {

    // obtain the current date
    static LocalDate Date = LocalDate.now();
    static LocalTime Time = LocalTime.now();
    static int Hour = Time.get(ChronoField.CLOCK_HOUR_OF_DAY);
    static int PreviousHour = Hour;
    static int elapsedHours = 0;

    // class requirements
    static settingsReader SettingsReader = new settingsReader();

    /**
     * Use the settings file and LocalDate/Time to verify what the current hours should be. meant to counteract sleep mode on certain operating systems.
     * @return the calculated current elapsed hours
     * @throws IOException
     */
    private static int verifyHours() throws IOException {

        int toElapse;

        Date = LocalDate.now();
        Time = LocalTime.now();

        SettingsReader.RefreshSettings();
        toElapse = SettingsReader.getCurrentHours();
        if (!SettingsReader.getPreviousDate().isEqual(Date)) {
            toElapse += (int) ((SettingsReader.getPreviousDate().until(Date).get(ChronoUnit.DAYS) * 24) - SettingsReader.getPreviousHour().getHour()) + Time.getHour();
        } else {
            toElapse += (int) Math.abs(SettingsReader.getPreviousHour().until(Time, ChronoUnit.HOURS));
        }
        SettingsReader.updateCurHours(elapsedHours);
        SettingsReader.updateCurDateTime(Date, Time);

        return toElapse;
    }

    public static void main(String[] args) throws IOException {

     elapsedHours = verifyHours();

     log.initLog();
     log.appendLog("Program began", true);

     // main run loop
     while (true) {

        Time = LocalTime.now();
        Hour = Time.get(ChronoField.CLOCK_HOUR_OF_DAY);

        // keep program from using excessive computer resources
         try {
             Thread.sleep(10000);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }

         elapsedHours = verifyHours();

         if (Hour != PreviousHour || elapsedHours > SettingsReader.getHoursSetting() - 1) {

            log.appendLog("hour elapsed at " + Hour, true);
            elapsedHours++;
            SettingsReader.RefreshSettings();
            SettingsReader.updateCurHours(elapsedHours);
            SettingsReader.updateCurDateTime(Date, Time);
            PreviousHour = Hour;

            // check if current passed hours = or is > than the setting for the backup timer
            if (elapsedHours > SettingsReader.getHoursSetting() - 1) {

                log.appendLog("beginning backup process", true);

                // reset timer
                elapsedHours = 0;
                SettingsReader.updateCurHours(0);

                // refresh settings and make the final directory with name and path
                SettingsReader.RefreshSettings();
                String dirPath = SettingsReader.getFinalDirectory() + "Automatic Backup on "+ Date.toString() + " Hour " + Hour;
                fileHandle.CreateDirectory(dirPath);

                for (String s : SettingsReader.getSourceDirectories()) {

                    // create directory inside main backup directory for each source
                    File SourceDir = new File(s);
                    fileHandle.CreateDirectory(dirPath + "/" + SourceDir.getName());
                    fileHandle.copyFiles(s, dirPath + "/" + SourceDir.getName());

                }
                log.appendLog("backupProcess Successful", true);
            }
        }
     }

    }
}
