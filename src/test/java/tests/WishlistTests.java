package tests;

import driver.DriverSingleton;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
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
    private WebDriver driver = DriverSingleton.getInstance().getCurrentWebDriver();

    @Test
    @Story("Content of wishlist tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that wishlist is empty")
    @Tag("SkipCleanup")
    public void verifyWishlist(){
        header = new Header();
        LoginPage loginPage = new LoginPage();
        header.loginToSite();
        AccountPage account = new AccountPage();
        loginPage.signIn();
        WishlistPage wishlist = new WishlistPage();
        account.goIntoWishlist();
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
        CatalogPage catalog = new CatalogPage();
        header.goToProductList();
        List<String> productsName = catalog.addProductsToWishList(numberOfProductInTheList);
        account = new AccountPage();
        header.goToAccount();
        wishlist = new WishlistPage();
        account.goIntoWishlist();
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
        header = new Header();
        LoginPage loginPage = new LoginPage();
        header.loginToSite();
        account = new AccountPage();
        loginPage.signIn();
        wishlist = new WishlistPage();
        account.goIntoWishlist();
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
                () -> assertTrue(wishlist.isElementVisible()),
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
