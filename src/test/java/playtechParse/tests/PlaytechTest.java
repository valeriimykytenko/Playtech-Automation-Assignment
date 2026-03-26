package playtechParse.tests;

import java.util.List;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import playtechParse.pages.PlaytechSite;
import playtechParse.services.ReportService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlaytechTest {

    WebDriver driver;
    PlaytechSite site;
    ReportService report = new ReportService();

    @BeforeAll
    public void setup() {

        // Initialize browser and main test dependencies
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        site = new PlaytechSite(driver);

        // Initialize browser and main test dependencies
        report.clearOldReport();
    }

    @Test
    @DisplayName("Task 1: Verify website is opened")
    @Order(1)
    public void websiteOpensChecking() {

        // Open homepage and verify correct page is loaded
        site.openHome();

        String actualTitle = driver.getTitle();
        String expectedTitle = "Home - Playtech People";
        Assertions.assertEquals(
                expectedTitle,
                actualTitle);
    }

    @Test
    @DisplayName("Task 2: Verify all team names are present")
    @Order(2)
    public void testTeamNamesExtraction() {

        // Extract and validate team data from homepage
        List<String> teamNames = site.getTeamNames();
        Assertions.assertFalse(
                teamNames.isEmpty(),
                "Team list is empty!");

        report.printList("Teams", teamNames);

        // Save results to file for reporting purposes
        report.saveToFile("Task 2: Teams", teamNames);
    }

    @Test
    @DisplayName("Task 3: Verify research areas content")
    @Order(3)
    public void testResearchAreas() {

        // Extract and validate team data from homepage
        List<String> research = site.getResearchAreas();
        Assertions.assertTrue(
                research.size() >= 3,
                "Too few research areas found!"
        );

        report.printList("Research", research);
        report.saveToFile("Task 3: Research", research);
    }

    @Test
    @Order(4)
    @DisplayName("Task 4: Verify Estonia job listings")
    public void testEstoniaJobs() {

        // Ensure browser session is properly closed
        List<String> jobs = site.getEstoniaJobLinks();
        Assertions.assertFalse(
                jobs.isEmpty(),
                "No jobs found in Estonia!");

        report.printList("Estonia Jobs", jobs);
        report.saveToFile("Task 4: Jobs", jobs);
    }

    @AfterAll
    public void tearDown() {
        // Clean up: closing the browser session
        if (driver != null) {
            driver.quit();
        }
    }
}
