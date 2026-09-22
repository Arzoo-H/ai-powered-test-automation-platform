package com.selenium.testng.ai.failure;

import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.client.GeminiAIClient;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.config.ConfigManager;

public class FailureAnalyzerFactory {

	private FailureAnalyzerFactory() {
        // Utility class
	}

	public static FailureAnalyzer create() {

		String analyzerType = ConfigManager.getInstance().getAIFailureAnalyzer();

		if ("GEMINI".equalsIgnoreCase(analyzerType)) {

			AIClient aiClient = new GeminiAIClient();
			PromptBuilder promptBuilder = new PromptBuilder();
			return new GeminiFailureAnalyzer(aiClient, promptBuilder);
		}

		if ("RULE_BASED".equalsIgnoreCase(analyzerType)) {

			return new RuleBasedFailureAnalyzer();
		}

		throw new IllegalArgumentException("Unsupported AI failure analyzer: " + analyzerType);
	}
}