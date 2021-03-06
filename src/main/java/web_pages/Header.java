package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Utils;

import java.util.Properties;

public class Header extends BasePage {
    private String propertyPath = "src/test/resources/sites.properties";
    private Properties properties = Utils.getProperties(propertyPath);


    @FindBy(css = ".account span")
    private WebElement viewCustomerAccountName;

    @FindBy(css = ".account")
    private WebElement userAccount;

    @FindBy(css = "#block_top_menu a[title='Women']")
    private WebElement menuWomen;

    @FindBy(xpath = "//header[@id = 'header']//div[@class='nav']//a[@class='login']")
    private WebElement signInButton;

    public Header() {
        PageFactory.initElements(this.driver, this);
    }

    @Step("Click sign in button")
    public void loginToSite(){
        driver.get(properties.getProperty("URL"));
        waitForClickable(signInButton).click();
    }

    @Step("go to product list")
    public void goToProductList(){
        menuWomen.click();
    }

    @Step("Retrieve user name from header")
    public String getUserName(){
        return viewCustomerAccountName.getText();
    }

    @Step("Go to account")
    public void goToAccount(){
        waitForClickable(userAccount).click();
    }
}
