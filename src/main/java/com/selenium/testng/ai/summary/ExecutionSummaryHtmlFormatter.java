package com.selenium.testng.ai.summary;

public class ExecutionSummaryHtmlFormatter {

	public static String formatHealth(String health) {

		String healthIndicator;

		switch (health) {
		case "HEALTHY":
			healthIndicator =
		    "<span style='display:inline-block; width:10px; height:10px; " +
		    "background-color:green; border-radius:50%; " +
		    "vertical-align:middle; margin-right:5px;'></span>";
			break;

		case "NEEDS ATTENTION":
		    healthIndicator =
		        "<span style='display:inline-block; width:10px; height:10px; " +
		        "background-color:orange; border-radius:50%; " +
		        "vertical-align:middle; margin-right:5px;'></span>";
		    break;

		case "UNHEALTHY":
		    healthIndicator =
		        "<span style='display:inline-block; width:10px; height:10px; " +
		        "background-color:red; border-radius:50%; " +
		        "vertical-align:middle; margin-right:5px;'></span>";
		    break;

		default:
		    healthIndicator =
		        "<span style='display:inline-block; width:10px; height:10px; " +
		        "background-color:gray; border-radius:50%; " +
		        "vertical-align:middle; margin-right:5px;'></span>";
		}

		return healthIndicator + " " + health;
	}
}