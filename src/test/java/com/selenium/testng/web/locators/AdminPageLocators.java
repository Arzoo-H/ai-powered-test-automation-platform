package com.selenium.testng.web.locators;

public class AdminPageLocators {

    // Buttons
    public static final String DELETE_SELECTED_BTN = "//button[contains(@class, 'label-danger')]";
    public static final String MODAL_CANCEL_BTN = "//div[contains(@class, 'modal-footer')]/button[contains(@class, 'ghost')]";

	// Checkboxes
    public static final String USERNAME_CHK = "//div[@class='oxd-table-card'][.//div[text()='%s'] and .//div[text()='%s']]//span[contains(@class,'checkbox-input')]";

    
}
