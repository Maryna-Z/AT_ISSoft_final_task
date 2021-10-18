package web_pages;

import driver.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;

    public static final long FAST_WAIT = 10;
    public static final long SLOW_WAIT = 20;

    public BasePage(){
        this.driver = DriverSingleton.getInstance().getCurrentWebDriver();
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForClickable(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, FAST_WAIT);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, FAST_WAIT);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
