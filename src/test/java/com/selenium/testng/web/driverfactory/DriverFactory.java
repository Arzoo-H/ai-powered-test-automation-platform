package com.selenium.testng.web.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;

import com.selenium.testng.utils.LoggerUtil;

public class DriverFactory {

	private static final Logger log = LoggerUtil.getLogger(DriverFactory.class);

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static void initDriver(String browser, boolean headless) {

		WebDriver webDriver;

		switch (browser.toLowerCase()) {

		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			log.info("Creating ChromeDriver");
			log.info("Headless : ", headless);

			if (headless) {
				chromeOptions.addArguments("--headless=new");
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--disable-dev-shm-usage");
			}

			webDriver = new ChromeDriver(chromeOptions);
			break;

		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			log.info("Creating FirefoxDriver");
			log.info("Headless : ", headless);

			if (headless) {
				firefoxOptions.addArguments("-headless");
			}

			webDriver = new FirefoxDriver(firefoxOptions);
			break;

		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			log.info("Creating EdgeDriver");
			log.info("Headless : ", headless);

			if (headless) {
				edgeOptions.addArguments("--headless=new");
				edgeOptions.addArguments("--no-sandbox");
				edgeOptions.addArguments("--disable-dev-shm-usage");
			}

			webDriver = new EdgeDriver(edgeOptions);
			break;

		case "safari":
			log.info("Creating SafariDriver");

			webDriver = new SafariDriver();
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		log.info("Maximizing browser");
		webDriver.manage().window().maximize();

		driver.set(webDriver);
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void unload() {
		driver.remove();
	}
}
