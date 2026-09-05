package com.apollo.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    private static ThreadLocal<WebDriver> driverBox = new ThreadLocal<>();
    protected Properties config = new Properties();

    public static WebDriver getDriver() {
        return driverBox.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) throws Exception {
        System.out.println("Starting browser = " + browser);
        config.load(new FileInputStream("src/test/resources/config.properties"));

        WebDriver driver;
        String name = browser.toLowerCase().trim();

        if (name.equals("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driver = new EdgeDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
        }

        driverBox.set(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(config.getProperty("url.home"));
        pause(1500);
    }

    @AfterMethod
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverBox.remove();
        }
    }

    protected void scrollAndClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
                element);
        pause(800);
        js.executeScript("arguments[0].click();", element);
        pause(800);
    }

    protected void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}