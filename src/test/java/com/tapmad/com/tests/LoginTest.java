package com.tapmad.com.tests;

import com.tapmad.com.base.Base;
import com.tapmad.com.pages.LoginPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login")

public class LoginTest extends Base {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Verify user can login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Valid user login")

    public void testLoginFlow() {

        loginPage.openMenu();
        loginPage.selectDropdownItem();
        loginPage.login("************", "****");
        String successText = loginPage.getLoginSuccessPopupText();
        // Force a failure to test screenshot (remove later)
        // Assert.assertEquals(successText, "wrong text");
        Assert.assertTrue(
                successText.trim().contains("Logged-In Successfully"),
                "Expected success message not found. Actual text was: [" + successText + "]"
        );
    }
}
