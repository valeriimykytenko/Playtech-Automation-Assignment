package teamParce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    By cookieButton = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    By lifeAtPlaytech = By.linkText("Life at Playtech");
    By whoWeAre = By.linkText("Who we are");
    By jobsButton = By.className("yellow-button");


    // Constructor to initialize the driver session
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate directly to the URL
    public void open() {
        driver.get("https://www.playtechpeople.com");
    }

    // Handle the cookie to clear the UI for further actions
    public void acceptCookies() {
        driver.findElement(cookieButton).click();
    }

    // Handle navigation to reach the 'Who we are' section
    public void goToWhoWeAre() {
        driver.findElement(lifeAtPlaytech).click();
        driver.findElement(whoWeAre).click();
    }

    // Direct navigation to the Careers section
    public void goToJobs() {
        driver.findElement(jobsButton).click();
    }
}
