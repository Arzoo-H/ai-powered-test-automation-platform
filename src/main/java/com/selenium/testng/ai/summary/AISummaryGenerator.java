
package com.selenium.testng.ai.summary;

import java.util.List;

import com.google.gson.Gson;
import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.execution.ExecutionResult;

public class AISummaryGenerator {

	private final AIClient aiClient;
	private final PromptBuilder promptBuilder;

	public AISummaryGenerator(AIClient aiClient, PromptBuilder promptBuilder) {
		this.aiClient = aiClient;
		this.promptBuilder = promptBuilder;
	}

	public ExecutionSummary generate(ExecutionSummary summary, List<ExecutionResult> executionResults) {

		// generate prompt
		String prompt = promptBuilder.buildExecutionSummaryPrompt(summary, executionResults); 

		// generate AI response (whichever AI's class object e.g. GeminiAIClient, we pass to AIClient that will generate response)
		String response = aiClient.generate(prompt); 
		
		// parse the response, the expected JSON response fields are mentioned in the prompt
		AISummaryResponse aiResponse = new Gson().fromJson(response, AISummaryResponse.class);

		// verifying if the response is not proper and null
		// if any is null, the response is incomplete and cannot be used to generate a reliable execution summary.
		if (aiResponse == null
		        || aiResponse.summary == null || aiResponse.summary.isBlank()
		        || aiResponse.overallHealth == null || aiResponse.overallHealth.isBlank()
		        || aiResponse.keyObservations == null || aiResponse.keyObservations.isBlank()) {

		    throw new IllegalStateException(
		            "Gemini returned an incomplete execution summary.");
		}

		summary.setSummary(aiResponse.summary);
		summary.setOverallHealth(aiResponse.overallHealth);
		summary.setKeyObservations(aiResponse.keyObservations);

		return summary;
	}

	private static class AISummaryResponse {
		String summary;
		String overallHealth;
		String keyObservations;
	}
}