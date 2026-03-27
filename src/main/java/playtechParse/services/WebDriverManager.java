package playtechParse.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverManager {
    protected WebDriver driver;

    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    public void shutDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}