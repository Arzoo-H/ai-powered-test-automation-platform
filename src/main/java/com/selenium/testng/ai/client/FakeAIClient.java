package com.selenium.testng.ai.client;

public class FakeAIClient implements AIClient {

    @Override
    public String generate(String prompt) {

        return """
                {
                  "summary": "FAKE SUMMARY - Gemini was not called.",
                  "overallHealth": "HEALTHY",
                  "keyObservations": "FAKE OBSERVATION - Used for TestNG lifecycle debugging."
                }
                """;
    }
}