package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.*;
import com.apollo.utils.ExcelReader;

public class TC05_ValidLoginExcelTest extends BaseTest {
    @DataProvider(name = "validMobile")
    public Object[][] validMobile() {
        return ExcelReader.getData(
            System.getProperty("user.dir") + "/src/test/resources/testdata/Circle_TestData.xlsx",
            "LoginData", "Positive");
    }

    @Test(dataProvider = "validMobile", description = "TC_AUTO_05 Positive - valid mobile from Excel")
    public void validLoginFromExcel(String id, String type, String mobile, String expected) {
    	new HomePage(driver).goToCircleMembership();
    	pause(2000);

    	CircleLandingPage page = new CircleLandingPage(driver);
    	page.clickLogin();
    	pause(3000);   // you should SEE the login popup now

    	LoginOverlayPage login = new LoginOverlayPage(driver);
    	Assert.assertTrue(login.isOpen(),
    	        id + " overlay not open. URL=" + driver.getCurrentUrl());

    	login.enterMobile(mobile);
    	pause(1500);   // you should SEE the number typed
    }
}