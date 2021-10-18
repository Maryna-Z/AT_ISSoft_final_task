package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage extends BasePage{

    @FindBy(css = ".lnk_wishlist")
    private WebElement wishList;

    public AccountPage() {
        PageFactory.initElements(this.driver, this);
    }

    @Step("Retrieve title name")
    public String getTitleName(){
        return driver.getTitle();
    }

    @Step("Go into wishlist")
    public void goIntoWishlist(){
        wishList.click();
    }
}
