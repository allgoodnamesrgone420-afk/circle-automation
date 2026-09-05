package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.HomePage;
import com.apollo.pages.CircleLandingPage;

public class TC01_HomeToCircleTest extends BaseTest {
    @Test(description = "TC_AUTO_01 Positive - Home to Circle Membership")
    public void navigateHomeToCircle() {
        new HomePage(getDriver()).goToCircleMembership();
        Assert.assertTrue(new CircleLandingPage(getDriver()).isCirclePageLoaded(),
            "Did not reach Circle Membership page from Home");
    }
}