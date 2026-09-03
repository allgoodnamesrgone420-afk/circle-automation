package com.apollo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.JavascriptExecutor;

public class HomePage {
    private WebDriver driver;

    // locator type: partialLinkText
    @FindBy(partialLinkText = "Circle Membership")
    private WebElement circleMembershipLink;

    // locator type: cssSelector (backup)
    @FindBy(css = "a[href*='circle']")
    private WebElement circleCssLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToCircleMembership() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", circleMembershipLink);
            Thread.sleep(800);
            js.executeScript("arguments[0].click();", circleMembershipLink);
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", circleCssLink);
        }
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }
}