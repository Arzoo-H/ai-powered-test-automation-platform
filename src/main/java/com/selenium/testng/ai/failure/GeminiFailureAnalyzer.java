package com.selenium.testng.ai.failure;

import com.google.gson.Gson;
import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.execution.ExecutionResult;

public class GeminiFailureAnalyzer implements FailureAnalyzer {

    private final AIClient aiClient;
    private final PromptBuilder promptBuilder;

    public GeminiFailureAnalyzer(AIClient aiClient, PromptBuilder promptBuilder) {
        this.aiClient = aiClient;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public FailureAnalysis analyze(ExecutionResult executionResult) {

    	// Create prompt
        String prompt = promptBuilder.buildFailureAnalysisPrompt(executionResult);

        // Send to AI
        String response = aiClient.generate(prompt);

        // JSON parsing will be added in the next step.
        // For now, we are only validating the complete flow.
        System.out.println("Gemini Analysis Response:");
        System.out.println(response);

        Gson gson = new Gson();
        // map the response to key-value pair to FailureAnalysis POJO variables
        FailureAnalysis analysis = gson.fromJson(response, FailureAnalysis.class); 
        return analysis;
    }
}