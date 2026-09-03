package com.apollo.tests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.apollo.base.BaseTest;
import com.apollo.pages.*;
import com.apollo.utils.ExcelReader;

public class TC06_InvalidLoginExcelTest extends BaseTest {
    @DataProvider(name = "invalidMobile")
    public Object[][] invalidMobile() {
        return ExcelReader.getData(
            System.getProperty("user.dir") + "/src/test/resources/testdata/Circle_TestData.xlsx",
            "LoginData", "Negative");
    }

    @Test(dataProvider = "invalidMobile", description = "TC_AUTO_06 Negative - empty/invalid mobile from Excel")
    public void invalidLoginFromExcel(String id, String type, String mobile, String expected) {
        new HomePage(driver).goToCircleMembership();
        CircleLandingPage page = new CircleLandingPage(driver);
        page.clickLogin();
        pause(2000);
        LoginOverlayPage login = new LoginOverlayPage(driver);
        Assert.assertTrue(login.isOpen());
        login.enterMobile(mobile); // empty from Excel
        login.submit();
        pause(1500);
        Assert.assertTrue(login.stillOnLogin(), expected);
    }
}