package tests;

import com.google.common.collect.ImmutableMap;
import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import test_extensions.ScreenshotRules;
import utils.Utils;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

@ExtendWith(ScreenshotRules.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    WebDriver driver;

    @BeforeAll
    public void init(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Utils.getBrowserName(driver))
                        .put("Browser.Version", Utils.getBrowserVersion(driver))
                        .put("OSName", Utils.getOsName(driver))
                        .build(), System.getProperty("user.dir") + "/target/allure-results"
        );
    }

    @AfterAll
    public void closeWebDriver(){
        DriverSingleton.getInstance().closeWebDriver();
    }
}
