package com.selenium.testng.ai.client;

import org.testng.annotations.Test;

public class GeminiAIClientTest {

	@Test
	public void shouldConnectToGemini() {

		GeminiAIClient geminiAIClient = new GeminiAIClient();

		String response = geminiAIClient.generate("Respond with exactly: Gemini connection successful");

		System.out.println("Gemini Response:");
		System.out.println(response);
	}
}