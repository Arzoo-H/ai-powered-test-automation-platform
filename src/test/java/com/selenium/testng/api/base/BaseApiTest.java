package com.selenium.testng.api.base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.selenium.testng.config.ConfigManager;
import com.selenium.testng.config.LoggerConfig;
import com.selenium.testng.context.TestContext;
import com.selenium.testng.listeners.RetryListener;
import com.selenium.testng.listeners.TestListener;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

@Listeners({
    TestListener.class,
    RetryListener.class
})
public class BaseApiTest {
	
    @BeforeSuite
    public void oneTimeSetup() {
    	
    	// Programmatically setting log file details instead setting in Logback.xml
    	// because Logback reads logback.xml before our code gets a chance to set logFileName with 
    	// same runID as ExtentReport and Screenshot
    	LoggerConfig.configure();
    }

	@Parameters({"env"})
	@BeforeClass
	public void setupApi(@Optional("qa") String env) {

		TestContext.setContext(new TestContext(null, env, null));

		RestAssured.baseURI = ConfigManager.getInstance().getApiUrl(env);
		
		RestAssured.requestSpecification = new RequestSpecBuilder()
												.addHeader("x-api-key", ConfigManager.getInstance().getApiKey(env))
												.setContentType(ContentType.JSON)
												.build();
	}
	
}
