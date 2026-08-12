package com.selenium.testng.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.selenium.testng.enums.Menu;
import com.selenium.testng.utils.LocatorUtil;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.web.base.BasePage;
import com.selenium.testng.web.locators.DashboardPageLocators;

public class DashboardPage extends BasePage {
	
	private static final Logger log = LoggerUtil.getLogger(DashboardPage.class);

    public DashboardPage(WebDriver driver) {
        super(driver);
    }
  
    
	// ==========================================
	// FUNCTIONS
	// ==========================================
    
    /**
     * Clicks on the menu - Performance, Admin, etc
     * @param menuName [String] - Name of the menu item you want to click
     * @return true if menu mentioned is clicked
     * @author Arzoo Hingorani
     */
	public boolean clickOnMenu(Menu menuName) {
		By menuItem = LocatorUtil.xpath(DashboardPageLocators.MENU_ITEM, menuName.getValue());
		try {
			if (actions.waitForClickable(menuItem) != null) {
				actions.click(menuItem);
				log.info("Clicked Menu '{}'", menuName.getValue());
				return true;
			}
		} catch (Exception e) {
			log.error("Could not click on Menu '{}'", menuName.getValue(), e);
		}
		return false;
	}
}