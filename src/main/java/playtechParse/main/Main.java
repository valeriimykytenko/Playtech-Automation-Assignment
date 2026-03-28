package playtechParse.main;

import org.openqa.selenium.WebDriver;
import playtechParse.pages.PlaytechWebsite;
import playtechParse.services.WebDriverManager;
import playtechParse.services.ReportService;

import java.util.List;

/**
 * Entry point to run the data extraction without JUnit.
 * Opens the site, collects required data,
 * and writes results to a file.
 */
public class Main {
    private PlaytechWebsite site;
    private ReportService report;
    private WebDriverManager driverManager ;

    public void setUp(){
        driverManager = new WebDriverManager();
        WebDriver driver = driverManager.createDriver();
        report = new ReportService();
        report.deleteOldReport();
        site = new PlaytechWebsite(driver);
    }

    public void tearDown(){
        driverManager.shutDown();
    }

    public void playParse(){
        List<String> teamNames = site.getTeamNames();
        report.printList("List of " + teamNames.size() + " Playtech Teams", teamNames);
        report.saveToFile("Task 2: List of " + teamNames.size() + " Playtech Teams", teamNames);

        List<String> research = site.getResearchAreas();
        report.printList("Research areas", research);
        report.saveToFile("Task 3: Research areas", research);

        String job = site.getJobLinkForTallinnAndTartu();
        report.printList("Tallinn & Tartu Job", List.of(job));
        report.saveToFile("Task 4: Tallinn & Tartu Job", List.of(job));
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setUp();
        app.playParse();
        app.tearDown();
    }
}
