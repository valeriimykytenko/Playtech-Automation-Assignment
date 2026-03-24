package playtechParce.tests;

import playtechParce.pages.HomePage;     // Импортируем страницу
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import playtechParce.services.ReportService;

import java.util.List;

public class PlaytechTest {
    WebDriver driver;
    HomePage home;
    ReportService report = new ReportService();


    @BeforeEach
    public void setup() {
        // Initializing the Chrome driver session and set up an explicit wait for element synchronization
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        home = new HomePage(driver);
    }

    @Test
    @DisplayName("Task 2: Verify all team names are present")
    public void testTeamNamesExtraction() {
        home.open();
        home.acceptCookies();

        List<String> teamNames = home.getTeamNames();
        Assertions.assertFalse(teamNames.isEmpty(), "Team list is empty!");

        report.printList("Teams", teamNames);
        report.saveToFile("results.txt", "Task 2: Teams", teamNames);
    }

    @Test
    @DisplayName("Task 3: Verify research areas content")
    public void testResearchAreas() {
        home.open();
        home.acceptCookies();

        List<String> research = home.getResearchAreas();
        Assertions.assertTrue(research.size() >= 3, "Too few research areas found!");

        report.printList("Research", research);
        report.saveToFile("results.txt", "Task 3: Research", research);
    }

    @Test
    @DisplayName("Task 4: Verify Estonia job listings")
    public void testEstoniaJobs() {
        home.open();
        home.acceptCookies();

        List<String> jobs = home.getEstoniaJobLinks();
        Assertions.assertFalse(jobs.isEmpty(), "No jobs found in Estonia!");

        report.printList("Estonia Jobs", jobs);
        report.saveToFile("results.txt", "Task 4: Jobs", jobs);
    }

    @AfterEach
    public void tearDown() {
        // Clean up: closing the browser session
        if (driver != null) {
            driver.quit();
        }
    }
}
