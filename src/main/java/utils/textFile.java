package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class textFile {
    private File txtFile;
    private List<String> lines;
    private int length;

    public textFile(File txtFile) {
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
