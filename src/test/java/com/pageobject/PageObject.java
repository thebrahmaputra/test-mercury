package com.pageobject;

import com.pageobject.pages.HerokuAppNewWindow;
import com.testsetup.TestSuiteSetup;
import javafx.scene.effect.SepiaTone;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PageObject extends TestSuiteSetup {

    public void clickElement(By locator){
        driver.findElement(locator).click();
    }

    public void typeKeys(By locator, String val){
        driver.findElement(locator).sendKeys(val);
    }

    public WebElement find(By locator){
        return driver.findElement(locator);
    }

    public List<WebElement> findList(By locator){
        return driver.findElements(locator);
    }

    public List<WebElement> findTableRows(WebElement element){
        return element.findElements(By.tagName("tr"));
    }

    public List<WebElement> findTableColumns(WebElement element){
        return element.findElements(By.tagName("td"));
    }

    public List<WebElement> findTableColumnHeaders(WebElement element){
        return element.findElements(By.tagName("th"));
    }

    public List<WebElement> findAll(By locator){
        return driver.findElements(locator);
    }

    public void selectDropDownItemByVisibleText(WebElement element, String visibleText){
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public void verifySelectedDropdownOption(WebElement element, String val){
        Select select = new Select(element);
        String selecteText = select.getFirstSelectedOption().getText();
        Assert.assertEquals(selecteText, val);
    }

    public void switchToWindowWithTitle(String title){

        String firstWindow = driver.getWindowHandle();
        Set <String> windHandles = driver.getWindowHandles();
        Iterator iterator = windHandles.iterator();
        while (iterator.hasNext()){
            String curWindowHandle = iterator.next().toString();
            if (!curWindowHandle.equals(firstWindow)){
                driver.switchTo().window(curWindowHandle);
                if (driver.getTitle().equals("New Window")) {
                    break;
                }
            }
        }
    }


}
