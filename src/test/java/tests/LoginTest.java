package tests;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class LoginTest {
    static WebDriver driver;

    @BeforeClass
    public static void beforeClass() throws Exception {
        driver = new ChromeDriver();
        driver.navigate().to("https://woodmart.xtemos.com/home/");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    @Test
    public void testValidLogin() {

        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginSubmitButton = driver.findElement(By.name("login"));

        usernameField.sendKeys("tomoskaj@gmail.com");
        passwordField.sendKeys("jovana123*");
        loginSubmitButton.click();

    }


    @Test
    public void invalidPassword() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginSubmitButton = driver.findElement(By.name("login"));
        usernameField.sendKeys("tomoskaj@gmail.com");
        passwordField.sendKeys("123jovana45*");
        loginSubmitButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='woocommerce-error']//li"));
        assertTrue(errorMessage.getText().contains("Invalid password"));
    }

    @Test
    public void testInvalidUsername() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginSubmitButton = driver.findElement(By.name("login"));
        usernameField.sendKeys("user578@gmail.com");
        passwordField.sendKeys("jovana123*");
        loginSubmitButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='woocommerce-error']//li"));
        assertTrue(errorMessage.getText().contains("Invalid username"));
    }

    @Test
    public void emptyFields() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement loginSubmitButton = driver.findElement(By.name("login"));
        loginSubmitButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='woocommerce-error']//li"));
        assertTrue(errorMessage.getText().contains("error"));
    }
    @Test
    public void loginRedirect() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginSubmitButton = driver.findElement(By.name("login"));
        usernameField.sendKeys("tomoskaj@gmail.com");
        passwordField.sendKeys("jovana123*");
        loginSubmitButton.click();

        assertEquals("https://woodmart.xtemos.com/my-account/", driver.getCurrentUrl());
    }
    @Test
    public void logout() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginSubmitButton = driver.findElement(By.name("login"));
        usernameField.sendKeys("tomoskaj@gmail.com");
        passwordField.sendKeys("jovana123*");
        loginSubmitButton.click();

        WebElement logoutButton = driver.findElement(By.xpath("//a[contains(text(), 'Logout')]"));
        logoutButton.click();

        WebElement loginAgain = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        assertTrue(loginAgain.isDisplayed());
    }


    @AfterClass
    public static void afterClass() throws Exception {
        driver.quit();
    }


}
