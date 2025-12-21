package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class textTest extends BasicTextFileReader {

    private File txtFile;

    textTest(File txtFile) {
        this.txtFile = txtFile;
        if (txtFile.exists()) {
            refreshLines();
        }
    }

    @Override
    void refreshLines() {
        try {
            lines = Files.readAllLines(Path.of(txtFile.getPath()));
            length = lines.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void lineUpdate(int LineNum, String NewContent) throws IOException {
        lines.set(LineNum, NewContent);
        StringBuilder builder = new StringBuilder();
        for (String s : lines) {
            builder.append(s).append("\n");
        }
        writeFile(builder.toString());
    }

    @Override
    String getLine(int lineNumber) throws IOException {
        return lines.get(lineNumber);
    }

    @Override
    void writeFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(txtFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    @Override
    int findLineOf(String identifier) {
        return lines.indexOf(identifier);
    }
}
