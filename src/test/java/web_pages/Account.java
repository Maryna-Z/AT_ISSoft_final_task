package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Account {
    WebDriver driver;

    @FindBy(css = "[title = 'View my customer account'] span")
    private WebElement viewCustomerAccount;

    public Account(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Retrieve title name")
    public String getTitleName(){
        return driver.getTitle();
    }

    @Step("Retrieve user name from header")
    public String getUserName(){
        return viewCustomerAccount.getText();
    }
}
