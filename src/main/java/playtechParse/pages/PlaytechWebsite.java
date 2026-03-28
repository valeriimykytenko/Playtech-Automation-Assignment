package playtechParse.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object representing playtechpeople.com
 * Contains methods fot extracting UI data used in tests
 */
public class PlaytechWebsite extends BasePage {

    private static final String DEFAULT_URL = "https://www.playtechpeople.com";

    public static final String LOCATION_ESTONIA = "estonia";

    private static String resolveBaseUrl() {
        String prop = System.getProperty("base.url");
        if (prop != null) return prop;
        return DEFAULT_URL;
    }

    private final String baseUrl = resolveBaseUrl();

    public PlaytechWebsite(WebDriver driver) {
        super(driver);
    }

    // Opens homepage and returns title
    public String getHomeTitle() {
        openUrl(baseUrl);
        return driver.getTitle();
    }

    // Extract all team names displayed on homepage
    public List<String> getTeamNames() {
        openUrl(baseUrl);
        By teamCards = By.className("team-card");
        List<WebElement> teams = driver.findElements(teamCards);
        List<String> names = new ArrayList<>();
        for (WebElement team : teams) {
            names.add(team.getText());
        }
        return names;
    }

    // Navigates to "Life at Playtech" page and extracts research areas
    public List<String> getResearchAreas() {
        String url = baseUrl + "/life-at-playtech/";
        openUrl(url);

        By researchButton = By.cssSelector("#heading-6-4-6 button");
        By selector = By.cssSelector("#collapse-6-4-6 ul ul li");
        scrollToElement(researchButton);
        clickJs(researchButton);
        List<WebElement> items = findAll(selector);

        List<String> areas = new ArrayList<>();
        for (WebElement item : items) {
            String text = item.getAttribute("textContent");
            areas.add(text);
        }
        return areas;
    }

    // Returns jobs links filtered by location
    public List<String> getJobsLinksByLocation(String location) {
        String url = baseUrl + "/jobs-our/";
        openUrl(url);
        By jobItems = By.className("job-item");
        List<WebElement> jobs = findAll(jobItems);
        List<String> locationLinks = new ArrayList<>();

        for (WebElement job : jobs) {
            String dataLocation = job.getAttribute("data-location");
            if (location.equalsIgnoreCase(dataLocation)) {
                locationLinks.add(job.getAttribute("href"));
            }
        }
        return locationLinks;
    }

    public List<String> getEstoniaJobLinks() {
        return getJobsLinksByLocation(LOCATION_ESTONIA);
    }

    // Opens Estonia jobs links and checks page content for both Tallinn and Tartu
    public String getJobLinkForTallinnAndTartu() {
        List<String> links = getJobsLinksByLocation(LOCATION_ESTONIA);

        for (String link : links) {
            openUrl(link);
            String pageSource = getPageText();
            boolean isTallinn = pageSource.contains("Tallinn");
            boolean isTartu = pageSource.contains("Tartu");
            if (isTallinn && isTartu) {
                return link;
            }
        }
        return "";
    }
}
