package com.testsetup;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.IOException;

public class TestSetUp extends TestSuiteSetup{
    /*@AfterTest
    public void testTearDown(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE){
            try {
                File screenCapture = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenCapture, new File("target/surefire-reports/screens/" +result.getName()+ ".jpg"), true);
                Reporter.log("Screenshot saved at : <a href=\"screens/" +result.getName()+ ".jpg\">Failure screen</a>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
