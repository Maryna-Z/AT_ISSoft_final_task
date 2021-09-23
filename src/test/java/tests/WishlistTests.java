package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.*;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Shop functionality")
@Feature("Add into Wishlist")
@Tag("Critical")
@Execution(CONCURRENT)
public class WishlistTests extends BaseTest{
    private Header header;
    private WishlistPage wishlist;

    @Test
    @Story("Content of wishlist tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that wishlist is empty")
    @Tag("stable")
    public void verifyWishlist(){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        AccountPage account = loginPage.signIn();
        WishlistPage wishlist = account.goIntoWishlist();
        Assertions.assertTrue(wishlist.isElementVisible(), "wishlist is empty");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1})
    @Story("Add to wishlist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product can be add to auto-created Wishlist")
    @Tag("unstable")
    public void addToAutoCreatedWishlist(int numberOfProductInTheList){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        AccountPage account = loginPage.signIn();
        CatalogPage catalog = header.goToProductList();
        String productName = catalog.addProductToWishList(numberOfProductInTheList).trim();
        account = header.goToAccount();
        wishlist = account.goIntoWishlist();
        wishlist.selectWishlist();
        String nameOfProductInWishlist = wishlist.nameOfProductInWishlist();
        int quantity = wishlist.retrieveProductQuantity();
        Assertions.assertAll("Wishlist was created automatically and my product is in the list",
                () -> assertTrue(wishlist.isElementVisible()),
                () -> assertEquals( nameOfProductInWishlist, productName, "My product in wishlist"),
                () -> assertEquals( quantity, 1, "Quantity of product is 1")
        );
    }

    @AfterEach
    public void deleteWishlist(){
        wishlist.deleteProductFromWishlist();
    }
}
