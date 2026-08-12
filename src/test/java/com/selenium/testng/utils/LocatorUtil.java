package com.selenium.testng.utils;

import org.openqa.selenium.By;

public class LocatorUtil {

	/*
	 * Sample use : actions.click(LocatorUtil.xpath(USER_ACTION,
									                "John",
									                "Delete"));
	 */
    public static By xpath(String locator, Object... values) {

        return By.xpath(String.format(locator, values));
    }
}
