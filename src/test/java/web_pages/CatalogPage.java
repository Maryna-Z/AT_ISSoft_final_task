package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatalogPage {
    WebDriver driver;

    @FindBy(xpath = "//div[@id='center_column']//ul[@class = 'product_list grid row']//li[1]")
    private WebElement product;

    private String productStr = "//div[@id='center_column']//ul[@class = 'product_list grid row']//li[%s]";

    @FindBy(css = ".quick-view")
    private WebElement quickView;

    String wishListButtonStr = "#buy_block #wishlist_button";

    private String closeStr = "[title = Close]";

    @FindBy(css = "[title = Close]")
    private WebElement close;

    @FindBy(css = "#product h1[itemprop=\"name\"]")
    private WebElement productName;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Add product in wishlist")
    public String addProductToWishList(int numberOfProductInList){
        new Actions(driver).moveToElement(driver.findElement(By.xpath(String.format(productStr,numberOfProductInList))))
                .perform();
        new Actions(driver).moveToElement(quickView).click().perform();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.switchTo().frame(0);
        String productName = retrieveProductName().trim();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(wishListButtonStr))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(closeStr))).click();
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(closeStr))).click();
        return productName;
    }

    @Step("Retrieve product name")
    public String  retrieveProductName(){
        return productName.getText();
    }
}
