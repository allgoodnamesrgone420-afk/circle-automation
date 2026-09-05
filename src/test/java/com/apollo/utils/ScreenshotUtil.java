package com.apollo.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }
        String stamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String folder = System.getProperty("user.dir") + "/test-output/screenshots/";
        new File(folder).mkdirs();
        String path = folder + testName + "_" + stamp + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(src, new File(path));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}