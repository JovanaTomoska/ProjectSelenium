package tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePageTest {
    static WebDriver driver;

    @BeforeClass
    public static void beforeClass() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void verifyGooglePage(){
        driver.get("https://www.google.sk/?hl=sk ");
        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Google", actualTitle);
    }
    @Test
    public void verifyWoodmartPage(){
        driver.navigate().to("https://woodmart.xtemos.com/home/");
        String expectedTitle = "WoodMart - Multipurpose WooCommerce Theme WordPress";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void verifyUrl() {
        driver.navigate().back();
        driver.navigate().forward();
        Assert.assertEquals("https://woodmart.xtemos.com/home/", driver.getCurrentUrl());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        driver.quit();
    }
}
