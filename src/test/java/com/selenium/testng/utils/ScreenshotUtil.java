package com.selenium.testng.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.selenium.testng.context.ExecutionContext;

public class ScreenshotUtil {

	public static String captureScreenshot(WebDriver driver, String testName) {

		String screenshotPath = ExecutionContext.getScreenshotPath(testName);
		
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {

			Files.createDirectories(new File(screenshotPath).getParentFile().toPath());

			Files.copy(source.toPath(), new File(screenshotPath).toPath(), StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenshotPath;
	}
}
