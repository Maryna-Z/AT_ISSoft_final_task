package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    WebDriver driver;

    @FindBy(css = "tbody .product-name a")
    private List<WebElement> descriptions;

    @FindBy(css = "tbody input[value][type=text]")
    private List<WebElement> quantity;

    @FindBy(css = "tbody span[class=price] span")
    private List<WebElement> price;

    @FindBy(css = "#total_product")
    private WebElement totalProductPrice;

    @FindBy(css = "tbody a[title=Delete]")
    private List<WebElement> deleteElements;

    @FindBy(css = "#summary_products_quantity")
    private WebElement summaryProductsQuantity;

    public ShoppingCart(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Retrieve products name")
    public List<String> getProductsNames(){
        List<String> productsNames = new ArrayList<>();
        for(WebElement description : descriptions){
            productsNames.add(description.getText());
        }
        return productsNames;
    }

    @Step("Retrieve quantity of every product")
    public List<Integer> getProductsQuantity(){
        List<Integer> productsQuantity = new ArrayList<>();
        for(WebElement quantityOfOneProduct : quantity){
            productsQuantity.add(Integer.valueOf(quantityOfOneProduct.getAttribute("value")));
        }
        return productsQuantity;
    }

    @Step("Retrieve product price")
    public List<Integer> getProductPrice(){
        List<Integer> productsPrice = new ArrayList<>();
        for(WebElement productPrice : price){
            String productPriceText = productPrice.getText();
            productsPrice.add(Integer.valueOf(productPriceText.substring(1).trim()));
        }
        return productsPrice;
    }

    @Step("Retrieve total product quantity")
    public Integer getTotalProductPrice(){
        return Integer.valueOf(totalProductPrice.getText().substring(1).trim());
    }

    @Step("Delete products from cart")
    public void deleteAllProductsFromCart(){
        int size = deleteElements.size();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        if (size > 0){
            for(WebElement delete : deleteElements){
                delete.click();
                if (size > 1){
                    wait.until(ExpectedConditions.textToBePresentInElement(summaryProductsQuantity,size - 1 + " Products"));
                } else {
                    wait.until(ExpectedConditions.textToBePresentInElement(summaryProductsQuantity, size - 1 + " Product"));
                }
                size--;
            }
        }
    }

}
