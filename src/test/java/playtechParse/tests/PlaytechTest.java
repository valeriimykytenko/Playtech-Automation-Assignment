package playtechParse.tests;

import java.util.List;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import playtechParse.pages.PlaytechWebsite;
import playtechParse.services.BrowserManager;
import playtechParse.services.ReportService;

import static org.junit.jupiter.api.Assertions.*;

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
    private BrowserManager driverManager;

    @BeforeAll
    public void setup() {
        driverManager  = new BrowserManager();
        driver = driverManager.createDriver();
        report = new ReportService();
        report.deleteOldReport();
        site = new PlaytechWebsite(driver);
    }

    @Test
    @DisplayName("Task 1.0: Home page is opened and title is correct")
    public void websiteOpensChecking() {
        String actualTitle = site.getHomeTitle();
        assertEquals("Home - Playtech People", actualTitle);
    }

    @Test
    @DisplayName("Task 2.0: Team list is not empty")
    public void testTeamNamesExtraction() {
        List<String> teamNames = site.getTeamNames();
        assertFalse(teamNames.isEmpty(), "Team list was found empty on the homepage");
    }

    @Test
    @DisplayName("Task 2.1: Team names are unique")
    public void testTeamNamesUnique() {
        List<String> teamNames = site.getTeamNames();
        long uniqueCount = teamNames.stream().distinct().count();
        assertEquals(teamNames.size(), uniqueCount, "Duplicate team names found!");
    }

    @Test
    @DisplayName("Task 3.0: Research areas are present")
    public void testResearchAreas() {
        List<String> research = site.getResearchAreas();
        assertTrue(research.size() >= 3, "Too few research areas found!");
    }

    @Test
    @DisplayName("Task 4: Job available in both Tallinn and Tartu")
    public void testEstoniaJobBothCities() {
        String links = site.getJobLinkForTallinnAndTartu();
        assertFalse(links.isEmpty(), "No job found available in both Tallinn and Tartu!");
    }

    @Test
    @DisplayName("Task 4.1: Job links are secured and start with https")
    public void testJobLinksFormat() {
        List<String> jobs = site.getEstoniaJobLinks();
        for (String link : jobs) {
            assertTrue(link.startsWith("https"), "Invalid job link: " + link);
        }
    }

    @Test
    @DisplayName("Task 4.2: Unknown location returns empty list")
    public void testUnknownLocationReturnsEmpty() {
        List<String> jobs = site.getJobsLinksByLocation("notestonia");
        assertTrue(jobs.isEmpty(), "Expected empty list for unknown location!");
    }

    @AfterAll
    public void tearDown() {
        driverManager.shutDown();
    }
}
