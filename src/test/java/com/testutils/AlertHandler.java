package com.testutils;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class AlertHandler {
    Alert alert;
    Logger logger;
    public AlertHandler(WebDriver driver, Logger logger){
        this.alert = driver.switchTo().alert();
        this.logger = logger;
    }

    public void acceptAlert(){
        logger.info("Accepting alert");
        alert.accept();
        logger.info("Accepted alert");
    }

    public void cancelAlert(){
        logger.info("Cancelling alert");
        alert.dismiss();
    }

    public void verifyAlertText(String textToVerify){
        Reporter.log("Alert text -> " +alert.getText());
        logger.info("Verifying alert text -> " +textToVerify);
        Assert.assertEquals(alert.getText(), textToVerify);
    }

    public void inputToAlert(String alertInput){
        alert.sendKeys(alertInput);
    }
}
