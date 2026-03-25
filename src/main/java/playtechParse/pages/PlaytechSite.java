package playtechParse.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import playtechParse.services.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class PlaytechSite extends BasePage{
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());
    private String baseUrl = "https://www.playtechpeople.com";

    // Constructor to initialize the driver session
    public PlaytechSite(WebDriver driver) {
        super(driver);
    }

    public void openHome(){
        driver.get(baseUrl);
        logger.info("Task 1: Home page opened");
    }

    //Extract all visible team names
    public List<String> getTeamNames() {
        // Locate and store a list of all team cards
        driver.get(baseUrl);
        By teamCards = By.className("team-card");
        List<WebElement> teams = driver.findElements(teamCards);
        List<String> names = new ArrayList<>();

        // Iterate through the list to find vacancies in Estonia
        for (WebElement team : teams) {
            names.add(team.getText());
        }
        return names;
    }

    //Navigate to "Who we are" page and extract Research section data
    public List<String> getResearchAreas() {
        String url = baseUrl + "/life-at-playtech/";
        driver.get(url);

        By selector = By.cssSelector("#collapse-6-4-6 ul ul li");
        By researchButton = By.cssSelector("#heading-6-4-6 button");

        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(researchButton));
        scrollToElement(btn);
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        clickJs(researchButton);

        List<WebElement> items = findAll(selector);
        // Extract  research list
        List<String> areas = new ArrayList<>();
        for (WebElement item : items) {
            areas.add(item.getText());
        }
        return areas;
    }

    //Method return links for vacancies from Estonia
    public List<String> getEstoniaJobLinks() {
        String url = baseUrl + "/jobs-our/";
        driver.get(url);
        // Wait until job elements are loaded
        By jobItems = By.className("job-item");

        // Collect all job cards
        List<WebElement> jobs = findAll(jobItems);
        List<String> estoniaLinks = new ArrayList<>();

        String searchedLocation = "estonia";

        // Filter jobs by 'data-location' attribute
        for (WebElement job : jobs) {
            String dataLocation = job.getAttribute("data-location");
            if (searchedLocation.equalsIgnoreCase(dataLocation)) {
                estoniaLinks.add(job.getAttribute("href"));
            }
        }
        return estoniaLinks;
    }

}