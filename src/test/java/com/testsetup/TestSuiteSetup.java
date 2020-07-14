package com.testsetup;

import com.pageobject.pages.HomePageElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class TestSuiteSetup {
    public static WebDriver driver;
    public static HomePageElement homePageElement;
    //static String url = "http://www.londonfreelance.org/courses/frames/index.html";
    static String url = "http://demo.guru99.com/selenium/newtours/index.php";
    @BeforeSuite(alwaysRun = true)
    public void setUpClass(){
        System.setProperty("webdriver.chrome.driver","E:\\Softwares\\selenium\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.get(url);
        homePageElement = new HomePageElement(driver);
        Reporter.clear();
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUp(){
//        Reporter.clear();
        driver.close();
        driver = null;
        homePageElement = null;
    }
    @AfterMethod
    public void testTearDown(ITestResult result){
        if (result.getStatus() == ITestResult.SUCCESS){
            try {
                File screenCapture = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenCapture, new File("target/surefire-reports/screens/" +result.getName()+ ".jpg"), true);
                Reporter.log("Screenshot saved at : <a href=\"screens/" +result.getName()+ ".jpg\">Failure screen</a>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
