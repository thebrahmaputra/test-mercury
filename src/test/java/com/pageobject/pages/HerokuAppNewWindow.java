package com.pageobject.pages;

import com.pageobject.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HerokuAppNewWindow extends PageObject{

    public By plainText = By.xpath("/html/body/div/h3");

    public HerokuAppNewWindow(WebDriver driver){
        this.driver = driver;
    }

}
