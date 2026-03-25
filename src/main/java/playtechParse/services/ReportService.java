package playtechParse.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class ReportService {
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());
    private static final String reportDirectory = "reports";
    private static final String defaultFileName = "results.txt";

    public ReportService() {
        File directory = new File(reportDirectory);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Report directory created: " + reportDirectory);
            }
        }
    }

    // Print list to console
    public void printList(String title, List<String> items) {
        System.out.println("--- " + title + " ---");
        if (items.isEmpty()) {
            logger.warning("Attempted to print an empty list for: " + title);
            return;
        }
        items.forEach(item -> System.out.println("- " + item));
        System.out.println();
    }

    // Save list to file
    public void saveToFile(String title, List<String> items) {
        File file = new File(reportDirectory, defaultFileName);
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(title + "\n");

            for (String item : items) {
                writer.write(" -" + item + "\n");
            }

            writer.write("\n");
            logger.info("Section '" + title + "' successfully appended to " + file.getPath());
        } catch (IOException e) {
            logger.severe("Failed to write to report: " + e.getMessage());        }
    }

    public void clearOldReport() {
        File file = new File(reportDirectory, defaultFileName);
        if (file.exists() && file.delete()) {
            logger.info("Old report cleared");
        }
    }
}