package com.testutils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserFactory {
    private String browser;
    private ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<WebDriver>();
    private Logger logger;

    public BrowserFactory(String browser, Logger logger){
        this.browser = browser;
        this.logger = logger;
    }

    public WebDriver createDriver(){
        System.out.println("Creating driver for browser :" +browser);

        switch (browser){
            case "chrome":
                System.setProperty("webdriver.chrome.driver","E:\\Softwares\\selenium\\chromedriver.exe");
                webDriverThreadLocal.set(new ChromeDriver());
                logger.info("Initialized browerd with ChromeDriver");
                break;

            default:
                System.setProperty("webdriver.chrome.driver","E:\\Softwares\\selenium\\chromedriver.exe");
                webDriverThreadLocal.set(new ChromeDriver());
                logger.info("Initialized browerd with Default");
        }
        return webDriverThreadLocal.get();
    }

}
