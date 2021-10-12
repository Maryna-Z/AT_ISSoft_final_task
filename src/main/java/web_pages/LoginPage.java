package web_pages;

import dto.UserDTO;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.Parser;
import utils.Utils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage{

    private String propertyPath = "src/test/resources/mail.properties";
    private Properties properties = Utils.getProperties(propertyPath);

    @FindBy(id = "email_create")
    private WebElement emailCreateInput;

    private String email_createStr = "email_create";
    public String userName;

    @FindBy(id = "SubmitCreate")
    private WebElement submit_create;

    String firstNameStr = "customer_firstname";

    @FindBy(id = "customer_firstname")
    private WebElement firstNameInput;

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

    public LoginPage() {
        PageFactory.initElements(this.driver, this);
    }

    public String getUserName() {
        return userName;
    }

    @Step("Create account")
    public void clickCreateAccButton(){
        waitForVisible(emailCreateInput).sendKeys(Utils.emailGenerator());
        submit_create.click();
    }

    @Step("Fill all required fields")
    public void fillRequiredFields(){
        UserDTO userDTO = Parser.readFromFile();
        String inputFirstName = userDTO.getFirstName();
        waitForVisible(firstNameInput).sendKeys(inputFirstName);
        String inputLastName = userDTO.getLastName();
        lastName.sendKeys(inputLastName);
        userName = inputFirstName + " " + inputLastName;
        password.sendKeys(userDTO.getPassword());
        address.sendKeys(userDTO.getAddress());
        city.sendKeys(userDTO.getCity());
        selectState();
        postcode.sendKeys(userDTO.getPostCode());
        mobile.sendKeys(Utils.numberGenerator(12));
    }

    @Step("Select state")
    private void selectState(){
        Select states = new Select(state);
        states.selectByIndex(2);
    }

    @Step("Click Register button")
    public void register(){
        register.click();
    }

    @Step("Log in account")
    public void signIn(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        email.sendKeys(properties.getProperty("USER_NAME_STORE"));
        password.sendKeys(properties.getProperty("PASSWORD_STORE"));
        signIn.click();
    }
}
