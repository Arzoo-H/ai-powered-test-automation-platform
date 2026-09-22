package com.selenium.testng.ai.failure;

import java.util.List;

import com.selenium.testng.execution.ExecutionResult;

public class FailureAnalysisRunner {

    private final FailureAnalyzer failureAnalyzer;

    public FailureAnalysisRunner(FailureAnalyzer failureAnalyzer) {
        this.failureAnalyzer = failureAnalyzer;
    }

    public List<ExecutionResult> analyzeFailures(List<ExecutionResult> executionResults) {

        for (ExecutionResult executionResult : executionResults) {

            if ("FAILED".equals(executionResult.getStatus())) {

                FailureAnalysis analysis = failureAnalyzer.analyze(executionResult);
                executionResult.setFailureAnalysis(analysis);

//                System.out.println("Failure Analysis:");
//                System.out.println("Test: " + executionResult.getTestName());
//                System.out.println("Type: " + analysis.getFailureType());
//                System.out.println("Root Cause: " + analysis.getRootCause());
//                System.out.println("Confidence: " + analysis.getConfidence());
            }
        }
        return executionResults;
    }
}