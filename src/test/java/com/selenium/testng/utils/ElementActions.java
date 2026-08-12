package com.selenium.testng.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.testng.web.driverfactory.DriverFactory;

public class ElementActions {

	private final WebDriverWait wait;
	private final JavascriptExecutor js;

	public ElementActions(WebDriver driver) {

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.js = (JavascriptExecutor) driver;

	}

	// ==========================================
	// PAGE RELATED
	// ==========================================

	public String getPageTitle() {

		return (String) js.executeScript("return document.title;");
	}

	public String getCurrentUrl() {

		return (String) js.executeScript("return window.location.href;");
	}

	// ==========================================
	// WAITS
	// ==========================================

	public WebElement waitForVisibility(By locator) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForVisibility(WebElement element) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public List<WebElement> waitForAllElementsVisible(List<WebElement> elements) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public WebElement waitForClickable(By locator) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public WebElement waitForClickable(WebElement element) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public boolean waitForInvisibility(By locator) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public boolean waitForInvisibility(WebElement element) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public Alert waitForAlert() {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public boolean waitForStaleness(WebElement element) {

		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

		return wait.until(ExpectedConditions.stalenessOf(element));
	}

	// ==========================================
	// CLICK
	// ==========================================

	public void click(By locator) {

		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void click(WebElement element) {

		wait.until(ExpectedConditions.elementToBeClickable(element));

		element.click();
	}

	public void jsClick(By locator) {

		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		js.executeScript("arguments[0].click();", element);
	}

	public void jsClick(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));

		js.executeScript("arguments[0].click();", element);
	}

	// ==========================================
	// ENTER TEXT
	// ==========================================

	public void enterText(By locator, String text) {

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		element.clear();
		element.sendKeys(text);
	}

	public void enterText(WebElement element, String text) {

		wait.until(ExpectedConditions.visibilityOf(element));

		element.clear();
		element.sendKeys(text);
	}

	public void jsSetValue(By locator, String value) {

		WebElement element = getElement(locator);

		js.executeScript("arguments[0].value=arguments[1]", element, value);
	}

	public void jsSetValue(WebElement element, String value) {

		js.executeScript("arguments[0].value=arguments[1]", element, value);
	}

	// ==========================================
	// GET TEXT
	// ==========================================

	public String getText(By locator) {

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	}

	public String getText(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));

		return element.getText();
	}

	public String getTextUsingJS(By locator) {

		WebElement element = getElement(locator);

		return (String) js.executeScript("return arguments[0].textContent;", element);
	}
	
	// ==========================================
	// GET ATTRIBUTE
	// ==========================================
	
	public String getAttribute(By locator, String attributeName) {

	    return waitForVisibility(locator).getAttribute(attributeName);
	}
	
	public String getAttribute(WebElement element, String attributeName) {

	    return element.getAttribute(attributeName);
	}

	// ==========================================
	// DISPLAYED
	// ==========================================

	public boolean isDisplayed(By locator) {

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
	}

	public boolean isDisplayed(WebElement element) {

		wait.until(ExpectedConditions.visibilityOf(element));

		return element.isDisplayed();
	}

	// ==========================================
	// ENABLED
	// ==========================================

	public boolean isEnabled(By locator) {

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isEnabled();
	}

	public boolean isEnabled(WebElement element) {

		return element.isEnabled();
	}

	// ==========================================
	// SELECTED
	// ==========================================

	public boolean isSelected(By locator) {

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isSelected();
	}

	public boolean isSelected(WebElement element) {

		return element.isSelected();
	}

	// ==========================================
	// GET ELEMENT
	// ==========================================

	public WebElement getElement(By locator) {

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// ==========================================
	// GET ELEMENTS
	// ==========================================

	public List<WebElement> getElements(By locator) {

		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	// ==========================================
	// SCROLL
	// ==========================================

	public void scrollIntoView(By locator) {

		WebElement element = getElement(locator);

		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	public void scrollIntoView(WebElement element) {

		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	public void scrollToBottom() {

		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollToTop() {

		js.executeScript("window.scrollTo(0,0);");
	}

	// ==========================================
	// HIGHLIGHT
	// ==========================================

	public void highlightElement(By locator) {

		WebElement element = getElement(locator);

		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// ==========================================
	// JS WAIT
	// ==========================================

	public void waitForPageToLoad() {

		wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
	}
}