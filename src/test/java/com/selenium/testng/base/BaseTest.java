package com.selenium.testng.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.annotations.*;

import com.selenium.testng.config.ConfigManager;
import com.selenium.testng.config.LoggerConfig;
import com.selenium.testng.context.TestContext;
import com.selenium.testng.listeners.*;
import com.selenium.testng.utils.LoggerUtil;
import com.selenium.testng.web.driverfactory.DriverFactory;


@Listeners({
    TestListener.class,
    RetryListener.class
})
public class BaseTest {

	private static final Logger log = LoggerUtil.getLogger(BaseTest.class);
	protected WebDriver driver;
    protected String url;

    @BeforeSuite
    public void oneTimeSetup() {
    	
    	// Programmatically setting log file details instead setting in Logback.xml
    	// because Logback reads logback.xml before our code gets a chance to set logFileName with 
    	// same runID as ExtentReport and Screenshot
    	LoggerConfig.configure();
    }

    @Parameters({"browser", "env", "headless"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, @Optional("qa") String env, @Optional("false") boolean headless) {

    	// Set details to a POJO class that has threadlocal to maintain details for parallel execution
    	TestContext.setContext(new TestContext(browser, env, headless));
    	
    	// Spin a driver
    	DriverFactory.initDriver(browser, headless);
	    driver = DriverFactory.getDriver();
	    
	    
	    url = ConfigManager.getInstance().getUrl(env);
    	driver.get(url);
    	
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {

        	driver.quit();
        	log.info("Driver quit");
            DriverFactory.unload();
        }
        
        TestContext.unload(); // Otherwise the ThreadLocal value remains attached to the thread until the JVM decides to clean it up.
    }
}