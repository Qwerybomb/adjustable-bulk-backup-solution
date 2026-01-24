import utils.ExternalTextFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class log {

    public static ExternalTextFile logFile;

    /**
     * Initializes the log and creates the file if it doesn't exist
     */
    public static void initLog() throws UnsupportedEncodingException {

        if (logFile == null) {
           String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
           String decodedPath = URLDecoder.decode(path, "UTF-8");
           String currentDir = decodedPath.substring(0, decodedPath.lastIndexOf("/"));
           logFile = new ExternalTextFile(new File(currentDir + "/BackupActionLog.txt"));
        }

        if (!logFile.getFile().exists()) {
            try {
                logFile.getFile().createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        logFile.refreshLines();
    }

    /**
     * adds a new line to the log file.
     * @param NewLine the new line to add
     * @param IncludeDate weather to include the current date or not
     */
    public static void appendLog(String NewLine, boolean IncludeDate) {
        ArrayList<String> fileLines = logFile.getLinesCopy();
        fileLines.add((IncludeDate ? LocalTime.now() + " " + LocalDate.now() + " : " : "") + NewLine );

        StringBuilder fileBuilder = new StringBuilder();

        for (String s : fileLines) {
            fileBuilder.append(s);
            fileBuilder.append("\n");
        }

        try {
            logFile.writeFile(fileBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logFile.refreshLines();
    }
}
