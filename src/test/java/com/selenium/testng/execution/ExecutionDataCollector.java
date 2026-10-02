package com.selenium.testng.execution;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.testng.ITestResult;

import com.selenium.testng.context.TestContext;
import com.selenium.testng.utils.RetryAnalyzer;
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
		executionResult.setInvocationId(buildInvocationId(result));
		
		boolean retryScheduled = Boolean.TRUE.equals(
			    result.getAttribute(RetryAnalyzer.RETRY_SCHEDULED_ATTRIBUTE));
		executionResult.setRetryScheduled(retryScheduled);

		executionResult.setStatus(getStatus(result, retryScheduled));
		executionResult.setDuration(result.getEndMillis() - result.getStartMillis());
		
		// test env information
		TestContext testContext = TestContext.getContext();
		if (testContext != null) {
			if (testContext.getBrowser() != null) {
				executionResult.setBrowser(testContext.getBrowser());
			}

			if (testContext.getEnv() != null) {
				executionResult.setEnvironment(testContext.getEnv());
			}

			if (testContext.getLastAction() != null) {
				executionResult.setAction(testContext.getLastAction());
			}

			if (testContext.getLastLocator() != null) {
				executionResult.setLocator(testContext.getLastLocator());
			}

			if (testContext.getLastElementDiagnostics() != null) {
				executionResult.setElementDiagnostics(testContext.getLastElementDiagnostics());
			}
		}

		if (DriverFactory.getDriver() != null) {
			executionResult.setUrl(DriverFactory.getDriver().getCurrentUrl());
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

	private static String buildInvocationId(ITestResult result) {

	    String testClass = result.getTestClass().getName();
	    String testName = result.getMethod().getMethodName();
	    String parameters = Arrays.deepToString(result.getParameters());

	    return testClass + "#" + testName + "#" + parameters;
	}
	    
	private static String getStatus(ITestResult result, boolean retryScheduled) {

		// TestNG may mark an attempt as skipped after a retry is scheduled.
		// Preserve its actual failure classification for AI analysis.
		if (retryScheduled && result.getThrowable() != null) {
			return "FAILED";
		}

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
