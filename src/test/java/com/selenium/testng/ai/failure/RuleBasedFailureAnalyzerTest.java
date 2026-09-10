package com.selenium.testng.ai.failure;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.testng.execution.ExecutionResult;

public class RuleBasedFailureAnalyzerTest {

	@Test
	public void shouldIdentifyLocatorFailure() {

		// Set execution values
		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("loginTest");
		executionResult.setExceptionType("org.openqa.selenium.NoSuchElementException");
		executionResult.setExceptionMessage("Unable to locate element");
		executionResult.setAction("click");
		executionResult.setLocator("//button[@id='login']");

		// Analyze the above execution results via the RuleBasedFailureAnalyzer class we
		// have written
		FailureAnalyzer analyzer = new RuleBasedFailureAnalyzer();

		FailureAnalysis analysis = analyzer.analyze(executionResult);

		// Assert
		Assert.assertEquals(analysis.getFailureType(), "LOCATOR");
		Assert.assertEquals(analysis.getRootCause(), "The expected element could not be located.");
		Assert.assertEquals(analysis.getConfidence(), 0.90);
		Assert.assertNotNull(analysis.getPossibleCauses());
		Assert.assertFalse(analysis.getPossibleCauses().isEmpty());
		Assert.assertNotNull(analysis.getSuggestedAction());
		Assert.assertNotNull(analysis.getEvidence());
	}

	@Test
	public void shouldIdentifyTimeoutFailure() {

		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("loginTest");
		executionResult.setExceptionType("org.openqa.selenium.TimeoutException");
		executionResult.setExceptionMessage("Expected condition failed");
		executionResult.setFailedStep("Wait for login button");
		executionResult.setAction("click");
		executionResult.setLocator("//button[@id='login']");

		FailureAnalyzer analyzer = new RuleBasedFailureAnalyzer();

		FailureAnalysis analysis = analyzer.analyze(executionResult);

		Assert.assertEquals(analysis.getFailureType(), "TIMEOUT");
		Assert.assertEquals(analysis.getConfidence(), 0.85);
		Assert.assertNotNull(analysis.getPossibleCauses());
		Assert.assertFalse(analysis.getPossibleCauses().isEmpty());
		Assert.assertNotNull(analysis.getSuggestedAction());
		Assert.assertNotNull(analysis.getEvidence());
	}

	@Test
	public void shouldIdentifyAssertionFailure() {

		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("verifyLoginTitle");
		executionResult.setExceptionType("java.lang.AssertionError");
		executionResult.setExceptionMessage("Expected: Home but found: Dashboard");
		executionResult.setFailedStep("Verify page title");

		FailureAnalyzer analyzer = new RuleBasedFailureAnalyzer();

		FailureAnalysis analysis = analyzer.analyze(executionResult);

		Assert.assertEquals(analysis.getFailureType(), "ASSERTION");
		Assert.assertEquals(analysis.getConfidence(), 0.95);
		Assert.assertNotNull(analysis.getPossibleCauses());
		Assert.assertFalse(analysis.getPossibleCauses().isEmpty());
		Assert.assertNotNull(analysis.getSuggestedAction());
		Assert.assertNotNull(analysis.getEvidence());
	}

	@Test
	public void shouldIdentifyUnknownFailure() {

		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("unknownFailureTest");
		executionResult.setExceptionType("java.lang.IllegalStateException");
		executionResult.setExceptionMessage("Unexpected application state");

		FailureAnalyzer analyzer = new RuleBasedFailureAnalyzer();

		FailureAnalysis analysis = analyzer.analyze(executionResult);

		Assert.assertEquals(analysis.getFailureType(), "UNKNOWN");
		Assert.assertEquals(analysis.getConfidence(), 0.30);
		Assert.assertNotNull(analysis.getPossibleCauses());
		Assert.assertFalse(analysis.getPossibleCauses().isEmpty());
		Assert.assertNotNull(analysis.getSuggestedAction());
		Assert.assertNotNull(analysis.getEvidence());
	}
	
	@Test
	public void shouldHandleMissingExceptionType() {

	    ExecutionResult executionResult = new ExecutionResult();
	    executionResult.setTestName("unknownFailureTest");
	    executionResult.setExceptionType(null);

	    FailureAnalyzer analyzer = new RuleBasedFailureAnalyzer();

	    FailureAnalysis analysis = analyzer.analyze(executionResult);

	    Assert.assertEquals(analysis.getFailureType(), "UNKNOWN");
	}
}