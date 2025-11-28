import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class settingsReader {

    // main class variables
    private File settings;
    private ArrayList<String> SourceDirs = new ArrayList<>();
    private String settingsLocation = null;
    private String FinalDir = null;
    private int HoursSetting = 24;
    private int readHours = 0;

    // detector strings
    String TargetDir = "░▒▓ Target Directories:";
    String BackupDir = "░▒▓ Backup directory";
    String HoursUpdate = "░▒▓ Hours elapsed until update";

    settingsReader() {

    }

    public void RefreshSettings() throws IOException {

        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        String currentDir = decodedPath.substring(0, decodedPath.lastIndexOf("/"));

        // if settings file doesn't exist. create the fill it with the basics.
        settings = new File(currentDir, "/Settings.txt");
        if (!settings.exists()) {
           settings.createNewFile();
            writeFile(settings,TargetDir + "\n\n" + BackupDir + "\n\n" + HoursUpdate + "\n" + "0/" + HoursSetting);
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

            // get hours needed
            line = br.readLine();
            line = br.readLine();
            HoursSetting = Integer.parseInt((line.substring(line.lastIndexOf("/") + 1, line.length())));
            readHours = Integer.parseInt((line.substring(0,line.lastIndexOf("/"))));

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

    public void updateCurHours(int update) throws IOException {


        List<String> lines = Files.readAllLines(Path.of(settings.getPath()));
        lines.set(lines.size() -1, update + "/" + HoursSetting);
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s + "\n");
        }
        writeFile(settings, builder.toString());
    }

    public void writeFile(File file, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public int getHoursSetting() {
      return HoursSetting;
    }

    public int getCurrentHours() {
        return readHours;
    }
}
