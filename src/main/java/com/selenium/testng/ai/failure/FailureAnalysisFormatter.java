package com.selenium.testng.ai.failure;

import java.util.List;

/*
 * class to format how the AI Failure Analysis looks in extent report
 */
public class FailureAnalysisFormatter {

	private FailureAnalysisFormatter() {
		// Utility class
	}

	public static String format(FailureAnalysis analysis) {

		return """
				<b>AI FAILURE ANALYSIS</b><br><br>

				<b>Failure Type:</b> %s<br>
				<b>Root Cause:</b> %s<br>
				<b>Confidence:</b> %.2f<br><br>

				<b>Explanation:</b><br>
				%s<br><br>

				<b>Possible Causes:</b>
				%s

				<b>Suggested Action:</b><br>
				%s<br><br>

				<b>Evidence:</b>
				%s
				""".formatted(
						escapeHtml(analysis.getFailureType()), 
						escapeHtml(analysis.getRootCause()),
						analysis.getConfidence(), 
						escapeHtml(analysis.getExplanation()),
						formatList(analysis.getPossibleCauses()), 
						escapeHtml(analysis.getSuggestedAction()),
						formatList(analysis.getEvidence())
					);
	}

	private static String formatList(List<String> items) {

		if (items == null || items.isEmpty()) {
			return "<br>None<br><br>";
		}

		StringBuilder html = new StringBuilder("<ul>"); // unordered list
		for (String item : items) {
			html.append("<li>").append(escapeHtml(item)).append("</li>");
		}
		html.append("</ul><br>");

		return html.toString();
	}

	private static String escapeHtml(String value) {

		if (value == null) {
			return "";
		}

		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}