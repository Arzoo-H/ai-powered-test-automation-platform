package com.selenium.testng.ai.failure;

import java.util.List;

import com.selenium.testng.execution.ExecutionResult;

/**
 * On the basis of ExecutionResult of the suite, we are making FailureAnalysis
 * 
 * @author Arzoo Hingorani
 *
 */
public class RuleBasedFailureAnalyzer implements FailureAnalyzer {

	@Override
	public FailureAnalysis analyze(ExecutionResult executionResult) {

		FailureAnalysis analysis = new FailureAnalysis();

		String exceptionType = executionResult.getExceptionType();

		if (exceptionType == null) {
		    exceptionType = "";
		}
		
		if (exceptionType != null && exceptionType.contains("NoSuchElementException")) {

			analysis.setFailureType(FailureType.LOCATOR.name());

			analysis.setRootCause("The expected element could not be located.");

			analysis.setConfidence(0.90);

			analysis.setExplanation("The test failed with NoSuchElementException, indicating "
					+ "that Selenium could not find the element using the provided locator.");

			analysis.setPossibleCauses(List.of("The locator is incorrect", 
												"The DOM structure has changed",
												"The element was not loaded", 
												"The element is rendered conditionally"));

			analysis.setSuggestedAction(
					"Verify the locator against the current DOM and consider using a more stable locator.");

			analysis.setEvidence(List.of("Exception type: " + executionResult.getExceptionType(),
										"Action: " + executionResult.getAction(), 
										"Locator: " + executionResult.getLocator()));
			
		} else if (exceptionType.contains("TimeoutException")) {

			analysis.setFailureType(FailureType.TIMEOUT.name());

			analysis.setRootCause("The expected condition was not met within the configured timeout.");

			analysis.setConfidence(0.85);

			analysis.setExplanation("The test timed out while waiting for an element or condition to become available.");

			analysis.setPossibleCauses(List.of("Application response was slow", 
												"Element locator may be incorrect",
												"Element was not rendered", 
												"Network latency delayed page rendering"));

			analysis.setSuggestedAction("Verify the locator, page loading behavior, synchronization strategy, and configured timeout.");

			analysis.setEvidence(List.of("Exception type: " + executionResult.getExceptionType(),
											"Failed step: " + executionResult.getFailedStep(), 
											"Action: " + executionResult.getAction(),
											"Locator: " + executionResult.getLocator()));
			
		} else if (exceptionType.contains("AssertionError")) {

			analysis.setFailureType(FailureType.ASSERTION.name());

			analysis.setRootCause("The actual result did not match the expected result.");

			analysis.setConfidence(0.95);

			analysis.setExplanation("The test assertion failed because the application behavior "
					+ "did not match the expected behavior defined by the test.");

		    analysis.setPossibleCauses(List.of("Application behavior differs from the expected behavior",
									            "Test data is incorrect or outdated",
									            "Expected value is incorrect",
									            "Application defect"));

		    analysis.setSuggestedAction("Compare the expected and actual values and verify the test data and assertion logic.");

		    analysis.setEvidence(List.of("Exception type: " + executionResult.getExceptionType(),
								            "Exception message: " + executionResult.getExceptionMessage(),
								            "Failed step: " + executionResult.getFailedStep()));
		} else {

		    analysis.setFailureType(FailureType.UNKNOWN.name());

		    analysis.setRootCause("The failure could not be classified by the current rule-based analyzer.");

		    analysis.setConfidence(0.30);

		    analysis.setExplanation("The exception type does not match any of the failure patterns "
		            + "currently supported by the analyzer.");

		    analysis.setPossibleCauses(List.of("Application defect",
									            "Test automation issue",
									            "Environment issue",
									            "Unexpected exception"));

		    analysis.setSuggestedAction("Review the exception message, stack trace, failed step, and execution environment.");

		    analysis.setEvidence(List.of("Exception type: " + executionResult.getExceptionType(),
								            "Exception message: " + executionResult.getExceptionMessage(),
								            "Failed step: " + executionResult.getFailedStep(),
								            "Action: " + executionResult.getAction(),
								            "Locator: " + executionResult.getLocator()));
		}

		return analysis;
	}

}