package test_extensions;

import driver.DriverSingleton;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Parameter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Optional;

public class ScreenshotRules implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot();
        addTestResultParameters();
        DriverSingleton.getInstance().closeWebDriver();
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        DriverSingleton.getInstance().closeWebDriver();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        DriverSingleton.getInstance().closeWebDriver();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        DriverSingleton.getInstance().closeWebDriver();
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    public void takeScreenshot(){
        saveScreenshot(((TakesScreenshot) DriverSingleton.getInstance().getCurrentWebDriver()).getScreenshotAs(OutputType.BYTES));
    }

    public void addTestResultParameters(){

        Capabilities cap = ((RemoteWebDriver) DriverSingleton.getInstance().getCurrentWebDriver()).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        String os = cap.getPlatform().toString().toLowerCase();
        final String browserVersion = cap.getVersion().toLowerCase();
        String browser = String.format("%s:%s:%s", browserName, browserVersion, os);

        Allure.getLifecycle().updateTestCase(result -> {
            result.getParameters().add(new Parameter()
            .withName("Browser")
            .withValue(browser));
        });
    }
}
