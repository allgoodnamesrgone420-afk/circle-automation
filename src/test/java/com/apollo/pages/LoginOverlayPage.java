package com.apollo.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginOverlayPage {
    private WebDriver driver;
    private WebDriverWait wait;
    // locator type: name
    private final By mobileBox = By.name("mobileNumber");

    public LoginOverlayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isOpen() {
        By[] candidates = {
            By.name("mobileNumber"),
            By.cssSelector("input[title*='mobile' i]"),
            By.cssSelector("input[title*='Mobile']"),
            By.xpath("//input[contains(@title,'mobile') or contains(@placeholder,'mobile') or contains(@placeholder,'Mobile')]")
        };
        long end = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < end) {
            for (By by : candidates) {
                try {
                    java.util.List<WebElement> list = driver.findElements(by);
                    for (WebElement e : list) {
                        if (e.isDisplayed()) {
                            return true;
                        }
                    }
                } catch (Exception ignored) {}
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        return false;
    }

    public void enterMobile(String mobile) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileBox));
        box.click();
        box.sendKeys(Keys.CONTROL + "a");
        box.sendKeys(Keys.DELETE);
        if (mobile != null && !mobile.isEmpty()) box.sendKeys(mobile);
    }

    public void submit() {
        try {
            driver.findElement(By.xpath(
                "//button[contains(.,'Get OTP') or contains(.,'Send OTP') or contains(.,'Continue')]")).click();
        } catch (Exception e) {
            driver.findElement(mobileBox).sendKeys(Keys.ENTER);
        }
    }

    public boolean stillOnLogin() {
        try { return driver.findElement(mobileBox).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}