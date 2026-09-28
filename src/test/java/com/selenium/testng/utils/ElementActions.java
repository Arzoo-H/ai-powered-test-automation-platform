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

import com.selenium.testng.context.TestContext;
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

		// Record the action and exact locator before Selenium attempts the action.
	    recordAction("click", locator.toString());

	    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
	    
	    // Once Selenium resolves the element, capture its actual DOM state.
	    recordAction("click", locator.toString(), element);

	    element.click();
	}

	public void click(WebElement element) {

		// The original By locator is not available once we only have a WebElement.
	    // Record the action before attempting the interaction so the failure context
	    // is still available if the click itself fails.
	    recordAction("click", "Original locator unavailable", element);
	    
		wait.until(ExpectedConditions.elementToBeClickable(element));

		element.click();
	}

	public void jsClick(By locator) {

		// Record the exact locator before Selenium attempts to resolve it.
	    recordAction("jsClick", locator.toString());
	    
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
		// Capture the actual DOM element details once the locator is resolved.
	    recordAction("jsClick", locator.toString(), element);

		js.executeScript("arguments[0].click();", element);
	}

	public void jsClick(WebElement element) {
		
		// The original By locator is unavailable, but the actual WebElement can still
		// provide useful diagnostic information.
		recordAction("jsClick", "Original locator unavailable", element);

		wait.until(ExpectedConditions.visibilityOf(element));

		js.executeScript("arguments[0].click();", element);
	}

	// ==========================================
	// ENTER TEXT
	// ==========================================

	public void enterText(By locator, String text) {

		// Record the exact locator before Selenium attempts to resolve the element.
	    recordAction("enterText", locator.toString());
	    
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
		// Capture the actual element state once the locator is resolved.
	    recordAction("enterText", locator.toString(), element);

		element.clear();
		element.sendKeys(text);
	}

	public void enterText(WebElement element, String text) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("enterText", "Original locator unavailable", element);
	    
		wait.until(ExpectedConditions.visibilityOf(element));

		element.clear();
		element.sendKeys(text);
	}

	public void jsSetValue(By locator, String value) {
		
		// Record the exact locator before Selenium attempts to resolve the element.
	    recordAction("jsSetValue", locator.toString());

		WebElement element = getElement(locator);
		
	    // Capture the actual element state once the locator is resolved.
	    recordAction("jsSetValue", locator.toString(), element);
	    
		js.executeScript("arguments[0].value=arguments[1]", element, value);
	}

	public void jsSetValue(WebElement element, String value) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("jsSetValue", "Original locator unavailable", element);

		js.executeScript("arguments[0].value=arguments[1]", element, value);
	}

	// ==========================================
	// GET TEXT
	// ==========================================

	public String getText(By locator) {

		// Record the exact locator before attempting to resolve the element.
	    recordAction("getText", locator.toString());
	    
	    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    
	    // Capture the actual element state once the locator is resolved.
	    recordAction("getText", locator.toString(), element);
	    
	    return element.getText();
	}

	public String getText(WebElement element) {
		
		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("getText", "Original locator unavailable", element);

		wait.until(ExpectedConditions.visibilityOf(element));

		return element.getText();
	}

	public String getTextUsingJS(By locator) {

		// Record the exact locator before attempting to resolve the element.
	    recordAction("getTextUsingJS", locator.toString());
	    
		WebElement element = getElement(locator);
		
		// Capture the actual element state once the locator is resolved.
	    recordAction("getTextUsingJS", locator.toString(), element);

		return (String) js.executeScript("return arguments[0].textContent;", element);
	}
	
	// ==========================================
	// GET ATTRIBUTE
	// ==========================================
	
	public String getAttribute(By locator, String attributeName) {
		
		// Record the exact locator before attempting to resolve the element.
		recordAction("getAttribute(" + attributeName + ")", locator.toString());

	    WebElement element = waitForVisibility(locator);

		// Capture the actual element state once the locator is resolved.
		recordAction("getAttribute(" + attributeName + ")", locator.toString(), element);

		return element.getAttribute(attributeName);
	}
	
	public String getAttribute(WebElement element, String attributeName) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("getAttribute(" + attributeName + ")", "Original locator unavailable", element);
		
	    return element.getAttribute(attributeName);
	}

	// ==========================================
	// DISPLAYED
	// ==========================================

	public boolean isDisplayed(By locator) {

		// Record the exact locator before attempting to resolve the element.
	    recordAction("isDisplayed", locator.toString());

	    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	    // Capture the actual element state once the locator is resolved.
	    recordAction("isDisplayed", locator.toString(), element);

	    return element.isDisplayed();
	}

	public boolean isDisplayed(WebElement element) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("isDisplayed", "Original locator unavailable", element);
	    
		wait.until(ExpectedConditions.visibilityOf(element));

		return element.isDisplayed();
	}

	// ==========================================
	// ENABLED
	// ==========================================

	public boolean isEnabled(By locator) {

		// Record the exact locator before attempting to resolve the element.
		recordAction("isEnabled", locator.toString());

		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		// Capture the actual element state once the locator is resolved.
		recordAction("isEnabled", locator.toString(), element);

		return element.isEnabled();
	}

	public boolean isEnabled(WebElement element) {
		
		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("isEnabled", "Original locator unavailable", element);

		return element.isEnabled();
	}

	// ==========================================
	// SELECTED
	// ==========================================

	public boolean isSelected(By locator) {

		// Record the exact locator before attempting to resolve the element.
		recordAction("isSelected", locator.toString());

		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		// Capture the actual element state once the locator is resolved.
		recordAction("isSelected", locator.toString(), element);

		return element.isSelected();
	}

	public boolean isSelected(WebElement element) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("isSelected", "Original locator unavailable", element);
	    
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

		// Record the exact locator before attempting to find the elements.
		recordAction("getElements", locator.toString());

		List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		// Capture diagnostics for the first matched element as a representative
		// element while retaining the exact locator used for the collection lookup.
		if (!elements.isEmpty()) {
			recordAction("getElements", locator.toString(), elements.get(0));
		}

		return elements;
	}

	// ==========================================
	// SCROLL
	// ==========================================

	public void scrollIntoView(By locator) {

		// Record the exact locator before attempting to resolve the element.
	    recordAction("scrollIntoView", locator.toString());
	    
		WebElement element = getElement(locator);
		
		// Capture the actual element state once the locator is resolved.
	    recordAction("scrollIntoView", locator.toString(), element);

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	public void scrollIntoView(WebElement element) {

		// The original By locator is unavailable, but the WebElement
		// can still provide useful diagnostic information.
		recordAction("scrollIntoView", "Original locator unavailable", element);
	    
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	public void scrollToBottom() {

		recordAction("scrollToBottom", "Page-level action");
		
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollToTop() {

		recordAction("scrollToTop", "Page-level action");

		js.executeScript("window.scrollTo(0,0);");
	}

	// ==========================================
	// HIGHLIGHT
	// ==========================================

	public void highlightElement(By locator) {

		// Record the exact locator before attempting to resolve the element.
	    recordAction("highlightElement", locator.toString());

		WebElement element = getElement(locator);
		
		// Capture the actual element state once the locator is resolved.
	    recordAction("highlightElement", locator.toString(), element);

		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// ==========================================
	// JS WAIT
	// ==========================================

	public void waitForPageToLoad() {

		recordAction("waitForPageToLoad", "Page-level action");
		
		wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
	}
	
	// ==========================================
	// Helping function to record last action and locator for AI failure analysis
	// ==========================================
	private void recordAction(String action, String locator) {

		TestContext context = TestContext.getContext();

		if (context != null) {
			context.setLastAction(action, locator, "Element diagnostics unavailable - element not resolved");
		}
	}

	private void recordAction(String action, String locator, WebElement element) {

		TestContext context = TestContext.getContext();

		if (context != null) {
			context.setLastAction(action, locator, getElementDiagnostics(element));
		}
	}

	private String getElementDiagnostics(WebElement element) {

		try {

			StringBuilder diagnostics = new StringBuilder();
			diagnostics.append("Tag: ").append(element.getTagName()).append("\n");

			// call this function to call getAttribute() to get values of element
			appendAttribute(diagnostics, "ID", element, "id");
			appendAttribute(diagnostics, "Name", element, "name");
			appendAttribute(diagnostics, "Class", element, "class");
			appendAttribute(diagnostics, "Type", element, "type");
			appendAttribute(diagnostics, "Role", element, "role");
			appendAttribute(diagnostics, "Aria-label", element, "aria-label");
			appendAttribute(diagnostics, "Test ID", element, "data-testid");

			String text = element.getText();

			if (text != null && !text.isBlank()) {
				diagnostics.append("Text: ").append(text.trim()).append("\n");
			}

			diagnostics.append("Displayed: ").append(element.isDisplayed()).append("\n");

			diagnostics.append("Enabled: ").append(element.isEnabled()).append("\n");

			diagnostics.append("Selected: ").append(element.isSelected()).append("\n");

			diagnostics.append("Location: ").append(element.getLocation()).append("\n");

			diagnostics.append("Size: ").append(element.getSize());

			return diagnostics.toString();

		} catch (Exception e) {

			return "Element diagnostics unavailable: " + e.getClass().getSimpleName() + " - " + e.getMessage();
		}
	}

	private void appendAttribute(StringBuilder diagnostics, String label, WebElement element, String attribute) {

		String value = element.getAttribute(attribute);

		if (value != null && !value.isBlank()) {
			diagnostics.append(label).append(": ").append(value).append("\n");
		}
	}
}