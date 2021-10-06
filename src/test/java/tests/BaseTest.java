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

@ExtendWith(Watcher.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    //WebDriver driver;

    @BeforeAll
    public void init(){
        WebDriver driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        Utils.setUpAllureEnvironment(driver);
    }

    @AfterAll
    public void closeWebDriver(){
        DriverSingleton.getInstance().closeWebDriver();
    }
}
