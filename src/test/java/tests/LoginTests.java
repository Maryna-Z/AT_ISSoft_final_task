package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.AccountPage;
import web_pages.Header;

import static org.testng.Assert.assertEquals;

@Epic("Shop functionality")
@Feature("Login in account")
@Tag("Critical")
public class LoginTests extends BaseTest{

    @ParameterizedTest
    @ValueSource(strings = { "My account - My Store"})
    @Story("Log in account tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User log in account")
    @Tag("stable")
    public void loginIntoAccount(String title){
        Header header = loginToSite();
        AccountPage account = new AccountPage();
        Assertions.assertAll("Page name and user name are correct",
                () -> assertEquals(account.getTitleName(), title, "Verify page name"),
                () -> assertEquals( header.getUserName(), "Marina Zagorskaya" , "Verify account")
        );
    }
}
