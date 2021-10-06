package test_extensions;

import driver.DriverSingleton;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Parameter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Utils;

public class Watcher implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot();
        addTestResultParameters();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    public void takeScreenshot(){
        saveScreenshot(((TakesScreenshot) DriverSingleton.getInstance().getCurrentWebDriver()).getScreenshotAs(OutputType.BYTES));
    }

    public void addTestResultParameters(){
        final WebDriver webDriver = (RemoteWebDriver)DriverSingleton.getInstance().getCurrentWebDriver();

        String browser = String.format("%s:%s:%s",
                Utils.getBrowserName(webDriver),
                Utils.getBrowserVersion(webDriver),
                Utils.getOsName(webDriver));

        Allure.getLifecycle().updateTestCase(result -> {
            result.getParameters().add(new Parameter()
                    .withName("Browser")
                    .withValue(browser));
        });
    }
}
