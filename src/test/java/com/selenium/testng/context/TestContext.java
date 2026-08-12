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

	public static void unload() {

		context.remove();
	}
}
