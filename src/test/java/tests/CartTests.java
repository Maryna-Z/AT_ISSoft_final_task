package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.CatalogPage;
import web_pages.Header;
import web_pages.LoginPage;
import web_pages.ShoppingCart;

import java.util.List;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("Shop functionality")
@Feature("Add into Cart")
@Tag("Critical")
@Execution(CONCURRENT)
public class CartTests extends BaseTest{
    private Header header;
    private ShoppingCart cart;

    @ParameterizedTest
    @ValueSource(ints = { 3})
    @Story("Add to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that products can be add to cart")
    @Tag("stable")
    public void addToCart(int numberOfProductInTheList){
        header = new Header(driver);
        LoginPage loginPage = header.loginToSite();
        loginPage.signIn();
        CatalogPage catalog = header.goToProductList();
        List<String> nameOfProductInCart = catalog.addProductsToCart(numberOfProductInTheList);
        catalog.goToShoppingCart();
        cart = new ShoppingCart(driver);
        List<String> productsNames = cart.getProductsNames();
        List<Integer> productsQuantity = cart.getProductsQuantity();
        List<Double> productPrice = cart.getProductPrice();
        Double totalProductPrice = cart.getTotalProductPrice();
        boolean namesInCartAndBeforeOrder = compareProductNamesInCartAndBeforeOrder(productsNames, nameOfProductInCart);
        Double calculatedTotalPrice = calculateTotalPrice(productPrice, productsQuantity);
        Assertions.assertAll("All products are in the cart and total is correct",
                () -> assertTrue(namesInCartAndBeforeOrder),
                () -> assertEquals(calculatedTotalPrice, totalProductPrice, "Total price is correct"));
    }

    private Double calculateTotalPrice(List<Double> productsPrice, List<Integer> productsQuantity){
        if (productsPrice.size() != productsQuantity.size()){
            return null;
        }
        Double totalPrice = 0.00;
        int size = productsPrice.size();
        for(int i = 0; i < size; i++){
            totalPrice += productsPrice.get(i) * productsQuantity.get(i);
        }
        return totalPrice;
    }

    private boolean compareProductNamesInCartAndBeforeOrder(List<String> productsInOrder, List<String> productsInCart){
        boolean flag = true;
        int size = productsInOrder.size();
        if(size != productsInOrder.size()){
            return false;
        }
        for(int i = 0; i < size; i++){
            if (productsInOrder.get(i).equals(productsInCart.get(i))){
                flag = true;
            } else{
                flag = false;
            }
        }
        return flag;
    }

    @AfterEach
    public void deleteAllProductsFromCart(){
        cart.deleteAllProductsFromCart();
    }

}
