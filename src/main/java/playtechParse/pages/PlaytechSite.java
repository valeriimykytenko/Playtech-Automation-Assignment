package playtechParse.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

public class PlaytechSite extends BasePage {

    private String baseUrl = "https://www.playtechpeople.com";

    // Constructor to initialize the driver session
    public PlaytechSite(WebDriver driver) {
        super(driver);
    }

    public void openHome() {
        openUrl(baseUrl);
    }

    //Extract all visible team names
    public List<String> getTeamNames() {
        // Locate and store a list of all team cards
        openUrl(baseUrl);
        By teamCards = By.className("team-card");
        List<WebElement> teams = driver.findElements(teamCards);
        List<String> names = new ArrayList<>();

        // Iterate through the team list
        for (WebElement team : teams) {
            names.add(team.getText());
        }
        return names;
    }

    //Navigate to "Who we are" page and extract Research section data
    public List<String> getResearchAreas() {
        String url = baseUrl + "/life-at-playtech/";
        openUrl(url);

        By researchButton = By.cssSelector("#heading-6-4-6 button");
        By selector = By.cssSelector("#collapse-6-4-6 ul ul li");

        scrollToElement(researchButton);

        clickJs(researchButton);

        List<WebElement> items = findAll(selector);
        // Extract  research list

        List<String> areas = new ArrayList<>();
        for (WebElement item : items) {
            String text = item.getAttribute("textContent");
            areas.add(text);
        }
        return areas;
    }

    //Method return links for vacancies from Estonia
    public List<String> getJobsLinksByLocation(String location) {
        String url = baseUrl + "/jobs-our/";
        openUrl(url);
        // Wait until job elements are loaded
        By jobItems = By.className("job-item");

        // Collect all job cards
        List<WebElement> jobs = findAll(jobItems);
        List<String> estoniaLinks = new ArrayList<>();


        // Filter jobs by 'data-location' attribute
        for (WebElement job : jobs) {
            String dataLocation = job.getAttribute("data-location");
            if (location.equalsIgnoreCase(dataLocation)) {
                estoniaLinks.add(job.getAttribute("href"));
            }
        }
        return estoniaLinks;
    }
}
