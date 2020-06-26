package pageobject.pages;

import pageobject.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePageElement extends PageObject {
    @FindBy(xpath = "/html/body/div/table/tbody/tr/td[1]/table/tbody/tr/td/table/tbody/tr/td/table")
    private WebElement menuTable;

    @FindBy(tagName = "a")
    private List<WebElement> links;

    @FindBy(xpath = "/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[2]/td/table")
    private WebElement headerLinkTable;

    @FindBy(linkText = "REGISTER")
    private WebElement registerLink;

    @FindBy(linkText = "Home")
    private WebElement homePageLink;

    public HomePageElement(WebDriver driver){
        super(driver);
    }

    public List<WebElement> getLinks(){
        return links;
    }

    public WebElement getMenuTable(){
        return menuTable;
    }

    public WebElement getHeaderLinkTable() { return headerLinkTable; }

    public WebElement getRegisterLink() { return registerLink; }

    public WebElement getHomePageLink() {return homePageLink;}
}
