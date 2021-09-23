package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.util.Properties;

public class LoginPage {
    WebDriver driver;

    private String propertyPath = "src/test/resources/mail.properties";
    private Properties properties = Utils.getProperties(propertyPath);
    private String email_create = "email_create";
    public String userName;

    @FindBy(id = "SubmitCreate")
    private WebElement submit_create;

    String firstName = "customer_firstname";

    @FindBy(id = "customer_lastname")
    private WebElement lastName;

    @FindBy(id = "passwd")
    private WebElement password;

    @FindBy(id = "address1")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "id_state")
    private WebElement state;

    @FindBy(id = "postcode")
    private WebElement postcode;

    @FindBy(id = "phone_mobile")
    private WebElement mobile;

    @FindBy(id = "submitAccount")
    private WebElement register;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "SubmitLogin")
    private WebElement signIn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public String getUserName() {
        return userName;
    }

    @Step("Create account")
    public void createAccount(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(email_create))).sendKeys(Utils.emailGenerator());
        submit_create.click();
    }

    @Step("Fill all required fields")
    public void fillRequiredFields(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String inputFirstName = Utils.stringGenerator();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(firstName))).sendKeys(inputFirstName);
        String inputLastName = Utils.stringGenerator();
        lastName.sendKeys(inputLastName);
        userName = inputFirstName + " " + inputLastName;
        password.sendKeys(properties.getProperty("PASSWORD_STORE"));
        address.sendKeys(Utils.addressGenerator());
        city.sendKeys("Sitka");
        selectState();
        postcode.sendKeys(Utils.numberGenerator(5));
        mobile.sendKeys(Utils.numberGenerator(12));
    }

    @Step("Select state")
    private void selectState(){
        Select states = new Select(state);
        states.selectByIndex(2);
    }

    @Step("Click Register button")
    public AccountPage register(){
        register.click();
        return new AccountPage(driver);
    }

    @Step("Log in account")
    public AccountPage signIn(){
        email.sendKeys(properties.getProperty("USER_NAME_STORE"));
        password.sendKeys(properties.getProperty("PASSWORD_STORE"));
        signIn.click();
        return new AccountPage(driver);
    }
}
