package com.selenium.testng.context;

/**
 * 
 *  We store the details in this POJO somewhere central w.r.t threads like:
	
	Thread 1 -> Chrome QA
	Thread 2 -> Firefox Stage and so on (combinations to execute in parallel)
	
	That's what ThreadLocal TestContext does.
	
	
	
	We use TestContext.getContext().getBrowser() and TestContext.getContext().getEnv() throughout the framework 
	to retrieve the browser and environment details using TestContext POJO class. 
	These values are stored in a TestContext object that is maintained separately for each thread using ThreadLocal, 
	ensuring thread safety during parallel execution while also working correctly for single-threaded execution.
 * 
 * @author Arzoo Hingorani
 *
 */
public class TestContext {

	private String browser;
	private String env;
	private Boolean headless;
	private static ThreadLocal<TestContext> context = new ThreadLocal<>();

	// For AI failure analysis 
	// For UI execution context
	private String lastAction;
	private String lastLocator;
	private String lastElementDiagnostics;
	
	// For API execution context
	private String apiMethod;
	private String apiEndpoint;
	private Integer apiStatusCode; // datatype Integer instead of int and Long instead of long because these values can be null
	private Long apiResponseTime;

	public TestContext(String browser, String env, Boolean headless) {

		this.browser = browser;
		this.env = env;
		this.headless = headless;
	}

	public String getBrowser() {
		return browser;
	}

	public String getEnv() {
		return env;
	}
	
	public Boolean isHeadless() {
		return headless;
	}

	/*
	 * Static methods are fine here because:
	 * ThreadLocal holds the thread-specific data not the static method itself.
	 */
	public static void setContext(TestContext testContext) {

		context.set(testContext);
	}

	public static TestContext getContext() {

		return context.get();
	}

	/*
	 * Method used to get and set information important for AI failure analysis
	 */
	public String getLastAction() {
	    return lastAction;
	}

	public String getLastLocator() {
	    return lastLocator;
	}
	
	public String getLastElementDiagnostics() {
	    return lastElementDiagnostics;
	}
	
	public void setLastAction(String lastAction, String lastLocator, String lastElementDiagnostics) {

		this.lastAction = lastAction;
		this.lastLocator = lastLocator;
		this.lastElementDiagnostics = lastElementDiagnostics;
	}
	
	public String getApiMethod() {
		return apiMethod;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public Integer getApiStatusCode() {
		return apiStatusCode;
	}

	public Long getApiResponseTime() {
		return apiResponseTime;
	}

	public void setApiExecutionContext(String apiMethod, String apiEndpoint, Integer apiStatusCode,
			Long apiResponseTime) {

		this.apiMethod = apiMethod;
		this.apiEndpoint = apiEndpoint;
		this.apiStatusCode = apiStatusCode;
		this.apiResponseTime = apiResponseTime;
	}
	
	public static void unload() {

		context.remove();
	}
}
