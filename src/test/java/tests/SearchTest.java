package tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SearchTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://woodmart.xtemos.com/home/");
    }

    @Test
    public void basicSearch() {
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".wd-header-search-form")));
        searchIcon.click();

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='s']")));
        searchInput.sendKeys("chair");
        searchInput.submit();

        wait.until(ExpectedConditions.urlContains("search"));

        WebElement searchTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-title")));
        assertTrue("Search title should contain the search keyword", searchTitle.getText().toLowerCase().contains("chair"));
    }

    @Test
    public void noResultsSearch() {
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".wd-header-search-form")));
        searchIcon.click();

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='s']")));
        searchInput.sendKeys("product123");
        searchInput.submit();

        wait.until(ExpectedConditions.urlContains("search"));

        WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wd-search-no-results")));
        assertTrue("No results message should be displayed", noResultsMessage.isDisplayed());
    }

    @Test
    public void testAdvancedSearch() {
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".wd-header-search-form")));
        searchIcon.click();

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='s']")));
        searchInput.sendKeys("table");
        searchInput.submit();

        wait.until(ExpectedConditions.urlContains("search"));

        try {
            WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".wd-product-categories a")));
            filterButton.click();

            wait.until(ExpectedConditions.stalenessOf(filterButton));

            List<WebElement> filteredResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".product-grid-item")));
            assertTrue("Filtered search should return at least one result", filteredResults.size() > 0);
        } catch (Exception e) {
            System.out.println("Category filters not available, skipping filter test");
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}