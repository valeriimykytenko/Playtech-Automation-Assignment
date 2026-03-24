package teamParce.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import teamParce.pages.HomePage;

import java.time.Duration;
import java.util.List;

public class PlaytechTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        // Initializing the Chrome driver session and set up an explicit wait for element synchronization
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testPlaytechWebsite(){
        // Navigate to the base URL
        HomePage home = new HomePage(driver);

        // OPEN SITE
        home.open();
        home.acceptCookies();

        // TASK 2: Extracting all available team names
        // Using findElements to get a list of all cards present in the DOM
        List<WebElement> teams = driver.findElements(By.className("team-card"));
        System.out.println("Total Teams: " + teams.size());
        for (WebElement team : teams) {
            System.out.println("Team Name: " + team.getText());
        }

        // TASK 3: Navigate to the 'Who we are' page and extract Research details
        home.goToWhoWeAre();

        // Locating the specific 'Research' accordion toggle
        WebElement researchLocator = driver.findElement(By.xpath("//button[contains(., 'Research')]"));

        // Scroll the button into view to ensure it's visible on the screen
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", researchLocator);

        // Wait for the button to be ready for interaction and click
        wait.until(ExpectedConditions.elementToBeClickable(researchLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", researchLocator);

        By  listsLocator = By.xpath("//li[contains(., 'areas such as')]/ul/li");

        // Wait until the hidden list becomes visible to the user
        List<WebElement> researchAreas = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listsLocator));

        for (WebElement area : researchAreas) {
            System.out.println("- " + area.getText());
        }

        // TASK 4: Job link extraction

        // Navigate to the job search section
        home.goToJobs();

        // Get all available job postings from the DOM
        List<WebElement> jobs = driver.findElements(By.className("job-item"));

        // Iterate through the list to find vacancies in Estonia
        for (WebElement job : jobs) {
            String location = job.getAttribute("data-location");

            if ("estonia".equalsIgnoreCase(location)) {
                String link = job.getAttribute("href");
                System.out.println("Estonia job: " + link);
            }
        }

    }


    @AfterEach
    public void tearDown() {
        // Clean up: closing the browser session
        if (driver != null) {
            driver.quit();
        }
    }
}
