package com.test.mercury.main;

import com.testsetup.TestSetUp;
import com.testutils.TestBasicUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomePageOperations extends TestSetUp {

    @Test
    public void test01clickOnSignonLink(){
        TestBasicUtils.step(1, "Clicking on SIGN-ON link");
        driver.findElement(By.linkText("SIGN-ON")).click();
    }

    @Test
    public void test02VerifyMenuLinks() {
        SoftAssert softAssert = new SoftAssert();
        String[] links = {"Home 1", "Flights", "Hotels", "Car Rentals", "Cruises", "Destinations", "Vacations"};
        List<String> linkList = Arrays.asList(links);
        List<WebElement> tableLinks = homePageElement.getMenuTable().findElements(By.tagName("a"));
        TestBasicUtils.step(1, "Print and verify links under Home Page Menu");
        Reporter.log("LinMessageskTexts");
        for (WebElement tl : tableLinks) {
            Reporter.log(tl.getText());
            if (!linkList.contains(tl.getText())) {
                softAssert.assertTrue(linkList.contains(tl.getText()), "Given link "+tl.getText()+" Link is not valid");
            }
        }
    }

    @Test (groups = {"sample1"})
    public void test03MenuTableLinks(){
        SoftAssert softAssert = new SoftAssert();
        TestBasicUtils.step(1, "Verify number of links in Menu Table of Home Page");
        int rows = homePageElement.getMenuTable().findElements(By.tagName("tr")).size();
        softAssert.assertEquals(rows, 7, " Menu table rows expected 7 but actual "+rows);
        softAssert.assertAll();
    }

    @Test
    public void test04HeaderLinksTable(){
        SoftAssert softAssert = new SoftAssert();
        TestBasicUtils.step(1, "Verify number of rows and columns in Link header table");
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
    public void test05TakeScreenShotOfHomePage(){
        homePageElement.getRegisterLink().click();
        File screenCapture = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture, new File("target/surefire-reports/screens/testTakeScreenShotOfHomePage.jpg"), true);
            Reporter.log("Screenshot saved at : <a href=\"screens/testTakeScreenShotOfHomePage.jpg\">Failure screen</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
