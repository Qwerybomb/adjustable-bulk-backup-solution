package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class textFile {

    private File txtFile;
    private InputStream fileStream;

    private List<String> lines;
    private int length;

    private boolean exists;
    private boolean canRead;
    private boolean canWrite;
    private boolean internal;

    public textFile(File txtFile) throws IOException {
        this.txtFile = txtFile;
        if (txtFile.exists()) {
            refreshLines();
            exists = true;
            canRead = txtFile.canRead();
            canWrite = txtFile.canWrite();
            internal = true;
        } else {
            exists = false;
        }
    }
    public textFile(InputStream fileData) throws IOException {
        this.fileStream = fileData;
        internal = false;
        canRead = true;
        canWrite = false;
        exists = true;

        refreshLines();
    }

    public void lineUpdate(int LineNum, String NewContent) throws IOException {
        if (!canWrite) { return; }
        lines.set(LineNum, NewContent);
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s).append("\n");
        }
        writeFile(builder.toString());
    }

    public String getLine(int lineNumber) throws IOException {
        if (!canRead) { return "Cannot read file"; }
        return lines.get(lineNumber);
    }

    public void writeFile(String content) throws IOException {
        if (!canWrite) { return; }
        FileWriter fileWriter = new FileWriter(txtFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public void setFile(File txtFile) {
        this.txtFile = txtFile;
    }
    public void setFile(InputStream stream) {
        this.fileStream = stream;
    }

    public File getExternalFile() {
        return txtFile;
    }

    public void refreshLines() throws IOException {
      if (!canRead) { return; }
      if (txtFile != null) {

          // read from file on disk
          try {
              lines = Files.readAllLines(Path.of(txtFile.getPath()));
              length = lines.size();
          } catch (IOException e) {
              throw new RuntimeException(e);
          }

      } else if (fileStream != null) {

         // read from inputStream
          if ( lines == null) {
            lines = new ArrayList<>();
          }
         lines.clear();
         BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
          while(reader.ready()) {
            lines.add(reader.readLine());
          }

      } else {
          System.out.println("No readable object presented");
      }
    }

    public int getFileLength() {
        if (!canRead) { return -1; }
        return length;
    }

    public int findLineOf(String identifier) {
        if (!canRead) { return -1;}
        return lines.indexOf(identifier);
    }

    public void createExternalFile() {
        if (!txtFile.exists() && txtFile != null) {
            try {
                txtFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("invalid file creation type");
        }
    }
}
