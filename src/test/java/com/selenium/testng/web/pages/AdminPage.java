package com.selenium.testng.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.selenium.testng.utils.LocatorUtil;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.web.base.BasePage;
import com.selenium.testng.web.locators.AdminPageLocators;

public class AdminPage extends BasePage {
	
	private static final Logger log = LoggerUtil.getLogger(AdminPage.class);

    public AdminPage(WebDriver driver) {
        super(driver);
    }
  
    By DeleteSelectedBtn = By.xpath(AdminPageLocators.DELETE_SELECTED_BTN);
    By ModalCancelBn = By.xpath(AdminPageLocators.MODAL_CANCEL_BTN);
    
	// ==========================================
	// FUNCTIONS
	// ==========================================
    
	/**
	 * Selects the mentioned first user having the employee role and a status match
	 * We get elements to fetch all user matching with mentioned details 
	 * @param role [String] : Employee's role
	 * @param status [String] : Their status
	 * @return true if user is selected
	 * @author Arzoo Hingorani
	 */
	public boolean selectFirstUser(String role, String status) {
		By usernameChk = LocatorUtil.xpath(AdminPageLocators.USERNAME_CHK, role, status);
		try {
			List<WebElement> usernamesChk = actions.getElements(usernameChk);
			if (usernamesChk != null) {
				actions.scrollIntoView(usernamesChk.get(0));
				actions.click(usernamesChk.get(0));
				log.info("First employee having role {} and status {} is selected", role, status);
				
				if(actions.getAttribute(usernamesChk.get(0), "class").contains("focus"))
					return true;
			}
		} catch (Exception e) {
			log.error("Could not select employee having role {} whose status is {}", role, status, e);
		}
		return false;
	}
	
	/**
	 * Click on Delete Selected user button
	 * @return true if button is clicked
	 * @author Arzoo Hingorani
	 */
	public boolean clickOnDeleteSelectedUser() {
		try {
			if (actions.waitForClickable(DeleteSelectedBtn) != null) {
				actions.scrollIntoView(DeleteSelectedBtn);
				actions.click(DeleteSelectedBtn);
				log.info("Clicked on Delete Selected button");
				return true;
			}
		} catch (Exception e) {
			log.error("Could not click on Delete Selected button", e);
		}
		return false;
	}
	
	/**
	 * Click on Cancel button on the Delete selected user popup
	 * @return true if button is clicked
	 * @author Arzoo Hingorani
	 */
	public boolean clickCancelBtnOnDeleteUserModal() {
		try {
			if (actions.waitForClickable(ModalCancelBn) != null) {
				actions.click(ModalCancelBn);
				log.info("Clicked on Cancel button on Modal popup");
				return true;
			}
		} catch (Exception e) {
			log.error("Could not click on Cancel button on Modal popup", e);
		}
		return false;
	}
}