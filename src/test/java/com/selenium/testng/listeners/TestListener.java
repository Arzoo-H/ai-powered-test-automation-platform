package com.selenium.testng.listeners;

import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.selenium.testng.context.TestContext;
import com.selenium.testng.utils.ExtentManager;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.utils.ScreenshotUtil;
import com.selenium.testng.web.driverfactory.DriverFactory;

public class TestListener implements ITestListener {

	private static final Logger log = LoggerUtil.getLogger(TestListener.class);

    private ExtentReports extent = ExtentManager.getInstance();

    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());
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

        test.pass("Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.fail(result.getThrowable());
		log.error("Test Failed: {}", result.getMethod().getMethodName(), result.getThrowable());

		if (DriverFactory.getDriver() != null) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(),
					result.getMethod().getMethodName());

			try {
				test.addScreenCaptureFromPath(screenshotPath);
				log.info("Screenshot saved: {}", screenshotPath);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
        
    }

    @Override
    public void onFinish(ITestContext context) {
    	
        extent.flush();
        ExtentManager.unload();

    }
}