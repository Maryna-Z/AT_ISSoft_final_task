package driver;

import exceptions.CommonException;
import lombok.SneakyThrows;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverBuilder {
    public static String nodeURL = "http://localhost:4444/wd/hub";

    public static DriverBuilderStrategy initWebDriver(Config config){
        DriverBuilderStrategy driver;
            switch (config) {
                case CHROME:
                    driver = new DriverBuilderChrome();
                    break;
                case FF:
                    driver = new DriverBuilderFirefox();
                    break;
                case REMOTE_FF:
                    driver = new DriverBuilderRemoteChrome();
                    break;
                case REMOTE_CHROME:
                    driver = new DriverBuilderRemoteFirefox();
                    break;
                default:
                    throw new CommonException("Not supported driver type");
            }
            return driver;
    }

    public static class DriverBuilderChrome implements DriverBuilderStrategy {

        @Override
        public WebDriver getDriver() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            return new ChromeDriver(chromeOptions);
        }
    }

    public static class DriverBuilderFirefox implements DriverBuilderStrategy {

        @Override
        public WebDriver getDriver() {
            System.setProperty("webdriver.gecko.driver", "c:\\Users\\user\\Java\\webdrivers\\firefox\\geckodriver.exe");
            return new FirefoxDriver();
        }
    }

    public static class DriverBuilderRemoteChrome implements DriverBuilderStrategy {

        @SneakyThrows
        @Override
        public WebDriver getDriver() {
            DesiredCapabilities capabilitiesH = DesiredCapabilities.chrome();
            capabilitiesH.setBrowserName("chrome");
            capabilitiesH.setVersion("93.0.4577.82");
            capabilitiesH.setPlatform(Platform.WINDOWS);
            return new RemoteWebDriver(new URL(nodeURL), capabilitiesH);
        }
    }

    public static class DriverBuilderRemoteFirefox implements DriverBuilderStrategy {

        @SneakyThrows
        @Override
        public WebDriver getDriver() {
            DesiredCapabilities capabilitiesF = DesiredCapabilities.firefox();
            capabilitiesF.setBrowserName("firefox");
            capabilitiesF.setVersion("92.0");
            capabilitiesF.setPlatform(Platform.WINDOWS);
            return new RemoteWebDriver(new URL(nodeURL), capabilitiesF);
        }
    }
}
