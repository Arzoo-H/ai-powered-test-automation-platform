package com.selenium.testng.ai.failure;

public class FailureAnalysisFormatter {

    private FailureAnalysisFormatter() {
        // Utility class
    }

    public static String format(FailureAnalysis analysis) {

        return """
                AI Failure Analysis

                Failure Type: %s
                Root Cause: %s
                Confidence: %.2f

                Explanation:
                %s

                Possible Causes:
                %s

                Suggested Action:
                %s

                Evidence:
                %s
                """.formatted(
                analysis.getFailureType(),
                analysis.getRootCause(),
                analysis.getConfidence(),
                analysis.getExplanation(),
                String.join("\n- ", analysis.getPossibleCauses()),
                analysis.getSuggestedAction(),
                String.join("\n- ", analysis.getEvidence())
        );
    }
}