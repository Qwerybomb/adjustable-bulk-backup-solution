import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirHandle {

    // create a directory at String Location
    public static void CreateDirectory(String Location) {
        File directory = new File(Location);
        if (directory.mkdir()) {
            System.out.println("Directory Succesfully Created at " + Location);
        } else {
            System.out.println("Failed to create Directory at at " + Location);
        }
    }

    // copy a directory from (String sourceDirectoryLocation) and place it at (String destinationDirectoryLocation)
    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
            Path destination = Paths.get(destinationDirectoryLocation, source.toString().substring(sourceDirectoryLocation.length() - 1));
            try {
                Files.copy(source, destination);
                System.out.println("copied Files from" + sourceDirectoryLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
