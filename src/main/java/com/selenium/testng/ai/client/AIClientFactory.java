package com.selenium.testng.ai.client;

import com.selenium.testng.config.ConfigManager;

public final class AIClientFactory {

    private AIClientFactory() {
        // Utility class
    }

	public static AIClient create() {

		String provider = ConfigManager.getInstance().getExecutionSummaryProvider();

		if ("FAKE".equalsIgnoreCase(provider)) {
			return new FakeAIClient();
		}

		if ("GEMINI".equalsIgnoreCase(provider)) {
			return new GeminiAIClient();
		}

		throw new IllegalArgumentException("Unsupported AI provider: " + provider);
	}
}