import java.util.*;
import edu.duke.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;

/**
 * The ResourceLoader class provides a utility method 
 * to read and return lines from either a local file 
 * or a URL as an ArrayList<String>, 
 * handling both file paths and web resources seamlessly.
 * 
 * @author Vaishali Vyas
 * @version 2024-02-12
 */
public class ResourceLoader {
    /**
     * Reads lines from a file or URL and returns them as an ArrayList.
     * @param sourcePath Path to the file or URL to read.
     * @return ArrayList containing lines read from the source.
     */
    public static ArrayList<String> readResourceLines(String sourcePath) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            if (sourcePath.startsWith("http")) {
                URLResource resource = new URLResource(sourcePath);
                resource.lines().forEach(lines::add);
            } else {
                Files.lines(Paths.get(sourcePath)).forEach(lines::add);
            }
        } catch (Exception e) {
            System.err.println("Error accessing source: " + sourcePath + " - " + e.getMessage());
        }
        return lines;
    }
}
