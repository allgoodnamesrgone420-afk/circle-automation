package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.*;

public class TC04_JoinCircleTest extends BaseTest {
    @Test(description = "TC_AUTO_04 Positive - Join Circle next step, no payment")
    public void joinCircleNoPayment() {
        new HomePage(driver).goToCircleMembership();
        CircleLandingPage page = new CircleLandingPage(driver);
        Assert.assertTrue(page.isJoinVisible());
        page.clickJoinCircle();
        String url = driver.getCurrentUrl().toLowerCase();
        Assert.assertFalse(url.contains("success") || url.contains("thank"));
        Assert.assertTrue(new LoginOverlayPage(driver).isOpen()
                || page.pageHas("Plan") || page.pageHas("Login"));
    }
}