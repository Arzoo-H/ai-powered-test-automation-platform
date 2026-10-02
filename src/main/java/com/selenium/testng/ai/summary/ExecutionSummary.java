package com.selenium.testng.ai.summary;

/**
 * 
 * @author Arzoo Hingorani
 * 
 * Output would look something as,
 * 
 	Total Tests      : 20
	Passed           : 15
	Failed           : 3
	Skipped          : 2
	Total Duration   : 48.2 seconds
	
	Overall Health   : NEEDS ATTENTION
	
	Summary:
	15 of 20 tests passed. Three tests failed and two were skipped.
	The failures are concentrated around login and checkout functionality.
	
	Key Observations:
	- Login failures account for 2 of the 3 failures.
	- One failure appears to be locator-related.
	- Checkout test was skipped.
	- Overall pass rate was 75%.
 *
 */
public class ExecutionSummary {

	private int totalTests;
	private int passedTests;
	private int failedTests;
	private int skippedTests;
	
	private int totalAttempts;
	private int retryAttempts;
	private int testsRetried;
	private int failedAttempts;
	private int recoveredAfterRetry;

	private long totalDuration;

	private String summary;
	private String overallHealth;
	private String keyObservations;

	public ExecutionSummary() {
	}

	public ExecutionSummary(int totalTests, int passedTests, int failedTests, int skippedTests, long totalDuration,
			String summary, String overallHealth, String keyObservations, int totalAttempts, int retryAttempts,
			int testsRetried, int failedAttempts, int recoveredAfterRetry) {

		this.totalTests = totalTests;
		this.passedTests = passedTests;
		this.failedTests = failedTests;
		this.skippedTests = skippedTests;
		this.totalDuration = totalDuration;
		this.summary = summary;
		this.overallHealth = overallHealth;
		this.keyObservations = keyObservations;

		this.totalAttempts = totalAttempts;
		this.retryAttempts = retryAttempts;
		this.testsRetried = testsRetried;
		this.failedAttempts = failedAttempts;
		this.recoveredAfterRetry = recoveredAfterRetry;
	}

	public int getTotalTests() {
		return totalTests;
	}

	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}

	public int getPassedTests() {
		return passedTests;
	}

	public void setPassedTests(int passedTests) {
		this.passedTests = passedTests;
	}

	public int getFailedTests() {
		return failedTests;
	}

	public void setFailedTests(int failedTests) {
		this.failedTests = failedTests;
	}

	public int getSkippedTests() {
		return skippedTests;
	}

	public void setSkippedTests(int skippedTests) {
		this.skippedTests = skippedTests;
	}
	
	public int getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(int totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public int getRetryAttempts() {
		return retryAttempts;
	}

	public void setRetryAttempts(int retryAttempts) {
		this.retryAttempts = retryAttempts;
	}

	public int getTestsRetried() {
		return testsRetried;
	}

	public void setTestsRetried(int testsRetried) {
		this.testsRetried = testsRetried;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public int getRecoveredAfterRetry() {
		return recoveredAfterRetry;
	}

	public void setRecoveredAfterRetry(int recoveredAfterRetry) {
		this.recoveredAfterRetry = recoveredAfterRetry;
	}

	public long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOverallHealth() {
		return overallHealth;
	}

	public void setOverallHealth(String overallHealth) {
		this.overallHealth = overallHealth;
	}

	public String getKeyObservations() {
		return keyObservations;
	}

	public void setKeyObservations(String keyObservations) {
		this.keyObservations = keyObservations;
	}
}