package tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WishlistTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @Before
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.get("https://woodmart.xtemos.com/shop/");

    }

    @Test
    public void аddProductToWishlist() {
        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-grid-item")));

        actions.moveToElement(firstProduct).perform();
        String productName = firstProduct.findElement(By.cssSelector(".wd-entities-title")).getText();

        WebElement wishlistButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-grid-item:first-child .wd-wishlist-btn")));
        wishlistButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".woocommerce-message, .added-to-wishlist")));
        navigateToWishlist();

        List<WebElement> wishlistItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".wishlist-content-wrapper .product-title")));

        boolean productFound = false;
        for (WebElement item : wishlistItems) {
            if (item.getText().equals(productName)) {
                productFound = true;
                break;
            }
        }

        assertTrue("Product should be found in the wishlist", productFound);
    }

    @Test
    public void removeProductFromWishlist() {
        WebElement secondProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-grid-item:nth-child(2)")));

        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".remove_from_wishlist")));
        removeButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".wishlist-content-wrapper")));

        try {
            WebElement emptyWishlist = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".wishlist-empty")));
            assertTrue("Wishlist should be empty after removing the product", emptyWishlist.isDisplayed());
        } catch (Exception e) {
            List<WebElement> remainingItems = driver.findElements(By.cssSelector(".wishlist_table .product-name"));
            assertEquals("No products should remain in the wishlist", 0, remainingItems.size());
        }
    }

    @Test
    public void multipleProductsToWishlist() {

        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-grid-item:nth-child(1)")));
        actions.moveToElement(firstProduct).perform();

        WebElement firstWishlistButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-grid-item:nth-child(1) .wd-wishlist-btn")));
        firstWishlistButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".woocommerce-message, .added-to-wishlist")));

        WebElement secondProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-grid-item:nth-child(2)")));
        actions.moveToElement(secondProduct).perform();

        WebElement secondWishlistButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-grid-item:nth-child(2) .wd-wishlist-btn")));
        secondWishlistButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".woocommerce-message, .added-to-wishlist")));

        navigateToWishlist();

        List<WebElement> wishlistItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector(".wishlist-content-wrapper .product-title")));
        assertTrue("Wishlist should contain at least 2 products", wishlistItems.size() >= 2);
    }


    private void navigateToWishlist() {
        try {
            WebElement wishlistIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".wd-header-wishlist, .wd-tools-element a[href*='wishlist']")));
            wishlistIcon.click();
        } catch (Exception e) {
            driver.get("https://woodmart.xtemos.com/wishlist/");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".wishlist-content-wrapper, .wishlist-empty")));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}