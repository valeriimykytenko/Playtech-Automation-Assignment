package playtechParce.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    WebDriver driver;
    private WebDriverWait wait;

    private By cookieButton = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    private By teamCards = By.className("team-card");
    private By lifeAtPlaytech = By.linkText("Life at Playtech");
    private By whoWeAre = By.linkText("Who we are");
    private By researchButton = By.xpath("//button[contains(., 'Research')]");
    private By researchListItems = By.xpath("//li[contains(., 'areas such as')]/ul/li");
    private By jobsButton = By.className("yellow-button");

    // Constructor to initialize the driver session
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //  Open the main Playtech career page
    public void open() {
        driver.get("https://www.playtechpeople.com");
    }

    // Accept cookie banner to avoid blocking further interactions
    public void acceptCookies() {
        driver.findElement(cookieButton).click();
    }

    //Extract all visible team names
    public List<String> getTeamNames() {
        // Locate and store a list of all team cards
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
        driver.findElement(lifeAtPlaytech).click();
        driver.findElement(whoWeAre).click();

        // Wait for Research accordion button
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(researchButton));

        // Scroll element into view to avoid click interception
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        // Wait until element is clickable and use JavaScript click
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        // Wait until the hidden list becomes visible
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(researchListItems));

        // Extract  research list
        List<String> areas = new ArrayList<>();
        for (WebElement item : items) {
            areas.add(item.getText());
        }
        return areas;
    }

    //Method return links for vacancies from Estonia
    public List<String> getEstoniaJobLinks() {
        driver.findElement(jobsButton).click();

        // Wait until job elements are loaded
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("job-item")));

        // Collect all job cards
        List<WebElement> jobs = driver.findElements(By.className("job-item"));
        List<String> estoniaLinks = new ArrayList<>();

        // Filter jobs by 'data-location' attribute
        for (WebElement job : jobs) {
            if ("estonia".equalsIgnoreCase(job.getAttribute("data-location"))) {
                estoniaLinks.add(job.getAttribute("href"));
            }
        }
        return estoniaLinks;
    }

}