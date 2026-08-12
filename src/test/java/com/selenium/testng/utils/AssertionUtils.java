package com.selenium.testng.utils;

import org.testng.Assert;

import io.restassured.response.Response;

public class AssertionUtils {

	public static void assertEquals(String actual, String expected, String stepMessage) {

		try {
			Assert.assertEquals(actual, expected);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void assertTrue(boolean condition, String stepMessage) {

		try {
			Assert.assertTrue(condition);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void assertFalse(boolean condition, String stepMessage) {

		try {
			Assert.assertFalse(condition);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void fail(String stepMessage) {

		ExtentManager.getTest().fail(stepMessage);
		Assert.fail(stepMessage);
	}

	// Soft asserts

	public static void assertAll() {
		SoftAssertManager.get().assertAll();
	}

	public static void softAssertTrue(boolean condition, String stepMessage) {

		SoftAssertManager.get().assertTrue(condition);

		if (condition) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}

	public static void softAssertFalse(boolean condition, String stepMessage) {

		SoftAssertManager.get().assertFalse(condition);

		if (!condition) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}

	public static void softAssertEquals(String actual, String expected, String stepMessage) {

		SoftAssertManager.get().assertEquals(actual, expected);

		if (actual != null && actual.equals(expected)) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}
	
	public static void softAssertNotEquals(String actual, String expected, String stepMessage) {

		SoftAssertManager.get().assertNotEquals(actual, expected);

		if (actual == null || !actual.equals(expected)) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}

	// For API Assertins
	public static void assertStatusCode(Response response, int expectedStatusCode, String stepMessage) {

		try {
			Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void assertJsonPathEquals(Response response, String jsonPath, String expectedValue,
			String stepMessage) {

		try {
			Assert.assertEquals(response.jsonPath().getString(jsonPath), expectedValue);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void assertResponseTimeLessThan(Response response, long expectedTimeInMillis, String stepMessage) {

		try {
			Assert.assertTrue(response.getTime() < expectedTimeInMillis);
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	public static void assertJsonPathNotNull(Response response, String jsonPath, String stepMessage) {

		try {
			Assert.assertNotNull(response.jsonPath().get(jsonPath));
			ExtentManager.getTest().pass(stepMessage);

		} catch (AssertionError e) {
			ExtentManager.getTest().fail(stepMessage + "<br>" + e.getMessage());
			throw e;
		}
	}

	// For API Assertins - Soft Asserts
	public static void softAssertStatusCode(Response response, int expectedStatusCode, String stepMessage) {

		SoftAssertManager.get().assertEquals(response.getStatusCode(), expectedStatusCode);

		if (response.getStatusCode() == expectedStatusCode) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}

	public static void softAssertJsonPathEquals(Response response, String jsonPath, String expectedValue,
			String stepMessage) {

		String actual = response.jsonPath().getString(jsonPath);

		SoftAssertManager.get().assertEquals(actual, expectedValue);

		if (expectedValue.equals(actual)) {
			ExtentManager.getTest().pass(stepMessage);
		} else {
			ExtentManager.getTest().fail(stepMessage);
		}
	}

}