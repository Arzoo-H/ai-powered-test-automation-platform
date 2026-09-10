package com.selenium.testng.ai.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.selenium.testng.config.ConfigManager;

public class GeminiAIClient implements AIClient {

	private final String apiKey;

	public GeminiAIClient() {
		this.apiKey = System.getenv("GEMINI_API_KEY");
	}

	@Override
	public String generate(String prompt) {

		try {
			
			ConfigManager config = ConfigManager.getInstance();

			String baseUrl = config.getGeminiApiUrl();
			String model = config.getGeminiModel();
			String endpoint = baseUrl + "/models/" + model + ":generateContent";

			String requestBody = """
					{
					  "contents": [
					    {
					      "parts": [
					        {
					          "text": "%s"
					        }
					      ]
					    }
					  ]
					}
					""".formatted(prompt.replace("\"", "\\\""));

			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(endpoint))
					.header("Content-Type", "application/json")
					.header("x-goog-api-key", apiKey)
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();
			
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Parse the response
			/*
			 * Response would be of format
			 	{
				  "candidates": [
				    {
				      "content": {
				        "parts": [
				          {
				            "text": "..."
				          }
				        ]
				      }
				    }
				  ]
				}
			 */
			JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();

			JsonArray candidates = responseJson.getAsJsonArray("candidates");

			JsonObject content = candidates.get(0).getAsJsonObject()
						    		.getAsJsonObject("content");

			JsonArray parts = content.getAsJsonArray("parts");

			String text = parts.get(0).getAsJsonObject()
					        .get("text").getAsString();
			
			text = text.trim();

			if (text.startsWith("```json")) {
			    text = text.substring(7);
			}

			if (text.endsWith("```")) {
			    text = text.substring(0, text.length() - 3);
			}

			return text.trim();

		} catch (Exception e) {
			throw new RuntimeException("Failed to call Gemini API", e);
		}
	}
}