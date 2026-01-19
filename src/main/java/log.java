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
           logFile = new ExternalTextFile(new File(currentDir + "/BackupActionLog"));
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
     */
    public static void appendLog(String NewLine) throws IOException {
        ArrayList<String> fileLines = logFile.getLinesCopy();
        fileLines.add(NewLine);

        StringBuilder fileBuilder = new StringBuilder();

        for (String s : fileLines) {
            fileBuilder.append(s);
            fileBuilder.append("\n");
        }

        logFile.writeFile(fileBuilder.toString());
    }
}
