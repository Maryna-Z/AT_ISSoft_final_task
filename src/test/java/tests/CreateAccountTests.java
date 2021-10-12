package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.AccountPage;
import web_pages.Header;
import web_pages.LoginPage;

import static org.testng.Assert.assertEquals;

@Epic("Shop functionality")
@Feature("Create account")
@Tag("Critical")
public class CreateAccountTests extends BaseTest{

    @ParameterizedTest
    @ValueSource(strings = { "My account - My Store" })
    @Story("Create account tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User creates account")
    @Tag("stable")
    public void createAccount(String title){
        Header header = new Header();
        LoginPage loginPage = new LoginPage();
        header.loginToSite();
        loginPage.clickCreateAccButton();
        loginPage.fillRequiredFields();
        AccountPage account = new AccountPage();
        loginPage.register();
        Assertions.assertAll("Page name and user name are correct",
                () -> assertEquals(account.getTitleName(), title, "Verify page name"),
                () -> assertEquals( header.getUserName(), loginPage.getUserName(), "Verify account")
        );
    }
}
