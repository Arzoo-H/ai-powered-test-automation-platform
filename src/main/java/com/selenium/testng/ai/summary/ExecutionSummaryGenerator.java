package com.selenium.testng.ai.summary;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.selenium.testng.execution.ExecutionResult;

public class ExecutionSummaryGenerator {

	public ExecutionSummary generateSummary(List<ExecutionResult> results) {

		/*
		 * Each ExecutionResult represents one execution attempt. Grouping by
		 * invocationId allows us to treat retries as one logical test.
		 * 
		 * invocationId becomes the key 
		 * and corresponding individual execution becomes list of ExecutionResult
		 * so, if one testcase ran twice due to retry logic, then there will be two ExecutionResult logged against one invocationId
		 */
		Map<String, List<ExecutionResult>> testsByInvocation = results.stream()
																	  .collect(Collectors.groupingBy(ExecutionResult::getInvocationId));

		int totalTests = testsByInvocation.size(); // unique total testcase count

		int passedTests = 0;
		int failedTests = 0;
		int skippedTests = 0;

		int totalAttempts = results.size(); // overall executions count
		int retryAttempts = 0;
		int testsRetried = 0;
		int failedAttempts = 0;
		int recoveredAfterRetry = 0;

		for (List<ExecutionResult> attempts : testsByInvocation.values()) { // not map as it is running only on values that is List<ExecutionResult>

			// Calculate failedAttempts - Count of how many times the testcase failed
			long failedAttemptCount = attempts.stream()
											  .filter(result -> "FAILED".equalsIgnoreCase(result.getStatus()))
											  .count();

			failedAttempts += (int) failedAttemptCount;

			// Calculate testsRetried - A retry was scheduled if any attempt in this invocation had retryScheduled = true
			boolean wasRetried = attempts.stream()
										 .anyMatch(ExecutionResult::isRetryScheduled); // on all objects of list, call isRetryScheduled() method.

			if (wasRetried) {
				testsRetried++;
			}

			/*
			 * Calculate retryAttempts - Every failed attempt that scheduled another attempt represents a retry.
			 *
			 * Example: FAILED + retryScheduled=true | FAILED + retryScheduled=true | PASSED = 2 retry attempts
			 */
			retryAttempts += (int) attempts.stream()
										   .filter(ExecutionResult::isRetryScheduled) // filter() keeps the objects where the condition is true
										   .count();

			/*
			 * The final attempt determines the test result.
			 * What reduce() does -> Whenever you give me two elements, throw away the first and keep the second
			 * 
			 * Example: FAILED + retryScheduled=true | FAILED + retryScheduled=true | PASSED = 2 retry attempts
			 * then it will keep second one and then it will run through second and third and choose third entry
			 * And null when there are 0 attempts
			 */
			ExecutionResult finalAttempt = attempts.stream()
												   .reduce((first, second) -> second) // reduce() combines multiple elements into one element
												   .orElse(null);

			if (finalAttempt == null) {
				continue; // Skip the remaining processing for this List<ExecutionResult> group and move to the next group in the loop.
			}

			// Whether after 0 or 3 attempts, did the testcase PASS/FAIL
			String finalStatus = finalAttempt.getStatus();

			if ("PASSED".equalsIgnoreCase(finalStatus)) {
				passedTests++;

				if (wasRetried) {
					recoveredAfterRetry++; // the testcase passed after it was retried
				}

			} else if ("FAILED".equalsIgnoreCase(finalStatus)) {
				failedTests++;

			} else if ("SKIPPED".equalsIgnoreCase(finalStatus)) {
				skippedTests++;
			}
		} // end of loop

		long totalDuration = results.stream()
									.mapToLong(ExecutionResult::getDuration) // iterate over all objects in List<ExecutionResult> & get the duration for each
									.sum(); // add them all

		return new ExecutionSummary(totalTests, passedTests, failedTests, skippedTests, totalDuration, null, null, null,
				totalAttempts, retryAttempts, testsRetried, failedAttempts, recoveredAfterRetry);
	}
}