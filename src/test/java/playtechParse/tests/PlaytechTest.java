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

        // Prepare clean reporting environment
        report.clearOldReport();
    }

    @Test
    @Order(1)
    @DisplayName("Task 1: Verify website is opened")
    public void websiteOpensChecking() {

        // Open homepage and verify correct page is loaded
        site.openHome();

        String actualTitle = driver.getTitle();
        String expectedTitle = "Home - Playtech People";
        Assertions.assertEquals(
                expectedTitle,
                actualTitle);
        System.out.println("Home page opened");
    }

    @Test
    @Order(2)
    @DisplayName("Task 2: Verify all team names are present")
    public void testTeamNamesExtraction() {

        // Extract and validate team data from homepage
        List<String> teamNames = site.getTeamNames();
        Assertions.assertFalse(
                teamNames.isEmpty(),
                "Team list is empty!");

    }

    @Test
    @Order(3)
    @DisplayName("Task 2.1: Verify team names are unique")
    public void testTeamNamesUnique() {
        List<String> teamNames = site.getTeamNames();
        long uniqueCount = teamNames.stream().distinct().count();

        Assertions.assertEquals(
                teamNames.size(), uniqueCount,
                "Duplicate team names found!");
    }

    @Test
    @Order(4)
    @DisplayName("Task 3: Verify research areas content")
    public void testResearchAreas() {

        // Extract and validate team data from homepage
        List<String> research = site.getResearchAreas();
        Assertions.assertTrue(
                research.size() >= 3,
                "Too few research areas found!"
        );
    }

    @Test
    @Order(5)
    @DisplayName("Task 4: Verify Estonia job listings")
    public void getEstoniaJobLinks() {
        List<String> jobs = site.getJobsLinksByLocation("estonia");
        Assertions.assertFalse(
                jobs.isEmpty(),
                "No jobs found in Estonia!");
    }

    @Test
    @Order(6)
    @DisplayName("Task 4.1: Verify job links format")
    public void testJobLinksFormat() {
        List<String> jobs = site.getJobsLinksByLocation("estonia");

        for (String link : jobs) {
            Assertions.assertTrue(
                    link.startsWith("https"),
                    "Invalid job link: " + link);
        }
    }

    @AfterAll
    public void tearDown() {
        // Clean up: closing the browser session
        if (driver != null) {
            driver.quit();
        }
    }
}
