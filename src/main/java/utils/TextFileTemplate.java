package utils;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * uses a String to locate a particular line in the file
     * @param identifier the String text to locate
     * @return returns the lines at location
     */
    public int findLineOf(String identifier) {
        if (lines != null) {
            return lines.indexOf(identifier);
        } else {
            return -1;
        }
    }

    /**
     * get the raw string data of a particular line in a file
     * @param lineNumber the specific line number to obtain the information from
     * @return the information found at line lineNumber
     */
    public String getLine(int lineNumber) throws IOException {
        if (lines != null) {
            return lines.get(lineNumber);
        } else {
            return "lines is not yet initialized. try calling refreshLines() first";
        }
    }

    /**
     * obtains the length of the file in zero indexing format
     * @return the length of the file in zero indexing format
     */
    public int getLengthIndexForm() {
        return lines.size() - 1;
    }

    /**
     * obtains the length of the file in 1 indexing format (like in notepad or others0
     * @return the length of the file in 1 indexing format
     */
    public int getLengthActualForm() {
        return lines.size();
    }

    /**
     * obtains a copy of the current line state of the file at any given moment
     * @return an effective copy of the lines object
     */
    public ArrayList<String> getLinesCopy() {
        ArrayList<String> linesCopy = new ArrayList<>(lines.size());

        linesCopy.addAll(lines);

        return linesCopy;
    }
}
