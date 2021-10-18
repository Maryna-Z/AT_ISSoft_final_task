package tests;

import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import test_extensions.Watcher;
import utils.Utils;
import web_pages.Header;
import web_pages.LoginPage;

@ExtendWith(Watcher.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    @BeforeAll
    public void init(){
        WebDriver driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        Utils.setUpAllureEnvironment(driver);
    }

    @AfterAll
    public void closeWebDriver(){
        DriverSingleton.getInstance().closeWebDriver();
    }

    protected Header loginToSite(){
        Header header = new Header();
        LoginPage loginPage = new LoginPage();
        header.loginToSite();
        loginPage.signIn();
        return header;
    }
}
