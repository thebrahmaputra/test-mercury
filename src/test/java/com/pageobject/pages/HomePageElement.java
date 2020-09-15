package com.pageobject.pages;

import com.pageobject.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePageElement extends PageObject {

    protected By signOn = By.linkText("SIGN-ON");
    protected By registerLink = By.linkText("REGISTER");
    protected By linkTag = By.tagName("a");
    protected By menuTable = By.xpath("/html/body/div/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table");

}
