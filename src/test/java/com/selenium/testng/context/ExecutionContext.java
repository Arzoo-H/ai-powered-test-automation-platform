package com.selenium.testng.context;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class ExecutionContext {

	private static final String RUN_ID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

	public static String getRunId() {
		return RUN_ID;
	}

	public static String getLogPath() {

		return System.getProperty("user.dir") + "/logs/automation_" + RUN_ID + ".log";
	}

	public static String getReportPath() {

		return System.getProperty("user.dir") + "/reports/ExtentReport_" + RUN_ID + ".html";
	}

	public static String getScreenshotPath(String testName) {

		return System.getProperty("user.dir") + "/screenshots/" + testName + "_" + RUN_ID + ".png";
	}
}
