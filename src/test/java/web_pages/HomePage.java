package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.util.Properties;

public class HomePage {
    WebDriver driver;

    private String url = "http://automationpractice.com/";

    @FindBy(xpath = "//header[@id = 'header']//div[@class='nav']//a[@class='login']")
    private WebElement signInButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Click sign in button")
    public LoginPage loginToSite(){
        driver.get(url);
        signInButton.click();
        return new LoginPage(driver);
    }
}