package tests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class RegisterTest {
    static WebDriver driver;
    static WebDriverWait wait;
    private static final String REGISTER_URL = "https://woodmart.xtemos.com/home/my-account/";

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/Jovana/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(REGISTER_URL);

        WebElement registerTab = driver.findElement(By.xpath("//a[contains(text(), 'Register')]"));
        registerTab.click();
    }

    @Test
    public void successfulRegistration() {
        WebElement loginLink = driver.findElement(By.xpath("//div[contains(@class, 'wd-header-my-account')]//a"));
        loginLink.click();
        driver.findElement(By.xpath("//a[@href='https://woodmart.xtemos.com/my-account/?action=register']")).click();

        String username = "testUser" + UUID.randomUUID().toString().substring(0, 8);
        String email = username + "@gmail.com";
        String password = "user123**";

        WebElement usernameField = driver.findElement(By.id("reg_username"));
        WebElement emailField = driver.findElement(By.id("reg_email"));
        WebElement passwordField = driver.findElement(By.id("reg_password"));
        WebElement registerButton = driver.findElement(By.name("register"));
        usernameField.sendKeys(username);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        registerButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'woocommerce-message')]")));
    }

    @Test
    public void registrationWithExistingEmail() {
        driver.get(REGISTER_URL);
        WebElement registerTab = driver.findElement(By.xpath("//a[contains(text(), 'Register')]"));
        registerTab.click();

        String existingEmail = "existing@gmail.com";
        String username = "Userr" + UUID.randomUUID().toString().substring(0, 8);
        String password = "user123**";

        WebElement usernameField = driver.findElement(By.id("reg_username"));
        WebElement emailField = driver.findElement(By.id("reg_email"));
        WebElement passwordField = driver.findElement(By.id("reg_password"));
        WebElement registerButton = driver.findElement(By.name("register"));
        usernameField.sendKeys(username);
        emailField.sendKeys(existingEmail);
        passwordField.sendKeys(password);
        registerButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("woocommerce-error")));
        assertTrue("Error message should indicate email already exists", errorMessage.getText().contains("already registered"));
    }

    @Test
    public void registrationWithInvalidEmail() {
        driver.get(REGISTER_URL);
        WebElement registerTab = driver.findElement(By.xpath("//a[contains(text(), 'Register')]"));
        registerTab.click();

        String username = "testUser" + UUID.randomUUID().toString().substring(0, 8);
        String invalidEmail = "invalid-email";
        String password = "StrongPassword123!";

        WebElement usernameField = driver.findElement(By.id("reg_username"));
        WebElement emailField = driver.findElement(By.id("reg_email"));
        WebElement passwordField = driver.findElement(By.id("reg_password"));
        WebElement registerButton = driver.findElement(By.name("register"));
        usernameField.sendKeys(username);
        emailField.sendKeys(invalidEmail);
        passwordField.sendKeys(password);
        registerButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("woocommerce-error")));
        assertTrue("Error message should be displayed for invalid email", errorMessage.isDisplayed());
    }

    @Test
    public void registrationWithEmptyFields() {
        driver.get(REGISTER_URL);
        WebElement registerTab = driver.findElement(By.xpath("//a[contains(text(), 'Register')]"));
        registerTab.click();

        WebElement registerButton = driver.findElement(By.name("register"));
        registerButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("woocommerce-error")));
        assertTrue("Error message should be displayed for empty required fields", errorMessage.isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}