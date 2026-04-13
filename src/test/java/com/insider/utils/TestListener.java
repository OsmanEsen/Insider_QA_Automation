package com.insider.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();
        if (driver != null) {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + result.getName() + "_" + dateName + ".png";
            File finalDestination = new File(destination);
            try {
                FileUtils.copyFile(source, finalDestination);
                System.out.println("Test patladı! Ekran görüntüsü alındı: " + destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}