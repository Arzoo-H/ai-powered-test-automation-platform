package com.selenium.testng.utils;

import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.testng.context.ExecutionContext;

/**
 * 
 * Responsible for:

	Create report
	Configure report
	Attach reporter
	Flush report
	
 * @author Arzoo Hingorani
 * 
 */
public class ExtentManager {

	private static final Logger log = LoggerUtil.getLogger(ExtentManager.class);

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getInstance() {

        if (extent == null) {

        	log.info("Creating Extent Report");
            String reportPath = ExecutionContext.getReportPath();
        	log.info("Report location: ", reportPath);

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            extent = new ExtentReports();

            extent.attachReporter(spark);

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("UI Automation Results");
        }

        return extent;
    }
    
    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void unload() {
        extentTest.remove();
    }
}
