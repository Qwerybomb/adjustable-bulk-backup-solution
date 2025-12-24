package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExternalTextFile extends TextFileTemplate {

    private File textFile;

    public ExternalTextFile(File txtFile) {
        this.textFile = txtFile;
        if (txtFile.exists()) {
            refreshLines();
        }
    }

    @Override
    public void refreshLines() {
        try {
            lines = Files.readAllLines(Path.of(textFile.getPath()));
            length = lines.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lineUpdate(int LineNum, String NewContent) throws IOException {
        lines.set(LineNum, NewContent);
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s).append("\n");
        }
        writeFile(builder.toString());
    }

    @Override
    public void writeFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(textFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public File getFile() {
        return textFile;
    }
}
