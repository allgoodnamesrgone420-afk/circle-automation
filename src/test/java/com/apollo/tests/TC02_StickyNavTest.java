package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.HomePage;
import com.apollo.pages.CircleLandingPage;

public class TC02_StickyNavTest extends BaseTest {
    @Test(description = "TC_AUTO_02 Positive - sticky bar navigation")
    public void stickyBarNavigation() {
        new HomePage(getDriver()).goToCircleMembership();
        CircleLandingPage page = new CircleLandingPage(getDriver());
        page.clickBenefits();
        Assert.assertTrue(page.pageHas("Benefit"));
        page.clickPlans();
        Assert.assertTrue(page.pageHas("Plan"));
        page.clickReviews();
        Assert.assertTrue(page.pageHas("Review") || page.pageHas("Member"));
        page.clickFaqs();
        Assert.assertTrue(page.pageHas("FAQ"));
    }
}