package com.selenium.testng.utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertManager {

	private static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

	public static SoftAssert get() {
		return softAssert.get();
	}

	public static void reset() {
		softAssert.remove();
	}
}