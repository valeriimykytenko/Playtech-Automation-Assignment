package playtechParse.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Manages Webdriver lifecycle and configuration
 */
public class WebDriverManager {
    protected WebDriver driver;

    // Run browser in headless mode for CI/CD compatibility
    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        return driver;
    }

    public void shutDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}