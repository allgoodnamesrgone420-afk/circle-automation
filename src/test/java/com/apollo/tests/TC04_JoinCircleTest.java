package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.*;

public class TC04_JoinCircleTest extends BaseTest {
    @Test(description = "TC_AUTO_04 Positive - Join Circle next step, no payment")
    public void joinCircleNoPayment() {
        new HomePage(getDriver()).goToCircleMembership();
        CircleLandingPage page = new CircleLandingPage(getDriver());
        Assert.assertTrue(page.isJoinVisible());
        page.clickJoinCircle();
        String url = getDriver().getCurrentUrl().toLowerCase();
        Assert.assertFalse(url.contains("success") || url.contains("thank"));
        Assert.assertTrue(new LoginOverlayPage(getDriver()).isOpen()
                || page.pageHas("Plan") || page.pageHas("Login"));
    }
}