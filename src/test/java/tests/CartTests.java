package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.*;

import java.util.List;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.testng.Assert.assertEquals;

@Epic("Shop functionality")
@Feature("Add into Cart")
@Tag("Critical")
@Execution(CONCURRENT)
public class CartTests extends BaseTest{
    private Header header;
    private WishlistPage wishlist;

    @ParameterizedTest
    @ValueSource(ints = { 1})
    @Story("Add to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that products can be add to cart")
    @Tag("stable")
    public void addToCart(int numberOfProductInTheList){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        loginPage.signIn();
        CatalogPage catalog = header.goToProductList();
        List<String> nameOfProductInCart = catalog.addProductsToCart(3);
        catalog.goToShoppingCart();
        ShoppingCart cart = new ShoppingCart(driver);
        List<String> productsNames = cart.getProductsNames();
        List<Integer> productsQuantity = cart.getProductsQuantity();
        List<Integer> productPrice = cart.getProductPrice();
        Integer totalProductPrice = cart.getTotalProductPrice();
        Assertions.assertAll("All products are in the cart and total is correct",
                () -> assertEquals(productsQuantity.size())
                );



    }

}
