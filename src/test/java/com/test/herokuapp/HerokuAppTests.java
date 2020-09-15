package com.test.herokuapp;

import com.pageobject.pages.HerokuAppNewWindow;
import com.pageobject.pages.HerokuAppPage;
import com.testutils.AlertHandler;
import com.testutils.TestBasicUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class HerokuAppTests extends HerokuAppPage {

    @Parameters({"url"})
    @BeforeMethod
    public void beforeTest(String url){
        driver.navigate().to(url);
    }

    @Test
    public void test01CheckBoxDefaultBehavior() {
        logger.info("Opened URL " + driver.getCurrentUrl());
        TestBasicUtils.step(1, "Click on checkbox link");
        find(checkBoxPageLink).click();
        List<WebElement> checkboxes = findAll(checkbox);
        TestBasicUtils.step(2, "Check which checkbox is selected");
        logger.info("Number of checkboxes " +checkboxes.size());
        for (WebElement chbox : checkboxes) {
            if (chbox.isSelected()){
                logger.info(chbox.getText()+ " is selected");
                TestBasicUtils.logDetails(chbox.getText()+ " is selected");
            } else {
                logger.info(chbox.getText()+ " is not selected");
                TestBasicUtils.logDetails(chbox.getText()+ " is not selected");
            }
        }
    }

    @Test
    public void test02DropDown(){
        TestBasicUtils.step(1, "Click on drop down link (Dropdown)");
        find(dropDownLink).click();

        TestBasicUtils.step(2, "Select Option 1");
        selectDropDownItemByVisibleText(find(dropDown), "Option 1");

        TestBasicUtils.step(3, "Verify selected option");
        verifySelectedDropdownOption(find(dropDown), "Option 1");
    }

    @Test
    public void test03SimpleAlertClick(){
        TestBasicUtils.step(1, "Click on alert page link");
        clickElement(alertLink);

        TestBasicUtils.step(2, "Click on 'Click for JS Alert' button");
        clickElement(jsAlertButton);

        TestBasicUtils.step(3, "Verify alert text");
        AlertHandler alertHandler = new AlertHandler(driver, logger);
        alertHandler.verifyAlertText( "I am a JS Alert");

        TestBasicUtils.step(4, "Accept the alert");
        alertHandler.acceptAlert();

        TestBasicUtils.step(5, "Verify result");
        String text = find(alertClickResult).getText();
        Assert.assertEquals(text, "You successfuly clicked an alert");
    }

    @Test
    public void test04SimpleAlertButtonClick(){
        TestBasicUtils.step(1, "Click on alert page link");
        clickElement(alertLink);

        TestBasicUtils.step(2, "Click on 'Click for JS Alert' button");
        clickElement(jsAlertConfirmButton);

        TestBasicUtils.step(3, "Verify alert text");
        AlertHandler alertHandler = new AlertHandler(driver, logger);
        alertHandler.verifyAlertText( "I am a JS Confirm");

        TestBasicUtils.step(4, "Cancel the alert");
        alertHandler.cancelAlert();

        TestBasicUtils.step(5, "Verify result");
        String text = find(alertClickResult).getText();
        Assert.assertEquals(text, "You clicked: Cancel");
    }

    @Test
    public void test05AcceptPromptOnAlertButtonClick(Method method){
        TestBasicUtils.step(1, "Click on alert page link");
        clickElement(alertLink);

        TestBasicUtils.step(2, "Click on 'Click for JS Alert' button");
        clickElement(jsAlertPromptButton);

        TestBasicUtils.step(3, "Verify alert text");
        AlertHandler alertHandler = new AlertHandler(driver, logger);
        alertHandler.verifyAlertText( "I am a JS prompt");

        TestBasicUtils.step(4, "Enter test name to alert");
        String alertInput = method.getName();
        alertHandler.inputToAlert(alertInput);

        TestBasicUtils.step(5, "Accept the alert");
        alertHandler.acceptAlert();

        TestBasicUtils.step(6, "Verify result");
        String text = find(alertClickResult).getText();
        Assert.assertEquals(text, "You entered: test05AcceptPromptOnAlertButtonClick");
    }

    @Test
    public void test06CancelPromptOnAlertButtonClick(Method method){
        TestBasicUtils.step(1, "Click on alert page link");
        clickElement(alertLink);

        TestBasicUtils.step(2, "Click on 'Click for JS Alert' button");
        clickElement(jsAlertPromptButton);

        TestBasicUtils.step(3, "Verify alert text");
        AlertHandler alertHandler = new AlertHandler(driver, logger);
        alertHandler.verifyAlertText( "I am a JS prompt");

        TestBasicUtils.step(4, "Enter test name to alert");
        String alertInput = method.getName();
        alertHandler.inputToAlert(alertInput);

        TestBasicUtils.step(5, "Cancel the alert");
        alertHandler.cancelAlert();

        TestBasicUtils.step(6, "Verify result");
        String text = find(alertClickResult).getText();
        Assert.assertEquals(text, "You entered: null");
    }

    @Test
    public void test07SwitchWindows(){
        TestBasicUtils.step(1, "Click on multiple page app link");
        clickElement(newWindowLink);

        TestBasicUtils.step(2, "Click on new window launcher link");
        clickElement(newWindowLauncherLink);

        TestBasicUtils.step(3, "Switch to new window with title 'New Window'");
        switchToWindowWithTitle("New Window");

        TestBasicUtils.step(4, "Verify new page text");
        HerokuAppNewWindow herokuAppNewWindow = new HerokuAppNewWindow(driver);
        String newWindowText = find(herokuAppNewWindow.plainText).getText();
        Reporter.log("Text in new window : " +newWindowText);
        Assert.assertEquals(newWindowText, "New Window");
    }
}
