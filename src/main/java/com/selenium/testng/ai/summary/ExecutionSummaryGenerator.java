package com.selenium.testng.ai.summary;

import java.util.List;

import com.selenium.testng.execution.ExecutionHistory;
import com.selenium.testng.execution.ExecutionHistoryBuilder;
import com.selenium.testng.execution.ExecutionResult;

public class ExecutionSummaryGenerator {

	public ExecutionSummary generateSummary(List<ExecutionResult> results) {

		List<ExecutionHistory> histories = ExecutionHistoryBuilder.build(results);
		
		int totalTests = histories.size(); // unique total testcase count

		int passedTests = 0;
		int failedTests = 0;
		int skippedTests = 0;

		int totalAttempts = results.size(); // overall executions count
		int retryAttempts = 0;
		int testsRetried = 0;
		int failedAttempts = 0;
		int recoveredAfterRetry = 0;

		for (ExecutionHistory history : histories) { //running on values that is List<ExecutionResult>

		    ExecutionResult finalAttempt = history.getFinalAttempt();

		    if (finalAttempt == null) {
		        continue; // Skip the remaining processing for this List<ExecutionResult> group and move to the next group in the loop.
		    }

		    failedAttempts += (int) history.getFailedAttempts();

		    if (history.wasRetried()) {
		        testsRetried++;
		    }

		    retryAttempts += history.getRetryAttempts();

			if (history.isPassed()) {

				passedTests++;

				if (history.recoveredAfterRetry()) {
					recoveredAfterRetry++;
				}

			} else if (history.isFailed()) {

				failedTests++;

			} else if (history.isSkipped()) {

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