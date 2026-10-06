
package com.selenium.testng.ai.summary;

import java.util.List;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.execution.ExecutionHistory;
import com.selenium.testng.execution.ExecutionHistoryBuilder;
import com.selenium.testng.execution.ExecutionResult;
import com.selenium.testng.utils.LoggerUtil;

public class AISummaryGenerator {

	private static final Logger log = LoggerUtil.getLogger(AISummaryGenerator.class);

	private final AIClient aiClient;
	private final PromptBuilder promptBuilder;

	public AISummaryGenerator(AIClient aiClient, PromptBuilder promptBuilder) {
		this.aiClient = aiClient;
		this.promptBuilder = promptBuilder;
	}

	public ExecutionSummary generate(ExecutionSummary summary, List<ExecutionResult> executionResults) {

		log.info("Generating AI execution summary for {} execution results", executionResults.size());
		
		List<ExecutionHistory> executionHistories = ExecutionHistoryBuilder.build(executionResults);
		log.info("Built {} execution histories for AI summary", executionHistories.size());
		
		// generate prompt
		String prompt = promptBuilder.buildExecutionSummaryPrompt(summary, executionHistories); 
	    log.info("AI execution summary prompt generated");

		// generate AI response (whichever AI's class object e.g. GeminiAIClient, we pass to AIClient that will generate response)
		String response = aiClient.generate(prompt); 
	    log.info("AI execution summary response received");
		
		// parse the response, the expected JSON response fields are mentioned in the prompt
		AISummaryResponse aiResponse = new Gson().fromJson(response, AISummaryResponse.class);

		// verifying if the response is not proper and null
		// if any is null, the response is incomplete and cannot be used to generate a reliable execution summary.
		if (aiResponse == null
		        || aiResponse.summary == null || aiResponse.summary.isBlank()
		        || aiResponse.overallHealth == null || aiResponse.overallHealth.isBlank()
		        || aiResponse.keyObservations == null || aiResponse.keyObservations.isBlank()) {

	        log.error("AI returned an incomplete execution summary");

		    throw new IllegalStateException(
		            "Gemini returned an incomplete execution summary.");
		}

		log.info("AI execution summary parsed successfully | Health: {}", aiResponse.overallHealth);
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