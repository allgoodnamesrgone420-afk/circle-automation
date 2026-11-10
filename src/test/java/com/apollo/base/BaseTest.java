package com.apollo.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
        // Closed after loading (it used to leak one file handle per test).
        try (InputStream in = new FileInputStream("src/test/resources/config.properties")) {
            config.load(in);
        }

        WebDriver driver;
        String name = browser.toLowerCase().trim();
        // Run without a window on CI: mvn test -Dheadless=true
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (name.equals("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("--headless=new", "--window-size=1920,1080");
            }
            driver = new EdgeDriver(options);
        } else if (name.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else if (name.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("--headless=new", "--window-size=1920,1080");
            }
            driver = new ChromeDriver(options);
        } else {
            // Any other name used to fall through to Chrome, so a "firefox"
            // run passed while never opening Firefox.
            throw new IllegalArgumentException("Unsupported browser: " + browser + " (use chrome, edge or firefox)");
        }

        driverBox.set(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(config.getProperty("url.home"));
        pause(1500);
    }

    // alwaysRun: the browser is closed even when setUp or the test failed.
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverBox.remove();
            }
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
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}