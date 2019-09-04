package test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class BaseRobot {

	private static final long DEFAULT_SLEEP_DURATION = 1000;
	private WebDriver driver;

	public BaseRobot() {
		setDriver(new FirefoxDriver());
		setDefaultWait();
	}

	public void setDefaultWait() {
		setWait(1);
	}

	public void setWait(int waitInSeconds) {
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	protected WebElement findElementById(String id) {
		return getDriver().findElement(By.id(id));
	}
	
	protected WebElement findElementByXpath(String xpath) {
		return getDriver().findElement(By.xpath(xpath));
	}
	
	protected WebElement findElementByXpathContainingText(String xpath, String text) {
		WebElement result = null;
		for (WebElement e : getDriver().findElements(By.xpath(xpath))) {

			if (e.getText().toLowerCase().contains(text.toLowerCase())) {
				result = e;
				break;
			}
		}
		if (result == null) {
			throw new IllegalStateException("Unable to find " + text);
		}
		return result;
	}

	public abstract void doWork() throws Exception;

	protected void scrollTo(WebElement elementToScrollTo) {
		getExecutor().executeScript("arguments[0].scrollIntoView(true);", elementToScrollTo);
	}

	protected void scrollToBottom() {
		getExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	protected String getInnerText(WebElement element) {
		return String.valueOf(getExecutor().executeScript("return arguments[0].innerText;", element));
	}

	public void pause() {
		// workaround for web driver wait
		try {
			Thread.sleep(DEFAULT_SLEEP_DURATION);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted from sleep", e);
		}
	}

	public void close() throws IOException {
		getDriver().quit();
	}
	
	public void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted from sleep", e);
		}
	}

	/**
	 * Gets the JavaScript executor
	 * 
	 * @return Returns the executor to run JavaScript with
	 * 
	 * @throws IllegalStateException IllegalStateException is thrown in case
	 *                               JavaScript execution is not supported
	 */
	public JavascriptExecutor getExecutor() {
		if (driver instanceof JavascriptExecutor) {
			return ((JavascriptExecutor) driver);
		} else {
			throw new IllegalStateException("JavaScript is not supported for " + getDriver());
		}
	}

}
