package playtechParce.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportService {

    // Print list to console
    public void printList(String title, List<String> items) {
        System.out.println(title);

        if (items.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        for (String item : items) {
            System.out.println("- " + item);
        }

        System.out.println();
    }

    // Save list to file
    public void saveToFile(String fileName, String title, List<String> items) {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            writer.write(title + "\n");

            for (String item : items) {
                writer.write(item + "\n");
            }

            writer.write("\n");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}