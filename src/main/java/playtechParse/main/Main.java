package playtechParse.main;

import org.openqa.selenium.WebDriver;
import playtechParse.pages.PlaytechWebsite;
import playtechParse.services.BrowserManager;
import playtechParse.services.ReportService;

import java.util.List;

/**
 * Entry point to run the data extraction without JUnit.
 * Opens the site, collects required data,
 * and writes results to a file.
 */
public class Main {
    private WebDriver driver;
    private PlaytechWebsite site;
    private ReportService report;
    private BrowserManager driverManager ;

    public void setup(){
        driverManager = new BrowserManager();
        driver = driverManager.createDriver();
        report = new ReportService();
        report.deleteOldReport();
        site = new PlaytechWebsite(driver);
    }

    public void tearDown(){
        driverManager.shutDown();
    }

    public void playParse(){
        List<String> teamNames = site.getTeamNames();
        report.reportInfo("Task 2: List of " + teamNames.size() + " Playtech Teams", teamNames);

        List<String> research = site.getResearchAreas();
        report.reportInfo("Task 3: Research areas", research);

        String job = site.getJobLinkForTallinnAndTartu();
        report.reportInfo("Task 4: Tallinn & Tartu Job", List.of(job));
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setup();
        app.playParse();
        app.tearDown();
    }
}
