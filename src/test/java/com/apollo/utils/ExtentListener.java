package com.apollo.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.apollo.base.BaseTest;
import com.apollo.utils.ScreenshotUtil;

public class ExtentListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setReportName("Apollo Circle Automation");
        spark.config().setDocumentTitle("Circle 6 Tests");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project", "circle-automation");
        extent.setSystemInfo("Tester", "Debasish");
    }

    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Passed");
    }

    public void onTestFailure(ITestResult result) {
        String shot = ScreenshotUtil.takeScreenshot(
                BaseTest.getDriver(), result.getMethod().getMethodName());
        test.get().log(Status.FAIL, result.getThrowable());
        if (shot != null) {
            try {
                test.get().addScreenCaptureFromPath(shot);
            } catch (Exception ignored) {
            }
        }
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}