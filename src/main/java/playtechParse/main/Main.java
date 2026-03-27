package playtechParse.main;

import org.openqa.selenium.WebDriver;
import playtechParse.pages.PlaytechWebsite;
import playtechParse.services.WebDriverManager;
import playtechParse.services.ReportService;

import java.util.List;

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

        List<String> jobs = site.getEstoniaJobLinks();
        report.printList("Estonia Jobs Links", jobs);
        report.saveToFile("Task 4: Jobs Links from Estonia", jobs);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setUp();
        app.playParse();
        app.tearDown();
    }
}
