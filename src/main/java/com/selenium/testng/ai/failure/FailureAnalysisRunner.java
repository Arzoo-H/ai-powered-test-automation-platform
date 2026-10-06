package com.selenium.testng.ai.failure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selenium.testng.execution.ExecutionResult;

public class FailureAnalysisRunner {
	
	private static final Logger log = LoggerFactory.getLogger(FailureAnalysisRunner.class);

    private final FailureAnalyzer failureAnalyzer;

    public FailureAnalysisRunner(FailureAnalyzer failureAnalyzer) {
        this.failureAnalyzer = failureAnalyzer;
    }

    public List<ExecutionResult> analyzeFailures(List<ExecutionResult> executionResults) {

		log.info("Starting failure analysis | Execution results: {}", executionResults.size());
		
        /*
         * Each ExecutionResult represents one execution attempt. Grouping by
         * invocationId allows us to treat retries as one logical test.
         *
         * invocationId becomes the key, and the corresponding execution attempts
         * become a List<ExecutionResult>.
         *
         * For example, if one test case runs twice due to retry logic,
         * there will be two ExecutionResult objects under one invocationId.
         */
		Map<String, List<ExecutionResult>> groupedResults = executionResults.stream()
																			.collect(Collectors.groupingBy(ExecutionResult::getInvocationId));

		log.info("Failure analysis candidates | Logical tests: {}", groupedResults.size());
		
		int analysesGenerated = 0;
		
		for (List<ExecutionResult> attempts : groupedResults.values()) {

			/*
			 * The final attempt determines the test result.
			 * What reduce() does -> Whenever you give me two elements, throw away the first and keep the second
			 * 
			 * Example: FAILED + retryScheduled=true | FAILED + retryScheduled=true | PASSED = 2 retry attempts
			 * then it will keep second one and then it will run through second and third and choose third entry
			 * And null when there are 0 attempts
			 */
			ExecutionResult finalAttempt = attempts.stream()
												   .reduce((first, second) -> second) // reduce() combines multiple elements into one element
												   .orElse(null);

			if (finalAttempt == null) {
				continue;
			}

			if ("FAILED".equals(finalAttempt.getStatus())) { // checking whether the final attempt failed

				try {
					FailureAnalysis analysis = failureAnalyzer.analyze(finalAttempt);

					finalAttempt.setFailureAnalysis(analysis);
					analysesGenerated++;

				} catch (Exception e) {
					log.error("AI failure analysis failed for test: {}", finalAttempt.getTestName(), e);
				}
			}
		}
		
		 // Log completion only after all logical tests have been processed
		log.info("Failure analysis completed | Analyses generated: {}", analysesGenerated);
        return executionResults;
    }
}