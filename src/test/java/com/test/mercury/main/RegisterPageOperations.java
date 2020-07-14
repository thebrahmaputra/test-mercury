package com.test.mercury.main;

import com.testsetup.TestSetUp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.pageobject.pages.HomePageElement;

import java.lang.reflect.Method;

public class RegisterPageOperations extends TestSetUp {

    /*static WebDriver driver;
    static HomePageElement homePageElement;
    //static String url = "http://www.londonfreelance.org/courses/frames/index.html";
    static String url = "http://demo.guru99.com/selenium/newtours/index.php";
    @BeforeClass
    static void setUpClass(){
        System.setProperty("webdriver.chrome.driver","E:\\Softwares\\selenium\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.get(url);
        homePageElement = new HomePageElement(driver);
        homePageElement.getRegisterLink().click();
    }*/

    @Test
    public void test01CountLinks(){
        int linkCount = homePageElement.getLinks().size();
        Reporter.log("Total links on register page: "+linkCount);
    }

    @DataProvider(name = "hardCodedBrowsers", parallel = true )
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"internet explorer", "11", "Windows 8.1"},
                new Object[]{"chrome", "41", "Windows XP"},
                new Object[]{"safari", "7", "OS X 10.9"},
                new Object[]{"firefox", "35", "Windows 7"},
                new Object[]{"opera", "12.12", "Windows 7"}
       };
    }

    /*@AfterClass
    public static void tearDown(){
        driver.close();
        driver = null;
        homePageElement = null;
    }*/
}
