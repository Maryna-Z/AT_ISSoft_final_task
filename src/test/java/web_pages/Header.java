package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {
    WebDriver driver;

    @FindBy(css = "[title = 'View my customer account'] span")
    private WebElement viewCustomerAccountName;

    @FindBy(css = "a[title = 'View my customer account']")
    private WebElement userAccount;

    @FindBy(css = "#block_top_menu a[title=\"Women\"]")
    private WebElement menuWomen;

    private String url = "http://automationpractice.com/";

    @FindBy(xpath = "//header[@id = 'header']//div[@class='nav']//a[@class='login']")
    private WebElement signInButton;

    @FindBy(css = "[title='View my shopping cart']")
    private WebElement viewShoppingCart;



    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Click sign in button")
    public LoginPage loginToSite(){
        driver.get(url);
        signInButton.click();
        return new LoginPage(driver);
    }

    @Step("go to product list")
    public CatalogPage goToProductList(){
        menuWomen.click();
        return new CatalogPage(driver);
    }

    @Step("Retrieve user name from header")
    public String getUserName(){
        return viewCustomerAccountName.getText();
    }

    @Step("Go to account")
    public AccountPage goToAccount(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(userAccount)).click();
        return new AccountPage(driver);
    }
}
