package com.test.mercury.main;

import com.pageobject.pages.HomePageElement;
import com.testutils.TestBasicUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomePageOperations extends HomePageElement {

    @Test(priority = 4)
    public void test01clickOnSignonLink(){
        TestBasicUtils.step(1, "Clicking on SIGN-ON link");
        find(signOn).click();
    }

    @Test
    public void test02VerifyMenuLinks() {
        SoftAssert softAssert = new SoftAssert();
        String[] links = {"Home 1", "Flights", "Hotels", "Car Rentals", "Cruises", "Destinations", "Vacations"};
        List<String> linkList = Arrays.asList(links);
        List<WebElement> tableLinks = findList(linkTag);
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
        List<WebElement> rows = findTableRows(find(menuTable));
        softAssert.assertEquals(rows.size(), 7, " Menu table rows expected 7 but actual "+rows.size());
        softAssert.assertAll();
    }

    @Test
    public void test04HeaderLinksTable(){
        SoftAssert softAssert = new SoftAssert();
        TestBasicUtils.step(1, "Verify number of rows and columns in Link header table");
        int rows = findTableRows(find(menuTable)).size();
        softAssert.assertEquals(rows, 7, "Expecte number of rows 7, actual "+rows);
        int cols = findTableColumns(findTableRows(find(menuTable)).get(1)).size();
        softAssert.assertEquals(cols, 2, "Expected number of columns 2 actual "+cols);
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
        find(registerLink).click();
        File screenCapture = ((ChromeDriver) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenCapture, new File("target/surefire-reports/screens/testTakeScreenShotOfHomePage.jpg"), true);
            Reporter.log("Screenshot saved at : <a href=\"screens/testTakeScreenShotOfHomePage.jpg\">Failure screen</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
