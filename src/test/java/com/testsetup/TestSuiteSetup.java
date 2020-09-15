package com.testsetup;

import com.testutils.BrowserFactory;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class TestSuiteSetup {
    public WebDriver driver;
//    public HomePageElement homePageElement;
    //static String url = "http://www.londonfreelance.org/courses/frames/index.html";
//    static String url;
    protected Logger logger;

    @Parameters({"browser", "url"})
    @BeforeSuite(alwaysRun = true)
    public void setUpClass(@Optional("chrome") String browser, @Optional("url") String url, ITestContext ctx){

        String testName = ctx.getCurrentXmlTest().getName();
        logger = Logger.getLogger(testName);
        BrowserFactory browserFactory = new BrowserFactory(browser, logger);
        driver=browserFactory.createDriver();
        driver.get(url);
//        homePageElement = new HomePageElement(driver);
        logger.info("Test setup done");
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUp(){
        driver.quit();
        driver = null;
        logger.info("Test clean-up done");
    }
    /*@AfterMethod
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
    }*/



}
