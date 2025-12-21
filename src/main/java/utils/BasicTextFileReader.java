package utils;

import java.io.IOException;
import java.util.List;

public abstract class BasicTextFileReader {

    protected List<String> lines;
    protected int length;

    private boolean exists;
    private boolean canRead;
    private boolean canWrite;
    private boolean internal;

    abstract void refreshLines();
    abstract void lineUpdate(int LineNum, String NewContent) throws IOException;
    abstract String getLine(int lineNumber) throws IOException;
    abstract void writeFile(String content) throws IOException;
    abstract int findLineOf(String identifier);
}
