package com.selenium.testng.web.tests;

import org.testng.annotations.Test;

import com.selenium.testng.base.BaseTest;
import com.selenium.testng.utils.AssertionUtils;
import com.selenium.testng.web.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(groups = {"ui-smoke"})
    public void verifyLogin() {

        LoginPage login = new LoginPage(driver);

        AssertionUtils.assertTrue(login.loginToPortal("Admin", "admin123"), 
        							"Log into the portal");
        
    }
}