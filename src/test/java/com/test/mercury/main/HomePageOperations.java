package com.test.mercury.main;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobject.pages.HomePageElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomePageOperations {
    static WebDriver driver;
    static HomePageElement homePageElement;
    //static String url = "http://www.londonfreelance.org/courses/frames/index.html";
    static String url = "http://demo.guru99.com/selenium/newtours/index.php";
    @BeforeClass(alwaysRun = true)
    static void setUpClass(){
        System.setProperty("webdriver.chrome.driver","E:\\Softwares\\selenium\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.get(url);
        homePageElement = new HomePageElement(driver);
        Reporter.clear();
    }

    @Test
    public void clickOnSignonLink(){
        driver.findElement(By.linkText("SIGN-ON")).click();
    }

    @Test
    public void verifyMenuLinks() {
        SoftAssert softAssert = new SoftAssert();
        String[] links = {"Home 1", "Flights", "Hotels", "Car Rentals", "Cruises", "Destinations", "Vacations"};
        List<String> linkList = Arrays.asList(links);
        List<WebElement> tableLinks = homePageElement.getMenuTable().findElements(By.tagName("a"));
        Reporter.log("LinkTexts");
        for (WebElement tl : tableLinks) {
            Reporter.log(tl.getText());
            if (!linkList.contains(tl.getText())) {
                softAssert.assertTrue(linkList.contains(tl.getText()), "Given link "+tl.getText()+" Link is not valid");
            }
        }
    }

    @Test (groups = {"sample1"})
    public void menuTableLinks(){
        SoftAssert softAssert = new SoftAssert();
        int rows = homePageElement.getMenuTable().findElements(By.tagName("tr")).size();
        softAssert.assertEquals(rows, 7, "expected 8 but actual "+rows);
        softAssert.assertAll();
    }

    @Test
    public void headerLinksTable(){
        SoftAssert softAssert = new SoftAssert();
        int rows = homePageElement.getHeaderLinkTable().findElements(By.tagName("tr")).size();
        softAssert.assertEquals(1, rows);
        int cols = homePageElement.getHeaderLinkTable().findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
        Assert.assertEquals(4, cols);
        softAssert.assertAll();
    }

    /*@Test (groups = {"frame-test"})
    public void frameOperations(){
        List <WebElement> frames = driver.findElements(By.tagName("frame"));
        System.out.println("number of frames: "+frames.size());
        System.out.println("Printing links of all frames");
        for (WebElement frme : frames) {
            driver.switchTo().frame(frme.getAttribute("name"));
            List<WebElement> links = driver.findElements(By.tagName("a"));
            for (WebElement link : links) {
                Reporter.log(link.getText());
                System.out.println(link.getText());
            }
            driver.switchTo().defaultContent();
        }
    }*/

    @Test
    public void takeScreenShotOfHomePage(){
        homePageElement.getRegisterLink().click();
        File screenCapture = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture, new File("E:/LinkedLerrning/HomePage.jpg"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    public static void cleanUp(){
        driver.close();
        driver = null;
        homePageElement = null;
    }

}
