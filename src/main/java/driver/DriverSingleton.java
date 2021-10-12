package driver;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;

public class DriverSingleton {
    private static volatile DriverSingleton instance;
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private DriverSingleton() {
    }

    public static DriverSingleton getInstance() {
        DriverSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (DriverSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DriverSingleton();
                }
            }
        }
        return localInstance;
    }

    @SneakyThrows
    public WebDriver getDriver(Config config) {
            WebDriver driver = webDriver.get();
            if (driver == null) {
                driver = DriverBuilder.initWebDriver(config).getDriver();
            }
            webDriver.set(driver);
            return driver;
        }

    public WebDriver getCurrentWebDriver() {
        return webDriver.get();
    }

    public void closeWebDriver() {
        WebDriver driver = this.webDriver.get();
        if (driver != null) {
            driver.quit();
            webDriver.set(null);
        }
    }
}
