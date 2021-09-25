package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.BaseTest;

public class WishlistPage extends BaseTest {
    WebDriver driver;

    @FindBy(css = "#block-history table")
    private WebElement wishlistTable;

    private String wishlistTableStr = "#block-history table";

    @FindBy(css = "#block-history table td a")
    private WebElement wishlist;

    String productDescriptionStr = "#s_title";

    @FindBy(css = "#s_title a[title='Product detail']")
    private WebElement productDetail;

    @FindBy(css = "p input[id=quantity_1_1]")
    private WebElement quantity;

    @FindBy(css = "#block-history table td[class=wishlist_delete] a")
    private WebElement deleteWishlist;

    @FindBy(css = "#name")
    private WebElement name;

    @FindBy(id = "submitWishlist")
    private WebElement submit;

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Verify that wishlist table is presented")
    public boolean isElementVisible(){
        try {
            return driver.findElement(By.cssSelector(wishlistTableStr)).isDisplayed();
        } catch (NoSuchElementException ex){
            return false;
        }
    }

    @Step("Select wishlist")
    public void selectWishlist(){
        wishlist.click();
    }

    @Step("Retrieve name of product from wishlist")
    public String nameOfProductInWishlist(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String textOfProductDescription = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(productDescriptionStr)))
                .getText();
        String[] descriptions = textOfProductDescription.split("\n");
        String productName = descriptions[0].trim();
        return productName;
    }

    @Step("Retrieve product quantity")
    public int retrieveProductQuantity(){
        return Integer.valueOf(quantity.getAttribute("value"));
    }

    @Step("Delete product from wishlist")
    public void deleteProductFromWishlist(){
        deleteWishlist.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Step("Create wishlist")
    public void createWishlist(String nameOfWishlist){
        name.sendKeys(nameOfWishlist);
        submit.click();
    }

}
