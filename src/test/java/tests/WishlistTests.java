package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.*;

import java.util.List;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Shop functionality")
@Feature("Add into Wishlist")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("Critical")
public class WishlistTests extends BaseTest{
    private Header header;
    private WishlistPage wishlist;
    private AccountPage account;

    private void signIn(){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        AccountPage account = loginPage.signIn();
    }

    @Test
    @Story("Content of wishlist tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that wishlist is empty")
    @Tag("SkipCleanup")
    public void verifyWishlist(){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        AccountPage account = loginPage.signIn();
        WishlistPage wishlist = account.goIntoWishlist();
        Assertions.assertTrue(!wishlist.isElementVisible(), "wishlist is empty");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1})
    @Story("Add to wishlist")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that product can be add to auto-created Wishlist")
    @Tag("stable")
    public void addToAutoCreatedWishlistProduct(int numberOfProductInTheList){
        verifyWishlist();
        CatalogPage catalog = header.goToProductList();
        List<String> productsName = catalog.addProductsToWishList(numberOfProductInTheList);
        account = header.goToAccount();
        wishlist = account.goIntoWishlist();
        wishlist.selectWishlist();
        String nameOfProductInWishlist = wishlist.nameOfProductInWishlist();
        int quantity = wishlist.retrieveProductQuantity();
        Assertions.assertAll("Wishlist was created automatically and my product is in the list",
                () -> assertTrue(wishlist.isElementVisible()),
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
        verifyWishlist();
        wishlist.createWishlist("My first list");
        CatalogPage catalog = header.goToProductList();
        List<String> productsName = catalog.addProductsToWishList(numberOfProductInTheList);
        account = header.goToAccount();
        wishlist.selectWishlist();
        String nameOfProductInWishlist = wishlist.nameOfProductInWishlist();
        int quantity = wishlist.retrieveProductQuantity();
        Assertions.assertAll("Wishlist was created automatically and my product is in the list",
                () -> assertTrue(wishlist.isElementVisible()),
                () -> assertEquals( nameOfProductInWishlist, productsName.get(0), "My product in wishlist"),
                () -> assertEquals( quantity, 1, "Quantity of product is 1")
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
