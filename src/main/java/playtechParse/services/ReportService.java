package playtechParse.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class ReportService {
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());
    private static final String REPORT_DIRECTORY = "reports";
    private static final String DEFAULT_FILE_NAME = "results.txt";

    // Ensure report directory exists before writing results
    public ReportService() {
        File directory = new File(REPORT_DIRECTORY);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Report directory created: " + REPORT_DIRECTORY);
            }
        }
    }

    public void printList(String title, List<String> items) {
        System.out.println("--- " + title + " ---");
        if (items.isEmpty()) {
            logger.info("Attempted to print an empty list for: " + title);
            return;
        }
        items.forEach(item -> System.out.println("- " + item));
        System.out.println();
    }

    // Append results to a single file to keep all output data
    public void saveToFile(String title, List<String> items) {
        File file = new File(REPORT_DIRECTORY, DEFAULT_FILE_NAME );
        try (FileWriter writer = new FileWriter(file, true)) {

            writer.write(title + "\n");

            for (String item : items) {
                writer.write("- " + item + "\n");
            }

            writer.write("\n");
            logger.info("Section '" + title + "' successfully appended to " + file.getPath());
        } catch (IOException e) {
            logger.severe("Failed to write to report: " + e.getMessage());        }
    }

    // Clean previous test results to avoid mixing data
    public void deleteOldReport() {
        File file = new File(REPORT_DIRECTORY, DEFAULT_FILE_NAME );
        if (file.exists() && file.delete()) {
            logger.info("Old report cleared");
        }
    }
}