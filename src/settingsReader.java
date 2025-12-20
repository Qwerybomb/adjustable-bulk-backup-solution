import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class  settingsReader {

    // main class variables
    private textFile settings = null;
    private final ArrayList<String> sourceDirs = new ArrayList<>();
    private String FinalDir = null;
    private int HoursSetting = 24;
    private int readHours = 0;
    private LocalDate decidedDate = LocalDate.now();
    private LocalTime decidedTime = LocalTime.now();

    // detector strings
    final String lineTargetDir = "░▒▓ Target Directories:";
    final String lineBackupDir = "░▒▓ Backup directory";
    final String lineRecordedHoursTime = "░▒▓ Hours elapsed until update";
    final String lineRecordedDateTime = "░▒▓ Previously recorded Date && time";

    public void RefreshSettings() throws IOException {

        String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        String currentDir = decodedPath.substring(0, decodedPath.lastIndexOf("/"));

        // make sure settings is non-null
        if (settings == null) {
            settings = new textFile(new File(currentDir, "/Settings.txt"));
        }

        // create new settings file if one isn't present
        if (!settings.getFile().exists()) {
            settings.getFile().createNewFile();
            settings.writeFile(lineTargetDir +
                            "\n\n" + lineBackupDir +
                            "\n\n" + lineRecordedHoursTime +
                            "\n" + "0/" + HoursSetting +
                            "\n" + lineRecordedDateTime +
                            "\n" + decidedDate + " 〗〖 " + decidedTime);
        }

        // prep for file reading
        String curLine = "";
        sourceDirs.clear();
        settings.refreshLines();

            for(int i = settings.findLineOf(lineTargetDir) + 1; !settings.getLine(i).equals(lineBackupDir); i++) {
                sourceDirs.add(settings.getLine(i));
            }

            // get backup directory
            FinalDir = settings.getLine(settings.findLineOf(lineBackupDir) + 1);

            // get hours needed
            curLine = settings.getLine(settings.findLineOf(lineRecordedHoursTime) + 1);
            HoursSetting = Integer.parseInt((curLine.substring(curLine.lastIndexOf("/") + 1, curLine.length())));
            readHours = Integer.parseInt((curLine.substring(0, curLine.lastIndexOf("/"))));

            // get the previous date
            curLine = settings.getLine(settings.findLineOf(lineRecordedDateTime) + 1);
            decidedDate = LocalDate.parse(curLine.substring(0,curLine.lastIndexOf(" 〗")));
            decidedTime = LocalTime.parse(curLine.substring(curLine.lastIndexOf("〖") + 2, curLine.length() - 1));
    }

    public void setSettingsLocation(String directory) {
        settings.setFile(new File(directory));
    }

    public String getFinalDirectory() {
        return FinalDir;
    }

    public ArrayList<String> getSourceDirectories() {
        return sourceDirs;
    }

    public void updateCurHours(int update) throws IOException {
        settings.lineUpdate(settings.findLineOf(lineRecordedHoursTime) + 1, update + "/" + HoursSetting);
    }

    public void updateCurDateTime(LocalDate newDate, LocalTime newTime) throws IOException {
        settings.lineUpdate(settings.findLineOf(lineRecordedDateTime) + 1, newDate.toString() + " 〗〖 " + newTime.toString());
    }

    public int getHoursSetting() {
      return HoursSetting;
    }

    public LocalDate getPreviousDate() {
        return decidedDate;
    }

    public LocalTime getPreviousHour() {
        return decidedTime;
    }

    public int getCurrentHours() {
        return readHours;
    }
}

class textFile {
  private File txtFile;
  private List<String> lines;
  private int length;

  textFile(File txtFile) {
      this.txtFile = txtFile;
      if (txtFile.exists()) {
          refreshLines();
      }
  }

  public void lineUpdate(int LineNum, String NewContent) throws IOException {
        lines.set(LineNum, NewContent);
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s).append("\n");
       }
        writeFile(builder.toString());
 }

  public String getLine(int lineNumber) throws IOException {
        return lines.get(lineNumber);
 }

  public void writeFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(txtFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
 }

  public void setFile(File txtFile) {
      this.txtFile = txtFile;
  }

  public File getFile() {
      return txtFile;
  }

  public void refreshLines() {
      try {
          lines = Files.readAllLines(Path.of(txtFile.getPath()));
          length = lines.size();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }

  public int getFileLength() {
      return length;
  }

  public int findLineOf(String identifier) {
      return lines.indexOf(identifier);
  }
}
