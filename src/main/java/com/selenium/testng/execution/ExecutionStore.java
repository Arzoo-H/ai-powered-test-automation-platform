package com.selenium.testng.execution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExecutionStore {

	private static final List<ExecutionResult> executionResults = Collections.synchronizedList(new ArrayList<>());

	private ExecutionStore() {
		// Utility class
	}

	public static void add(ExecutionResult executionResult) {

		if (executionResult != null) {
			executionResults.add(executionResult);
		}
	}

	public static List<ExecutionResult> getResults() {

		synchronized (executionResults) {
			return new ArrayList<>(executionResults);
		}
	}

	public static void clear() {
		executionResults.clear();
	}
}