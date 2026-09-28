package com.selenium.testng.ai.prompt;

import com.selenium.testng.execution.ExecutionResult;

public class PromptBuilder {

	public String buildFailureAnalysisPrompt(ExecutionResult executionResult) {

		return """

				You are an expert QA automation failure analyst.

				Analyze the following test execution failure and determine the most likely root cause.

				Use only the execution data provided below.
				Do not invent, assume, or infer missing evidence.
				If a field is null, empty, or unavailable, treat it as unavailable and do not fabricate a value.

				Pay particular attention to:
				- The exception type and message
				- The stack trace
				- The action that was being performed
				- The exact locator, when available
				- The actual element diagnostics, when available
				- The execution environment and URL

				Use the available evidence to distinguish between:
				- locator / element-not-found problems
				- timeout problems
				- assertion problems
				- authentication problems
				- application problems
				- environment / configuration problems
				- network problems
				- data problems

				Return ONLY valid JSON with exactly these fields:

				{
				  "failureType": "LOCATOR | TIMEOUT | ASSERTION | NETWORK | AUTHENTICATION | ENVIRONMENT | APPLICATION | DATA | CONFIGURATION | UNKNOWN",

				  "rootCause": "Concise statement of the most likely root cause",

				  "confidence": 0.0,

				  "explanation": "Concise explanation based only on the available evidence",

				  "possibleCauses": [
				    "Possible cause 1",
				    "Possible cause 2",
				    "Possible cause 3"
				  ],

				  "suggestedAction": "Concise recommended action for the QA engineer",

				  "evidence": [
				    "Specific evidence from the execution data"
				  ]
				}

				Rules:

				1. Confidence must be a number between 0.0 and 1.0.

				2. Do not repeat the entire stack trace in the explanation or evidence.

				3. Keep the explanation concise and focused on the most likely root cause.

				4. Return no more than 3 possible causes.

				5. Evidence must contain only facts present in the execution data.

				6. Do not invent a failed step, action, locator, element state, environment detail,
				   application behavior, or return value that is not explicitly provided.

				7. When Action, Locator, or Element Diagnostics are unavailable,
				   do not attempt to reconstruct them.

				8. Suggested actions should be practical and directly related to the evidence.

				9. For AssertionError, "expected true but found false" describes the
				   result of the complete assertion condition. It does not describe the
				   return value of the Action shown in the execution data.

				10. Action identifies the operation that was tracked immediately before
				    the failure. Do not assume that the Action's return value is the same
				    as the overall assertion result.

				11. Element Diagnostics describe the state observed for the target element
				    when diagnostics were captured. Do not claim that the element had a
				    different state unless explicit evidence supports that claim.

				12. If Action is a boolean-checking operation such as isDisplayed(),
				    isEnabled(), or isSelected(), do not infer its return value from the
				    assertion message. Use the explicit execution data and diagnostics.

				13. When an assertion may contain multiple operations, boolean expressions,
				    negation, or method calls, do not identify a specific operand as the
				    cause of failure unless the execution data explicitly establishes its
				    result.

				14. Do not suggest timing, synchronization, WebDriver bugs, rendering issues,
				    or transient behavior merely because two pieces of information appear
				    different. Such explanations require supporting evidence.

				15. When the available evidence is insufficient to determine which part of
				    a composite assertion caused the failure, explicitly state that the
				    exact failing operand cannot be determined from the available execution
				    data.

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

				Action: %s

				Locator: %s

				Element Diagnostics:
				%s

				Stack Trace:
				%s

				"""
				.formatted(
						executionResult.getTestName(), 
						executionResult.getTestClass(), 
						executionResult.getStatus(),
						executionResult.getDuration(), 
						executionResult.getBrowser(), 
						executionResult.getEnvironment(),
						executionResult.getUrl(), 
						executionResult.getExceptionType(),
						executionResult.getExceptionMessage(), 
						executionResult.getAction(), 
						executionResult.getLocator(),
						executionResult.getElementDiagnostics(), 
						executionResult.getStackTrace());
	}
}