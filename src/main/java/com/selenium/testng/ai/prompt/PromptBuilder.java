package com.selenium.testng.ai.prompt;

import com.selenium.testng.execution.ExecutionResult;

public class PromptBuilder {

    public String buildFailureAnalysisPrompt(ExecutionResult executionResult) {

        return """
                You are an expert QA automation failure analyst.

                Analyze the following test execution failure and determine the
                most likely root cause.

                Your analysis must be based only on the information provided.
                Do not invent evidence.

                Return ONLY valid JSON with exactly these fields:

                {
                  "failureType": "LOCATOR | TIMEOUT | ASSERTION | NETWORK | AUTHENTICATION | ENVIRONMENT | APPLICATION | DATA | CONFIGURATION | UNKNOWN",
                  "rootCause": "Most likely root cause",
                  "confidence": 0.0,
                  "explanation": "Detailed explanation of why this is the likely root cause",
                  "possibleCauses": [
                    "Possible cause 1",
                    "Possible cause 2"
                  ],
                  "suggestedAction": "Recommended action for the QA engineer",
                  "evidence": [
                    "Evidence from the execution data"
                  ]
                }

                Confidence must be a number between 0.0 and 1.0.

                Test execution details:

                Test Name: %s
                Test Class: %s
                Status: %s
                Duration: %d ms
                Browser: %s
                Environment: %s
                URL: %s
                Exception Type: %s
                Exception Message: %s
                Failed Step: %s
                Action: %s
                Locator: %s
                Stack Trace:
                %s
                """.formatted(
                        executionResult.getTestName(),
                        executionResult.getTestClass(),
                        executionResult.getStatus(),
                        executionResult.getDuration(),
                        executionResult.getBrowser(),
                        executionResult.getEnvironment(),
                        executionResult.getUrl(),
                        executionResult.getExceptionType(),
                        executionResult.getExceptionMessage(),
                        executionResult.getFailedStep(),
                        executionResult.getAction(),
                        executionResult.getLocator(),
                        executionResult.getStackTrace()
                );
    }
}