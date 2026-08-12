package com.selenium.testng.web.base;

import org.openqa.selenium.WebDriver;

import com.selenium.testng.utils.ElementActions;

public class BasePage {

    protected WebDriver driver;
    protected ElementActions actions;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        this.actions = new ElementActions(driver);
    }
    
    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void navigateBack() {
        driver.navigate().back();
    }

    protected void navigateForward() {
        driver.navigate().forward();
    }
}
