import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

// Page Object Class
class BingHomePage {
    WebDriver driver;

    // Locators
    private By searchBox = By.name("q");
    private By firstResultLink = By.xpath("//*[@id=\"b_results\"]/li[1]/h2/a");

    // Constructor
    public BingHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void enterSearchTerm(String searchTerm) {
        driver.findElement(searchBox).sendKeys(searchTerm);
    }

    public void submitSearch() {
        driver.findElement(searchBox).submit();
    }

    public String getFirstResultText() {
        return driver.findElement(firstResultLink).getText();
    }
}

// Main Test Class
public class UITest {
    public WebDriver driver;
    public String testURL = "https://www.bing.com/";

    @BeforeMethod
    public void setupTest() {
        // Using WebDriverManager to handle driver binaries
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(testURL);
    }

    @Test
    public void bingSearchTest() {
        BingHomePage bingHomePage = new BingHomePage(driver);

        // Use Explicit Wait
        WebDriverWait wait = new WebDriverWait(driver, 5);

        bingHomePage.enterSearchTerm("youtube");
        bingHomePage.submitSearch();

        // Wait for the first result to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"b_results\"]/li[1]/h2/a")));

        String firstResultText = bingHomePage.getFirstResultText();
        Assert.assertEquals(firstResultText, "YouTube");
        System.out.println("First Result: " + firstResultText);
    }

    @AfterMethod
    public void teardownTest() {
        driver.quit();
    }
}
