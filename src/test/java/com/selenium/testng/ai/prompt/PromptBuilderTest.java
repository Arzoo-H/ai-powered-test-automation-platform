package com.selenium.testng.ai.prompt;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.testng.execution.ExecutionResult;

public class PromptBuilderTest {

	@Test
	public void shouldBuildFailureAnalysisPrompt() {

		// Data generated from execution
		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("loginTest");
		executionResult.setTestClass("LoginTest");
		executionResult.setStatus("FAILED");
		executionResult.setDuration(2500);
		executionResult.setBrowser("Chrome");
		executionResult.setEnvironment("QA");
		executionResult.setUrl("https://example.com/login");
		executionResult.setExceptionType("NoSuchElementException");
		executionResult.setExceptionMessage("Unable to locate element");
		executionResult.setFailedStep("Enter username");
		executionResult.setAction("sendKeys");
		executionResult.setLocator("#username");
		executionResult.setStackTrace("org.openqa.selenium.NoSuchElementException...");

		// Prepping Prompt to send to AI for analysis
		PromptBuilder promptBuilder = new PromptBuilder();
		String prompt = promptBuilder.buildFailureAnalysisPrompt(executionResult);

		// Assertions
		Assert.assertNotNull(prompt);

		Assert.assertTrue(prompt.contains("loginTest"));
		Assert.assertTrue(prompt.contains("NoSuchElementException"));
		Assert.assertTrue(prompt.contains("Unable to locate element"));
		Assert.assertTrue(prompt.contains("#username"));
		Assert.assertTrue(prompt.contains("Chrome"));
		Assert.assertTrue(prompt.contains("QA"));

		Assert.assertTrue(prompt.contains("failureType"));
		Assert.assertTrue(prompt.contains("rootCause"));
		Assert.assertTrue(prompt.contains("confidence"));
		Assert.assertTrue(prompt.contains("possibleCauses"));
		Assert.assertTrue(prompt.contains("suggestedAction"));
		Assert.assertTrue(prompt.contains("evidence"));
	}
}