package com.selenium.testng.ai.failure;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.client.GeminiAIClient;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.execution.ExecutionResult;

public class GeminiFailureAnalyzerTest {

	@Test
	public void shouldAnalyzeFailureUsingGemini() {

		// Dummy data for execution failure
		ExecutionResult executionResult = new ExecutionResult();

		executionResult.setTestName("loginTest");
		executionResult.setTestClass("LoginTest");
		executionResult.setStatus("FAILED");
		executionResult.setDuration(2500);
		executionResult.setBrowser("Chrome");
		executionResult.setEnvironment("QA");
		executionResult.setUrl("https://example.com/login");
		executionResult.setExceptionType("org.openqa.selenium.NoSuchElementException");
		executionResult.setExceptionMessage("Unable to locate element: #username");
		executionResult.setFailedStep("Enter username");
		executionResult.setAction("sendKeys");
		executionResult.setLocator("#username");
		executionResult.setStackTrace("org.openqa.selenium.NoSuchElementException: " + "Unable to locate element");

		// Send ExecutionResult for analysis to Gemini
		AIClient aiClient = new GeminiAIClient();
		PromptBuilder promptBuilder = new PromptBuilder();

		GeminiFailureAnalyzer analyzer = new GeminiFailureAnalyzer(aiClient, promptBuilder);
		FailureAnalysis analysis = analyzer.analyze(executionResult);

		Assert.assertNotNull(analysis);

	    Assert.assertEquals(analysis.getFailureType(), "LOCATOR");
	    Assert.assertNotNull(analysis.getRootCause());
	    Assert.assertTrue(analysis.getConfidence() > 0.0);
	    Assert.assertTrue(analysis.getConfidence() <= 1.0);

	    Assert.assertNotNull(analysis.getExplanation());
	    Assert.assertNotNull(analysis.getPossibleCauses());
	    Assert.assertFalse(analysis.getPossibleCauses().isEmpty());

	    Assert.assertNotNull(analysis.getSuggestedAction());
	    Assert.assertNotNull(analysis.getEvidence());
	    Assert.assertFalse(analysis.getEvidence().isEmpty());
	}
}