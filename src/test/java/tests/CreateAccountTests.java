package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web_pages.Account;
import web_pages.HomePage;
import web_pages.LoginPage;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.testng.Assert.assertEquals;

@Epic("Shop functionality")
@Feature("Create account")
@Tag("Critical")
@Execution(CONCURRENT)
public class CreateAccountTests extends BaseTest{
    private HomePage homePage;

    @ParameterizedTest
    @ValueSource(strings = { "My account - My Store" })
    @Story("Create account tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User creates account")
    @Tag("stable")
    public void createAccount(String title){
        homePage = new HomePage(driver);
        LoginPage loginPage = homePage.loginToSite();
        loginPage.createAccount();
        loginPage.fillRequiredFields();
        Account account = loginPage.register();
        Assertions.assertAll("Page name and user name are correct",
                () -> assertEquals(account.getTitleName(), title, "Verify page name"),
                () -> assertEquals( account.getUserName(), loginPage.getUserName(), "Verify account")
        );
    }
}
