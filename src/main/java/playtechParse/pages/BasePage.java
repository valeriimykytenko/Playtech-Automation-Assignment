package playtechParse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all page objects
 * Provides Selenium utilities like waits, scrolling and JS actions
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void openUrl(String url){
        driver.get(url);
    }

    // Scroll element into viewpoint to avoid hidden element interaction issue
    protected void scrollIntoViewport(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // Use JavaScript click when Selenium click fails
    protected void executeClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    // Wait until elements are present in DOM and return items
    protected List<WebElement> findAll(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected String getAttribute(By locator, String attribute) {
        return wait.until(ExpectedConditions
                .presenceOfElementLocated(locator)).getAttribute(attribute);
    }

}
