package com.selenium.testng.execution;

import java.util.List;

public class ExecutionHistory {

	private final String invocationId;
	private final List<ExecutionResult> attempts;

	// for a testcase, 123, invocationIs is classname123something, if it has retried twice, 
	// List<ExecutionResult> attempts would have ExecutionResult 1 - Fail, ExecutionResult 2 - Pass
	public ExecutionHistory(String invocationId, List<ExecutionResult> attempts) {

		this.invocationId = invocationId;
		this.attempts = attempts;
	}

	public String getInvocationId() {
		return invocationId;
	}

	public List<ExecutionResult> getAttempts() {
		return attempts;
	}

	// Calculations
	
	/*
	 * The final attempt determines the test result.
	 * What reduce() does -> Whenever you give me two elements, throw away the first and keep the second
	 * 
	 * Example: FAILED + retryScheduled=true | FAILED + retryScheduled=true | PASSED = 2 retry attempts
	 * then it will keep second one and then it will run through second and third and choose third entry
	 * And null when there are 0 attempts
	 */
	public ExecutionResult getFinalAttempt() {

		if (attempts == null || attempts.isEmpty()) {
			return null;
		}

		return attempts.get(attempts.size() - 1);
	}

	public int getTotalAttempts() {
		return attempts == null ? 0 : attempts.size();
	}
	
	// Calculate failedAttempts - Count of how many times the testcase failed
	public int getFailedAttempts() {

		if (attempts == null) {
			return 0;
		}

		// cating long output to int to match return type
		return (int) attempts.stream()
						.filter(result -> "FAILED".equalsIgnoreCase(result.getStatus()))
						.count();
	}

	/*
	 * Calculate retryAttempts - Every failed attempt that scheduled another attempt represents a retry.
	 *
	 * Example: FAILED + retryScheduled=true | FAILED + retryScheduled=true | PASSED = 2 retry attempts
	 */
	public long getRetryAttempts() {

		if (attempts == null) {
			return 0;
		}

		// on all objects of list, call isRetryScheduled() method. filter() keeps the objects where the condition is true
		return attempts.stream()
						.filter(ExecutionResult::isRetryScheduled) 
						.count();
	}

	// Calculate testsRetried - A retry was scheduled if any attempt in this invocation had retryScheduled = true
	public boolean wasRetried() {
		return getRetryAttempts() > 0;
	}

	public boolean recoveredAfterRetry() {

		ExecutionResult finalAttempt = getFinalAttempt();

		return wasRetried() && finalAttempt != null && "PASSED".equalsIgnoreCase(finalAttempt.getStatus());
	}
	
	public boolean isPassed() {

		ExecutionResult finalAttempt = getFinalAttempt();

		return finalAttempt != null && "PASSED".equalsIgnoreCase(finalAttempt.getStatus());
	}

	public boolean isFailed() {

		ExecutionResult finalAttempt = getFinalAttempt();

		return finalAttempt != null && "FAILED".equalsIgnoreCase(finalAttempt.getStatus());
	}

	public boolean isSkipped() {

		ExecutionResult finalAttempt = getFinalAttempt();

		return finalAttempt != null && "SKIPPED".equalsIgnoreCase(finalAttempt.getStatus());
	}
}