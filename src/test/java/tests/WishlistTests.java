package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.AccountPage;
import web_pages.CatalogPage;
import web_pages.Header;
import web_pages.WishlistPage;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Shop functionality")
@Feature("Add into Wishlist")
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("Critical")
public class WishlistTests extends BaseTest{
    WishlistPage wishlist;

    @Test
    @Story("Content of wishlist tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that wishlist is empty")
    @Tag("SkipCleanup")
    public void verifyWishlistIsEmpty(){
        loginToSite();
        AccountPage account = new AccountPage();
        wishlist = new WishlistPage();
        account.goIntoWishlist();
        Assertions.assertTrue(!wishlist.isElementIsPresent(), "wishlist is empty");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1})
    @Story("Add to wishlist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product can be add to auto-created Wishlist")
    @Tag("stable")
    public void addToAutoCreatedWishlistProduct(int numberOfProductInTheList){
        Header header = loginToSite();
        CatalogPage catalog = new CatalogPage();
        header.goToProductList();
        List<String> productsName = catalog.addProductsToWishList(numberOfProductInTheList);
        AccountPage account = new AccountPage();
        header.goToAccount();
        wishlist = new WishlistPage();
        account.goIntoWishlist();
        wishlist.selectWishlist();
        String nameOfProductInWishlist = wishlist.nameOfProductInWishlist();
        int quantity = wishlist.retrieveProductQuantity();
        Assertions.assertAll("Wishlist was created automatically and my product is in the list",
                () -> assertTrue(wishlist.isElementIsPresent()),
                () -> assertEquals( nameOfProductInWishlist, productsName.get(0), "My product in wishlist"),
                () -> assertEquals( quantity, numberOfProductInTheList, "Quantity of product is 1")
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 1})
    @Story("Add to wishlist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product can be add to auto-created Wishlist")
    @Tag("stable")
    public void addToManuallyCreatedWishlistProduct(int numberOfProductInTheList){
        Header header = loginToSite();
        AccountPage account = new AccountPage();
        account.goIntoWishlist();
        wishlist = new WishlistPage();
        wishlist.createWishlist("My first list");
        CatalogPage catalog = new CatalogPage();
        header.goToProductList();
        List<String> productsName = catalog.addProductsToWishList(numberOfProductInTheList);
        header.goToAccount();
        account.goIntoWishlist();
        wishlist.selectWishlist();
        String nameOfProductInWishlist = wishlist.nameOfProductInWishlist();
        int quantity = wishlist.retrieveProductQuantity();
        Assertions.assertAll("Wishlist was created automatically and my product is in the list",
                () -> assertTrue(wishlist.isElementIsPresent()),
                () -> assertEquals( nameOfProductInWishlist, productsName.get(0), "My product in wishlist"),
                () -> assertEquals( quantity, numberOfProductInTheList, "Quantity of product is 1")
        );
    }


    @AfterEach
    public void deleteWishlist(TestInfo testInfo){
        if(testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        wishlist.deleteProductFromWishlist();
    }
}
