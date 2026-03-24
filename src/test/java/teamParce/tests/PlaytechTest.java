package teamParce.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class PlaytechTest {
    WebDriver driver;

    @BeforeEach
    public void setup() {
        // Initializing the Chrome driver session
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testPlaytechWebsite(){
        // Navigate to the base URL
        driver.get("https://www.playtechpeople.com");

        //Handle Cookies
        driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

        // TASK 2: Count and list all available teams from the main page cards
        List<WebElement> teams = driver.findElements(By.className("team-card"));
        System.out.println("Total Teams: " + teams.size());
        for (WebElement team : teams) {
            System.out.println("Team Name: " + team.getText());
        }
    }


    @AfterEach
    public void tearDown() {
        // Ensuring the browser closes after tests
        if (driver != null) {
            driver.quit();
        }
    }
}
