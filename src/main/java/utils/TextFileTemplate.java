package utils;

import java.io.IOException;
import java.util.List;

public abstract class TextFileTemplate {

    protected List<String> lines;
    protected int length;

    private boolean exists;
    private boolean canRead;
    private boolean canWrite;
    private boolean internal;

    public abstract void refreshLines() throws IOException;
    public abstract void lineUpdate(int LineNum, String NewContent) throws IOException;
    public abstract void writeFile(String content) throws IOException;

    public int findLineOf(String identifier) {
        if (lines != null) {
            return lines.indexOf(identifier);
        } else {
            return -1;
        }
    }

    public String getLine(int lineNumber) throws IOException {
        if (lines != null) {
            return lines.get(lineNumber);
        } else {
            return "lines is not yet initialized. try calling refreshLines() first";
        }
    }
}
