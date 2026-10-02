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

    	/*
		 * Each ExecutionResult represents one execution attempt. Grouping by
		 * invocationId allows us to treat retries as one logical test.
		 * 
		 * invocationId becomes the key 
		 * and corresponding individual execution becomes list of ExecutionResult
		 * so, if one testcase ran twice due to retry logic, then there will be two ExecutionResult logged against one invocationId
		 */
		Map<String, List<ExecutionResult>> groupedResults = executionResults.stream()
																			.collect(Collectors.groupingBy(ExecutionResult::getInvocationId));

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

			if ("FAILED".equals(finalAttempt.getStatus())) { // if a failure is found in the ExecutionResult

				try {
					FailureAnalysis analysis = failureAnalyzer.analyze(finalAttempt);

					finalAttempt.setFailureAnalysis(analysis);

				} catch (Exception e) {
					log.error("AI failure analysis failed for test: {}", finalAttempt.getTestName(), e);
				}
			}
		}
        return executionResults;
    }
}