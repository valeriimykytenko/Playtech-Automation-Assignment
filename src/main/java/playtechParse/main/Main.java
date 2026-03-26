package playtechParse.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import playtechParse.pages.PlaytechSite;
import playtechParse.services.ReportService;

import java.util.List;

public class Main {
    WebDriver driver;
    PlaytechSite site;
    ReportService report;

    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        report = new ReportService();
        site = new PlaytechSite(driver);
    }
    public void playParse(){

//        site.openHome();
        report.clearOldReport();

        List<String> teamNames = site.getTeamNames();
        long teamCount = teamNames.stream().distinct().count();
        report.printList("List of  "+teamCount+" Playtech Teams", teamNames);
        report.saveToFile("Task 2: List of "+teamCount+" Playtech Teams", teamNames);

        List<String> research = site.getResearchAreas();
        report.printList("Research areas", research);
        report.saveToFile("Task 3: Research areas", research);

        List<String> jobs = site.getJobsLinksByLocation("estonia");
        report.printList("Estonia Jobs Links", jobs);
        report.saveToFile("Task 4: Jobs Links from Estonia", jobs);

        driver.quit();
    }
    public static void main(String[] args) {
        Main app = new Main();
        app.setUp();
        app.playParse();
    }
}
