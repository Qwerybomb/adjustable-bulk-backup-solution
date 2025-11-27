import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;


public class settingsReader {

    // main class variables
    ArrayList<String> SourceDirs = new ArrayList<>();
    String settingsLocation = null;
    String FinalDir = null;

    // detector strings
    String TargetDir = "░▒▓ Target Directories:";
    String BackupDir = "░▒▓ Backup directory";

    settingsReader() {

    }

    public void RefreshSettings() throws IOException {

        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        String CurrentDir = decodedPath.substring(0, decodedPath.lastIndexOf("/"));

        // if settings file doesn't exist. create the fill it with the basics.
        File settings = new File(CurrentDir, "Settings.txt");
        if (!settings.exists()) {
           settings.createNewFile();
            FileWriter fileWriter = new FileWriter(settings);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(TargetDir + "\n\n" + BackupDir + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
            settingsLocation = settings.getPath();
        } else {
            settingsLocation = settings.getPath();
        }

        // prep for the actual file reading
        int breakout = 0;
        BufferedReader br = new BufferedReader(new FileReader(settingsLocation));
        SourceDirs.clear();

        try {

            // skip forward a few lines to begin reading the directories
            String line = br.readLine();
            line = br.readLine();

            while (!line.equals(BackupDir) || breakout > 100) {

                SourceDirs.add(line);
                line = br.readLine();
                breakout++;
            }

            // get backup directory
            FinalDir = br.readLine();

        } finally {
            br.close();
        }

    }

    public void setSettingsLocation(String directory) {
        settingsLocation = directory;
    }

    public String getFinalDirectory() {
        return FinalDir;
    }

    public ArrayList<String> getSourceDirectories() {
        return SourceDirs;
    }
}
