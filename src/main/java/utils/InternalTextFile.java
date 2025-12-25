package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InternalTextFile extends TextFileTemplate {

    InputStream fileStream;

    public InternalTextFile(InputStream internalFile) throws IOException {
        this.fileStream = internalFile;
        refreshLines();
    }

    @Override
    public void refreshLines() throws IOException {
        if ( lines == null) {
            lines = new ArrayList<>();
        }
        lines.clear();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
        while(reader.ready()) {
            lines.add(reader.readLine());
        }
    }

    @Override
    public void lineUpdate(int LineNum, String NewContent) throws IOException {
         System.out.println("Cant be read");
    }

    @Override
    public void writeFile(String content) throws IOException {
         System.out.println("cant be wrote");
    }
}
