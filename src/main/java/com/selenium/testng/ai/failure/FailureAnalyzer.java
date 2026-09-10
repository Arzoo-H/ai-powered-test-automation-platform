package com.selenium.testng.ai.failure;

import com.selenium.testng.execution.ExecutionResult;

/**
 * A middle layer connecting two POJOs, i.e, ExecutionResult and FailureAnalysis.
 * Any class that claims to be a FailureAnalyzer (like OpenAIFailureAnalyzer or GeminiFailureAnalyzer)
 * MUST provide an analyze() method 
 * that accepts an ExecutionResult and returns a FailureAnalysis.
 * 
 * @author Arzoo Hingorani
 *
 */
public interface FailureAnalyzer {

    FailureAnalysis analyze(ExecutionResult executionResult);

}
