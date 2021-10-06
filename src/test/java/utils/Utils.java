package utils;

import com.google.common.collect.ImmutableMap;
import exceptions.NoSuchFileException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.xml.xpath.XPath;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class Utils {

    private static Properties properties = new Properties();

    public static Properties getProperties(String path) {
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new NoSuchFileException("File not found", ex);
        }
        return properties;
    }

    public static String fileNameGenerator() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
        String path = "src/test/resources/";
        return path + "screenshot" + formatter.format(new Date()).replaceAll(":", "-")
                + " " + RandomStringUtils.randomAlphanumeric(10) + ".png";
    }

    public static String emailGenerator(){
        return "ee1vp" + RandomStringUtils.randomAlphanumeric(10) + "@yandex.by";
    }

    public static String stringGenerator(){
        return RandomStringUtils.randomAlphabetic(5);
    }

    public static String addressGenerator(){
        return stringGenerator() + ", "
                + RandomStringUtils.randomNumeric(1, 3) + "-" + RandomStringUtils.randomNumeric(1, 3);
    }

    public static String numberGenerator(int count){
        return RandomStringUtils.randomNumeric(count);
    }

    public static String getBrowserName(WebDriver driver){
        Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
        return cap.getBrowserName().toLowerCase();
    }

    public static String getBrowserVersion(WebDriver driver){
        Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
        return cap.getVersion();
    }

    public static String getOsName(WebDriver driver){
        Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
        return cap.getPlatform().toString();
    }

    public static void setUpAllureEnvironment(WebDriver driver){
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", getBrowserName(driver))
                        .put("Browser.Version", getBrowserVersion(driver))
                        .put("OSName", getOsName(driver))
                        .build(), System.getProperty("user.dir") + "/target/allure-results/"
        );
    }
}
