package com.selenium.testng.execution;

import com.selenium.testng.ai.failure.FailureAnalysis;

public class ExecutionResult {

	private String testName;
	private String testClass;
	private String status;
	private long duration;
	private String browser;
	private String environment;
	private String url;
	private String exceptionType;
	private String exceptionMessage;
	private String stackTrace;
	private String failedStep;
	private String action;
	private String locator;
	private FailureAnalysis failureAnalysis;
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getFailedStep() {
		return failedStep;
	}

	public void setFailedStep(String failedStep) {
		this.failedStep = failedStep;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}
	
	public FailureAnalysis getFailureAnalysis() {
		return failureAnalysis;
	}

	public void setFailureAnalysis(FailureAnalysis failureAnalysis) {
		this.failureAnalysis = failureAnalysis;
	}

}
