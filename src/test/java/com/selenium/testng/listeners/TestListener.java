package com.selenium.testng.listeners;

import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.selenium.testng.context.TestContext;
import com.selenium.testng.execution.ExecutionDataCollector;
import com.selenium.testng.execution.ExecutionResult;
import com.selenium.testng.execution.ExecutionStore;
import com.selenium.testng.utils.ExtentManager;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.utils.ScreenshotUtil;
import com.selenium.testng.web.driverfactory.DriverFactory;

public class TestListener implements ITestListener {

	private static final Logger log = LoggerUtil.getLogger(TestListener.class);

    private ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onStart(ITestContext context) {

        ExecutionStore.clear();
    }
    
    @Override
    public void onTestStart(ITestResult result) {

    	ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        // Assign this ExtentTest to a thread for one testcase/execution
        ExtentManager.setTest(test);
        
        // test object is registering all information to Extent reports
        // log object is registering all information to Log
		log.info("===================================");
    	test.info("Starting Test: " + result.getMethod().getMethodName());
		log.info("Starting Test: {}", result.getMethod().getMethodName());

    	if (TestContext.getContext().getBrowser() != null) {
    		test.assignCategory(TestContext.getContext().getBrowser()); // used to create filter tests by browser detail/category like chrome, firefox etc
        	test.info("Browser: " + TestContext.getContext().getBrowser());
    		log.info("Browser: {}", TestContext.getContext().getBrowser());
    	}

    	if (TestContext.getContext().getEnv() != null) {
    	    test.assignCategory(TestContext.getContext().getEnv());
    		test.info("Environment : " + TestContext.getContext().getEnv());
    		log.info("Environment: {}", TestContext.getContext().getEnv());
    	}
		log.info("===================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    	ExtentManager.getTest().pass("Test Passed");
    	
    	// Convert TestNG execution data into a standardized result for AI analysis
    	ExecutionResult executionResult = ExecutionDataCollector.collect(result, null);
    	ExecutionStore.add(executionResult);

    }
    
    @Override
    public void onTestSkipped(ITestResult result) {

    	// Because a skipped test can sometimes occur before your onTestStart() has successfully created ExtentTest
    	// This prevents the reporting code from throwing a NullPointerException
    	 if (ExtentManager.getTest() != null) {
    	        ExtentManager.getTest().skip("Test Skipped");
    	    }
    	
    	// Convert TestNG execution data into a standardized result for AI analysis
    	ExecutionResult executionResult = ExecutionDataCollector.collect(result, null);
    	ExecutionStore.add(executionResult);

    }

    @Override
    public void onTestFailure(ITestResult result) {
    	
    	String screenshotPath = null;

    	ExtentManager.getTest().fail(result.getThrowable());
		log.error("Test Failed: {}", result.getMethod().getMethodName(), result.getThrowable());

		if (DriverFactory.getDriver() != null) {
			screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(),
					result.getMethod().getMethodName());

			try {
				ExtentManager.getTest().addScreenCaptureFromPath(screenshotPath);
				log.info("Screenshot saved: {}", screenshotPath);

			} catch (Exception e) {
				log.error("Unable to attach screenshot", e);
			}
		}
        
		// Convert TestNG execution data into a standardized result for AI analysis
		ExecutionResult executionResult = ExecutionDataCollector.collect(result, screenshotPath);
		ExecutionStore.add(executionResult);
    }

    @Override
    public void onFinish(ITestContext context) {
    	
        extent.flush();
        ExtentManager.unload();

    }
}