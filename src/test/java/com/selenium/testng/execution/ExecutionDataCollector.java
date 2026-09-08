package com.selenium.testng.execution;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestResult;

import com.selenium.testng.context.TestContext;
import com.selenium.testng.web.driverfactory.DriverFactory;

/**
 * Convert raw TestNG execution information 'ITestResult' into our standardized
 * ExecutionResult.
 * 
 * @author Arzoo Hingorani
 */
public class ExecutionDataCollector {

	private ExecutionDataCollector() {
		// Utility class
	}

	public static ExecutionResult collect(ITestResult result, String screenshotPath) {

		ExecutionResult executionResult = new ExecutionResult();
		executionResult.setTestName(result.getMethod().getMethodName());
		executionResult.setTestClass(result.getTestClass().getName());
		executionResult.setStatus(getStatus(result));
		executionResult.setDuration(result.getEndMillis() - result.getStartMillis());

		// test env information
		if (TestContext.getContext().getBrowser() != null) {
			executionResult.setBrowser(TestContext.getContext().getBrowser());
		}

		if (TestContext.getContext().getEnv() != null) {
			executionResult.setEnvironment(TestContext.getContext().getEnv());
		}
		
		if (DriverFactory.getDriver() != null) {
		    executionResult.setUrl(
		            DriverFactory.getDriver().getCurrentUrl());
		}

		// exception information
		if (result.getThrowable() != null) {

			Throwable throwable = result.getThrowable();

			executionResult.setExceptionType(throwable.getClass().getName());
			executionResult.setExceptionMessage(throwable.getMessage());
			executionResult.setStackTrace(getStackTrace(throwable));
		}

		return executionResult;
	}

	private static String getStatus(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			return "PASSED";
		}

		if (result.getStatus() == ITestResult.FAILURE) {
			return "FAILED";
		}

		if (result.getStatus() == ITestResult.SKIP) {
			return "SKIPPED";
		}

		return "UNKNOWN";
	}

	private static String getStackTrace(Throwable throwable) {

		// A place in memory where Java can write text, instead of writing it to the console.
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		throwable.printStackTrace(printWriter); // throwable does not have overload - printStackTrace(StringWriter s), it has for PrintWriter
		return stringWriter.toString();
	}
}
