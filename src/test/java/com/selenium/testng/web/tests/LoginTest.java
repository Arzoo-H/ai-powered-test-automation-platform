package com.selenium.testng.web.tests;

import org.testng.annotations.Test;

import com.selenium.testng.base.BaseTest;
import com.selenium.testng.utils.AssertionUtils;
import com.selenium.testng.web.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(groups = {"ui-smoke"})
    public void verifyLogin() {

        LoginPage login = new LoginPage(driver);

        AssertionUtils.assertTrue(login.loginToPortal("Admin", "admin"), 
        							"Entered credentials into the portal and hit login button");
        
        AssertionUtils.assertFalse(login.isLoginButtonDisplayed(), 
									"Logged into the portal as Login button is not displayed");
        
    }
}