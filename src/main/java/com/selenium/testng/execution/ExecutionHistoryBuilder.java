package com.selenium.testng.execution;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.selenium.testng.utils.LoggerUtil;

public class ExecutionHistoryBuilder {

	private static final Logger log = LoggerUtil.getLogger(ExecutionHistoryBuilder.class);
	
	private ExecutionHistoryBuilder() {
		// Utility class
	}

	public static List<ExecutionHistory> build(List<ExecutionResult> results) {

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
		Map<String, List<ExecutionResult>> groupedResults = results.stream()
																	.collect(Collectors.groupingBy(ExecutionResult::getInvocationId));

		List<ExecutionHistory> histories = groupedResults.entrySet().stream()
																	.map(entry -> new ExecutionHistory(entry.getKey(), entry.getValue()))
																	.toList();
		
		log.info("Execution history built | Results: {} | Histories: {}", results.size(), histories.size());
		
		return histories;
	}
}