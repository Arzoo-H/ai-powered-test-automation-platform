package com.selenium.testng.ai.summary;

public class ExecutionSummaryFormatter {

    private ExecutionSummaryFormatter() {
        // Utility class
    }

    public static String format(ExecutionSummary summary) {

        return """
                <h2>AI Execution Summary</h2>

                <b>Execution Metrics</b><br>
                Total Tests: %d<br>
                Passed: %d<br>
                Failed: %d<br>
                Skipped: %d<br>
                Total Attempts: %d<br>
                Retry Attempts: %d<br>
                Tests Retried: %d<br>
                Failed Attempts: %d<br>
                Recovered After Retry: %d<br>
                Total Duration: %d ms<br><br>

                <b>Overall Health</b><br>
                %s<br><br>

                <b>Summary</b><br>
                %s<br><br>

                <b>Key Observations</b><br>
                %s
                """.formatted(summary.getTotalTests(),
				                summary.getPassedTests(),
				                summary.getFailedTests(),
				                summary.getSkippedTests(),
				                summary.getTotalAttempts(),
				                summary.getRetryAttempts(),
				                summary.getTestsRetried(),
				                summary.getFailedAttempts(),
				                summary.getRecoveredAfterRetry(),
				                summary.getTotalDuration(),
				                summary.getOverallHealth(),
				                summary.getSummary(),
				                summary.getKeyObservations()
        );
    }
}