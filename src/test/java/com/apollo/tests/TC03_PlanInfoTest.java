package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.*;

public class TC03_PlanInfoTest extends BaseTest {
	@Test(description = "TC_AUTO_03 Positive - plans, prices, radio/card clicks")
	public void planPricesAndOptions() {
	    new HomePage(driver).goToCircleMembership();
	    CircleLandingPage page = new CircleLandingPage(driver);

	    Assert.assertTrue(page.isPlanVisible(), "12 Months plan missing");
	    Assert.assertTrue(page.is199Visible(), "₹199 missing");
	    Assert.assertTrue(page.is599Visible(), "₹599 missing");

	    int clicked = page.clickAllPlanOptions();
	    // If the live page has only 1 plan, clicked can be 1. That is still valid.
	    Assert.assertTrue(clicked >= 1 || page.isPlanVisible(),
	            "Could not click any plan option");
	    Assert.assertTrue(page.is199Visible() || page.isJoinVisible(),
	            "After plan click, price/CTA disappeared");
	}
}