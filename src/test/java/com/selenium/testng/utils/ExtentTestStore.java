package com.selenium.testng.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentTest;
import com.selenium.testng.execution.ExecutionResult;

public class ExtentTestStore {

	private static final Map<ExecutionResult, ExtentTest> extentTests = new ConcurrentHashMap<>();

	private ExtentTestStore() {
		// Utility class
	}

	public static void add(ExecutionResult executionResult, ExtentTest extentTest) {

		extentTests.put(executionResult, extentTest);
	}

	public static ExtentTest get(ExecutionResult executionResult) {

		return extentTests.get(executionResult);
	}

	public static void clear() {

		extentTests.clear();
	}
}