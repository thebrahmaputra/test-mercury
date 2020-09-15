package com.test.ultimateqa;

import com.pageobject.pages.UltimateQAPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UltimateQATest extends UltimateQAPage {
    @Test
    public void radioButtonTest(){
        List<WebElement> elms = findAll(radioBtns);
        for (WebElement elm : elms){
            logger.info(elm.getAttribute("value"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", elm);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }
}
