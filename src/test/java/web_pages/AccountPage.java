package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {
    WebDriver driver;

    @FindBy(css = "[title = 'My wishlists']")
    private WebElement wishList;

    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Retrieve title name")
    public String getTitleName(){
        return driver.getTitle();
    }

    @Step("Go into wishlist")
    public WishlistPage goIntoWishlist(){
        wishList.click();
        return new WishlistPage(driver);
    }
}
