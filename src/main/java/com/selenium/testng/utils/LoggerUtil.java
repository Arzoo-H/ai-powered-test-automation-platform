package com.selenium.testng.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * SLF4J stands for: Simple Logging Facade for Java

	A facade is just an interface/abstraction.

	SLF4J defines methods like: log.info("Login successful"); | log.error("Login failed"); etc
 * 
 * 
 * Logback is the actual logging engine. It decides: Where to write logs? AND How should logs look?
 * 
 * -----------------------------------------------------------------------------------------------
 * Advantage of using both --> tomorrow you can switch:

			Logback
			   ↓
			Log4j2
			
			without changing:
			log.info(...)
			log.error(...)
 * 
 * -----------------------------------------------------------------------------------------------
 * What are the role of these two 'SLF4J + Logback' in logging?

	Analogy: Think of,
	
	SLF4J = WebDriver interface
	Logback = ChromeDriver implementation
	
	Just like: WebDriver driver = new ChromeDriver();
	
 * -----------------------------------------------------------------------------------------------
 * @author Arzoo Hingorani
 *
 */
public class LoggerUtil {

    public static Logger getLogger(Class<?> clazz) {

        return LoggerFactory.getLogger(clazz);
    }
}