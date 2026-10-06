package com.selenium.testng.listeners;

import java.util.List;

import org.slf4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.selenium.testng.ai.client.AIClient;
import com.selenium.testng.ai.client.AIClientFactory;
import com.selenium.testng.ai.failure.FailureAnalysisFormatter;
import com.selenium.testng.ai.failure.FailureAnalysisRunner;
import com.selenium.testng.ai.failure.FailureAnalyzer;
import com.selenium.testng.ai.failure.FailureAnalyzerFactory;
import com.selenium.testng.ai.prompt.PromptBuilder;
import com.selenium.testng.ai.summary.AISummaryGenerator;
import com.selenium.testng.ai.summary.ExecutionSummary;
import com.selenium.testng.ai.summary.ExecutionSummaryGenerator;
import com.selenium.testng.ai.summary.ExecutionSummaryHtmlFormatter;
import com.selenium.testng.execution.ExecutionResult;
import com.selenium.testng.execution.ExecutionStore;
import com.selenium.testng.utils.ExtentManager;
import com.selenium.testng.utils.ExtentTestStore;
import com.selenium.testng.utils.LoggerUtil;

public class SuiteListener implements ISuiteListener {

	private static final Logger log = LoggerUtil.getLogger(SuiteListener.class);

	private final ExtentReports extent = ExtentManager.getInstance();

	@Override
	public void onStart(ISuite suite) {

		log.info("SUITE STARTED: {} | Thread: {}", suite.getName(), Thread.currentThread().getName());

		ExecutionStore.clear();
		ExtentTestStore.clear();
	}

	@Override
	public void onFinish(ISuite suite) {

		log.info("SUITE FINISHED: {} | Thread: {}", suite.getName(), Thread.currentThread().getName());

		// Retrieve the execution results collected during this TestNG <test> context.
        // These results are the framework-level representation of the tests that were executed.
		List<ExecutionResult> results = ExecutionStore.getResults();

		log.info("ExecutionStore size for suite '{}': {}", suite.getName(), results.size());
		
		// -------------------------------
		//  AI Failure Analysis
		// -------------------------------
		try {

			// create object for Rule/Gemini based analysis// Create the configured failure analyzer.
		    // FailureAnalyzerFactory decides which implementation to use based on config.properties:
		    // GEMINI      -> GeminiFailureAnalyzer
		    // RULE_BASED  -> RuleBasedFailureAnalyzer
			FailureAnalyzer failureAnalyzer = FailureAnalyzerFactory.create();

			// Inject the selected analyzer into the runner.
		    // The runner is responsible for applying failure analysis to the collected executions.
			FailureAnalysisRunner runner = new FailureAnalysisRunner(failureAnalyzer);

			// Analyze and updated ExecutionResult with FailureAnalysis : failed executions and enrich their ExecutionResult objects
		    // with the generated FailureAnalysis.
			results = runner.analyzeFailures(results);

			// Add the generated failure analysis to the corresponding Extent report entry.
		    // ExtentTestStore maintains the mapping:
		    // ExecutionResult -> ExtentTest
			for (ExecutionResult executionResult : results) {

				// Only failed executions that were successfully analyzed will contain a FailureAnalysis. 
				if (executionResult.getFailureAnalysis() != null) {

					// Retrieve the ExtentTest associated with this exact execution.
					ExtentTest extentTest = ExtentTestStore.get(executionResult);

					if (extentTest != null) {

						// Format the structured FailureAnalysis into readable text
		                // and add it to the corresponding Extent report entry.
						extentTest.info(FailureAnalysisFormatter.format(executionResult.getFailureAnalysis()));
					}
				}
			}

		} catch (Exception e) {

			log.error("AI failure analysis could not be completed", e);
		}

		// -------------------------------
		//  AI Execution Summary
		// -------------------------------
		try {

			// Generate execution metrics
			ExecutionSummary summary = new ExecutionSummaryGenerator().generateSummary(results);

			log.info("Generating AI execution summary for suite '{}' with {} results", suite.getName(), results.size());
			
			log.info("========== EXECUTION SUMMARY ==========");
			log.info("Tests: {} | Passed: {} | Failed: {} | Skipped: {}",
			        summary.getTotalTests(),
			        summary.getPassedTests(),
			        summary.getFailedTests(),
			        summary.getSkippedTests());

			log.info("Attempts: {} | Retries: {} | Failed Attempts: {} | Recovered: {}",
			        summary.getTotalAttempts(),
			        summary.getRetryAttempts(),
			        summary.getFailedAttempts(),
			        summary.getRecoveredAfterRetry());

			log.info("Duration: {} ms | Health: {}",
			        summary.getTotalDuration(),
			        summary.getOverallHealth());

			log.info("========================================");

			// Generate AI execution summary
			AIClient aiClient = AIClientFactory.create();
			AISummaryGenerator aiSummaryGenerator = new AISummaryGenerator(aiClient, new PromptBuilder());
			
			summary = aiSummaryGenerator.generate(summary, results);

			log.info("AI execution summary generated for suite '{}'", suite.getName());

			// Add AI execution summary to Extent dashboard
			extent.setSystemInfo("AI Health", ExecutionSummaryHtmlFormatter.formatHealth(summary.getOverallHealth()));

			extent.setSystemInfo("AI Summary", summary.getSummary());

			extent.setSystemInfo("AI Observations", summary.getKeyObservations());

		} catch (Exception e) {

			log.error("AI execution summary could not be completed", e);
		}

		extent.flush();
		ExtentManager.unload();
	}
}