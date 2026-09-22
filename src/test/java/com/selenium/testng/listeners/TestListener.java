package com.selenium.testng.listeners;

import java.util.List;

import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.selenium.testng.ai.failure.FailureAnalysisFormatter;
import com.selenium.testng.ai.failure.FailureAnalysisRunner;
import com.selenium.testng.ai.failure.FailureAnalyzer;
import com.selenium.testng.ai.failure.FailureAnalyzerFactory;
import com.selenium.testng.context.TestContext;
import com.selenium.testng.execution.ExecutionDataCollector;
import com.selenium.testng.execution.ExecutionResult;
import com.selenium.testng.execution.ExecutionStore;
import com.selenium.testng.utils.ExtentManager;
import com.selenium.testng.utils.ExtentTestStore;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.utils.ScreenshotUtil;
import com.selenium.testng.web.driverfactory.DriverFactory;

public class TestListener implements ITestListener {

	private static final Logger log = LoggerUtil.getLogger(TestListener.class);

    private ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onStart(ITestContext context) {

        ExecutionStore.clear();
        ExtentTestStore.clear();
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

    	ExtentTest extentTest = ExtentManager.getTest();

    	if (extentTest != null) {
    	    extentTest.pass("Test Passed");
    	}
    	
    	// Convert TestNG execution data into a standardized result for AI analysis
    	ExecutionResult executionResult = ExecutionDataCollector.collect(result, null);
        
    	// Preserve the execution result for AI / execution-level processing
        ExecutionStore.add(executionResult);

        // Preserve the relationship between this execution and its Extent report entry
        ExtentTestStore.add(executionResult, extentTest);

    }
    
    @Override
    public void onTestSkipped(ITestResult result) {

		// Convert TestNG execution data into a standardized result for AI analysis
		ExecutionResult executionResult = ExecutionDataCollector.collect(result, null);

		// Because a skipped test can sometimes occur before your onTestStart() has successfully created ExtentTest
		// This prevents the reporting code from throwing a NullPointerException
		if (ExtentManager.getTest() != null) {
			ExtentManager.getTest().skip("Test Skipped");

			// Preserve the relationship between this execution result and its corresponding Extent report entry
			ExtentTestStore.add(executionResult, ExtentManager.getTest());
		}

		// Store the execution result for AI / execution-level processing
		ExecutionStore.add(executionResult);

    }

    @Override
    public void onTestFailure(ITestResult result) {
    	
    	String screenshotPath = null;

		ExtentTest extentTest = ExtentManager.getTest();

		if (extentTest != null) {
			extentTest.fail(result.getThrowable());
		}
    	
		log.error("Test Failed: {}", result.getMethod().getMethodName(), result.getThrowable());

		if (DriverFactory.getDriver() != null) {
			screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(),
					result.getMethod().getMethodName());
			if (extentTest != null) {
				try {

					extentTest.addScreenCaptureFromPath(screenshotPath);
					log.info("Screenshot saved: {}", screenshotPath);

				} catch (Exception e) {
					log.error("Unable to attach screenshot", e);
				}
			}
		}
        
		// Convert TestNG execution data into a standardized result for AI analysis
		ExecutionResult executionResult = ExecutionDataCollector.collect(result, screenshotPath);
		
		// Store the execution result for AI / execution-level processing
	    ExecutionStore.add(executionResult);

	    // Preserve the relationship between this execution result and its corresponding Extent report entry
	    if (extentTest != null) {
	        ExtentTestStore.add(executionResult, extentTest);
	    }
    }

    @Override
    public void onFinish(ITestContext context) {
    	
    	// Retrieve the execution results collected during this TestNG <test> context.
        // These results are the framework-level representation of the tests that were executed.
		List<ExecutionResult> results = ExecutionStore.getResults();

		try { 
			// create object for Rule/Gemini based analysis// Create the configured failure analyzer.
		    // FailureAnalyzerFactory decides which implementation to use based on config.properties:
		    // GEMINI      -> GeminiFailureAnalyzer
		    // RULE_BASED  -> RuleBasedFailureAnalyzer
			FailureAnalyzer failureAnalyzer = FailureAnalyzerFactory.create();	
			
			// Inject the selected analyzer into the runner.
		    // The runner is responsible for applying failure analysis to the collected executions.
			FailureAnalysisRunner runner = new FailureAnalysisRunner(failureAnalyzer);
			
			// Analyze and updated ExecutionResult with FailureAnalysis : failed executions and enrich their ExecutionResult objects
		    // with the generated FailureAnalysis.
			results = runner.analyzeFailures(results);
	    	
			// Add the generated failure analysis to the corresponding Extent report entry.
		    // ExtentTestStore maintains the mapping:
		    // ExecutionResult -> ExtentTest
			for (ExecutionResult executionResult : results) {
	
				// Only failed executions that were successfully analyzed will contain a FailureAnalysis. 
				if (executionResult.getFailureAnalysis() != null) {
	
					// Retrieve the ExtentTest associated with this exact execution.
					ExtentTest extentTest = ExtentTestStore.get(executionResult);
	
					if (extentTest != null) {
	
						// Format the structured FailureAnalysis into readable text
		                // and add it to the corresponding Extent report entry.
						extentTest.info(FailureAnalysisFormatter.format(executionResult.getFailureAnalysis()));
					}
				}
			}
		} catch (Exception e) {

	        // AI analysis is an enhancement and must not prevent
	        // the normal automation report from being generated.
	        log.error("AI failure analysis could not be completed", e);
	    }
		
        extent.flush();
        ExtentManager.unload();

    }
}