package com.apollo.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.apollo.base.BaseTest;

public class ExtentListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // onStart runs once per <test> in the suite file. Creating the report
    // here every time replaced it, so the HTML only kept the last <test>
    // (and parallel runs raced on the shared field). Create it once.
    private static synchronized ExtentReports report() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setReportName("Apollo Circle Automation");
            spark.config().setDocumentTitle("Circle 6 Tests");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project", "circle-automation");
            extent.setSystemInfo("Tester", "Debasish");
        }
        return extent;
    }

    public void onStart(ITestContext context) {
        report();
    }

    public void onTestStart(ITestResult result) {
        test.set(report().createTest(result.getMethod().getMethodName()));
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

    public void onTestSkipped(ITestResult result) {
        // A test skipped because setUp failed never reached onTestStart.
        if (test.get() == null) {
            test.set(report().createTest(result.getMethod().getMethodName()));
        }
        test.get().log(Status.SKIP, result.getThrowable() != null ? result.getThrowable().toString() : "Skipped");
    }

    public void onFinish(ITestContext context) {
        report().flush();
    }
}