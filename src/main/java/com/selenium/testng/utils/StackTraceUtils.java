package com.selenium.testng.utils;

import org.slf4j.Logger;

public final class StackTraceUtils {
	
	private static final Logger log = LoggerUtil.getLogger(StackTraceUtils.class);
	
	private static final int MAX_LINES = 12;

	private StackTraceUtils() {
		// Utility class
	}

	public static String getRelevantStackTrace(String stackTrace) {

		if (stackTrace == null || stackTrace.isBlank()) {
			return "Unavailable";
		}

		String[] lines = stackTrace.split("\\R"); // split wherever there is a line break

		// If the stack trace is already short (less than MAX_LINES = 12) keep everything
		if (lines.length <= MAX_LINES) {
			return stackTrace;
		}

		// else, if the stack trace is huge then only send the stack trace till 12 lines
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < MAX_LINES; i++) {
			result.append(lines[i]).append(System.lineSeparator());
		}
		result.append("... stack trace truncated ...");

		log.debug("Stack trace truncated | Original lines: {} | Retained lines: {}", lines.length, MAX_LINES);
		return result.toString();
	}
}