
package com.selenium.testng.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	public static final String RETRY_SCHEDULED_ATTRIBUTE = "retryScheduled";

	private int count = 0;
	private final int maxRetry = 2;

	@Override
	public boolean retry(ITestResult result) {

		boolean shouldRetry = count < maxRetry;

		// Record whether this failed attempt will be retried.
		result.setAttribute(RETRY_SCHEDULED_ATTRIBUTE, shouldRetry);

		if (shouldRetry) {
			count++;
			
			System.out.println(
				    "[RetryAnalyzer] Test: " + result.getName()
				    + " | Status: " + result.getStatus()
				    + " | Throwable: " + result.getThrowable()
				    + " | Retry scheduled: " + shouldRetry
				);
			return true;
		}

		System.out.println(
			    "[RetryAnalyzer] Test: " + result.getName()
			    + " | Status: " + result.getStatus()
			    + " | Throwable: " + result.getThrowable()
			    + " | Retry scheduled: " + shouldRetry
			);
		return false;
	}
}