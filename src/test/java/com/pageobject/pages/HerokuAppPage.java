package com.pageobject.pages;

import com.pageobject.PageObject;
import org.openqa.selenium.By;

public class HerokuAppPage extends PageObject {
    protected By checkbox = By.xpath("//input[@type='checkbox']");
    protected By checkBoxPageLink = By.linkText("Checkboxes");
    protected By dropDown = By.id("dropdown");
    protected By dropDownLink = By.linkText("Dropdown");
    protected By alertLink = By.linkText("JavaScript Alerts");
    protected By jsAlertButton = By.xpath("//*[@id='content']/div/ul/li[1]/button");
    protected By jsAlertConfirmButton = By.xpath("//*[@id='content']/div/ul/li[2]/button");
    protected By jsAlertPromptButton = By.xpath("//*[@id='content']/div/ul/li[3]/button");
    protected By alertClickResult = By.id("result");
    protected By newWindowLink = By.linkText("Multiple Windows");
    protected By newWindowLauncherLink = By.linkText("Click Here");
}
