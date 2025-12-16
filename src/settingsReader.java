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
    private textFile settings;
    private ArrayList<String> sourceDirs = new ArrayList<>();
    private String settingsLocation = null;
    private String FinalDir = null;
    private int HoursSetting = 24;
    private int readHours = 0;
    private LocalDate decidedDate = LocalDate.now();
    private LocalTime decidedTime = LocalTime.now();

    // line numbers of the various settings
    int HoursElapsedLine = 0;
    int LastRecordedTimeLine = 0;

    // detector strings
    final String TargetDir = "░▒▓ Target Directories:";
    final String BackupDir = "░▒▓ Backup directory";
    final String HoursUpdate = "░▒▓ Hours elapsed until update";
    final String LastRecordedTime = "░▒▓ Previously recorded Date && time";

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
            settings.writeFile(TargetDir +
                            "\n\n" + BackupDir +
                            "\n\n" + HoursUpdate +
                            "\n" + "0/" + HoursSetting +
                            "\n" + LastRecordedTime +
                            "\n" + decidedDate + " 〗〖 " + decidedTime);
        }

        // prep for the actual file reading
        int breakout = 0;
        int curLineNum = 1;
        String curLine = "";
        sourceDirs.clear();
        settings.refreshLines();

            for(int i = curLineNum; !settings.getLine(i).equals(BackupDir); i++) {
                sourceDirs.add(settings.getLine(i));
                curLineNum++;
            }

            // get backup directory
            curLineNum += 1;
            FinalDir = settings.getLine(curLineNum);

            // get hours needed
            curLineNum += 2;
            curLine = settings.getLine(curLineNum);

            HoursSetting = Integer.parseInt((curLine.substring(curLine.lastIndexOf("/") + 1, curLine.length())));
            readHours = Integer.parseInt((curLine.substring(0, curLine.lastIndexOf("/"))));
            HoursElapsedLine = curLineNum;

            // get the previous date
            curLineNum += 2;
            curLine = settings.getLine(curLineNum);

            decidedDate = LocalDate.parse(curLine.substring(0,curLine.lastIndexOf(" 〗")));
            decidedTime = LocalTime.parse(curLine.substring(curLine.lastIndexOf("〖") + 2, curLine.length() - 1));
            LastRecordedTimeLine = curLineNum;
    }

    public void setSettingsLocation(String directory) {
        settings.setSettings(new File(directory));
    }

    public String getFinalDirectory() {
        return FinalDir;
    }

    public ArrayList<String> getSourceDirectories() {
        return sourceDirs;
    }

    public void updateCurHours(int update) throws IOException {
        settings.lineUpdate(HoursElapsedLine, update + "/" + HoursSetting);
    }

    public void updateCurDateTime(LocalDate newDate, LocalTime newTime) throws IOException {
        settings.lineUpdate(LastRecordedTimeLine, newDate.toString() + " 〗〖 " + newTime.toString());
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

  public void setSettings(File settingsFile) {
      this.txtFile = settingsFile;
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
}
