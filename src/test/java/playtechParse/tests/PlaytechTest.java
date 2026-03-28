package playtechParse.tests;

import java.util.List;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import playtechParse.pages.PlaytechWebsite;
import playtechParse.services.WebDriverManager;
import playtechParse.services.ReportService;

/**
 * End-to-end tests for playtechpeople.com
 * Verifies page content, team data, research areas, and job listings
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class PlaytechTest {
    private WebDriver driver;
    private PlaytechWebsite site;
    private ReportService report;
    private WebDriverManager driverManager ;

    @BeforeAll
    public void setup() {
        driverManager  = new WebDriverManager();
        driver = driverManager.createDriver();
        report = new ReportService();
        report.deleteOldReport();
        site = new PlaytechWebsite(driver);
    }

    @Test
    @DisplayName("Task 1.0: Home page is opened and title is correct")
    public void websiteOpensChecking() {
        // Open homepage and verify correct page is loaded
        String actualTitle = site.getHomeTitle();
        Assertions.assertEquals("Home - Playtech People", actualTitle,
                "Unexpected page title: " + actualTitle);
    }

    @Test
    @DisplayName("Task 2.0: Team list is not empty")
    public void testTeamNamesExtraction() {
        // Extract and validate team data from homepage
        List<String> teamNames = site.getTeamNames();
        Assertions.assertFalse(teamNames.isEmpty(),
                "Team list is empty!");
    }

    @Test
    @DisplayName("Task 2.1: Team names are unique")
    public void testTeamNamesUnique() {
        List<String> teamNames = site.getTeamNames();
        long uniqueCount = teamNames.stream().distinct().count();
        Assertions.assertEquals(teamNames.size(), uniqueCount,
                "Duplicate team names found!");
    }


    @Test
    @DisplayName("Task 3.0: Research areas are present")
    public void testResearchAreas() {
        // Extract and validate team data from homepage
        List<String> research = site.getResearchAreas();
        Assertions.assertTrue(research.size() >= 3,
                "Too few research areas found!"
        );
    }

    @Test
    @DisplayName("Task 4: Job available in both Tallinn and Tartu")
    public void testEstoniaJobBothCities() {
        String links = site.getJobLinkForTallinnAndTartu();
        Assertions.assertFalse(links.isEmpty(),
                "No job found available in both Tallinn and Tartu!");
    }

    @Test
    @DisplayName("Task 4.1: Job links are secured and start with https")
    public void testJobLinksFormat() {
        List<String> jobs = site.getEstoniaJobLinks();
        for (String link : jobs) {
            Assertions.assertTrue(link.startsWith("https"),
                    "Invalid job link: " + link);
        }
    }

    @Test
    @DisplayName("Task 4.2: Unknown location returns empty list")
    public void testUnknownLocationReturnsEmpty() {
        List<String> jobs = site.getJobsLinksByLocation("notestonia");
        Assertions.assertTrue(jobs.isEmpty(),
                "Expected empty list for unknown location!");
    }

    @AfterAll
    public void tearDown() {
        driverManager.shutDown();
    }
}
