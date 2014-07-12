/**
 * 
 */
package com.automation.sele.web.services;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.sitebricks.headless.Service;
import com.automation.sele.web.selenium.webAPI.elements.Locators;

/**
 * This class will be used for synchronizing actions in UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class SynchronizeService {


	/** Wait for element to be present in DOM
	 * 
	 * @param driver
	 * @param waitSeconds
	 * @param locator
	 * @return
	 */
	public WebElement waitForElementPresence(WebDriver driver, long waitSeconds, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).determineLocator(locator)));
	}
	
	/** Wait for element to be visible
	 * @param driver
	 * @param waitSeconds
	 * @param locator
	 * @return
	 */
	public WebElement waitForElementVisibility(WebDriver driver, long waitSeconds, Object locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
		if(locator instanceof String){
			return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.findByLocator((String)locator).determineLocator((String)locator)));
		}
		else if(locator instanceof WebElement){
			return wait.until(ExpectedConditions.visibilityOf((WebElement)locator));
		}
		else{
			throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
		}
	}
	
	/**
	 * Wait for element to be clickable
	 * @param driver
	 * @param waitSeconds
	 * @param locator
	 * @return
	 */
	public WebElement waitForElementToBeClickable(WebDriver driver, long waitSeconds, Object locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
		if(locator instanceof String){
			return wait.until(ExpectedConditions.elementToBeClickable(Locators.findByLocator((String)locator).determineLocator((String)locator)));
		}
		else if(locator instanceof WebElement){
			return wait.until(ExpectedConditions.elementToBeClickable((WebElement)locator));
		}
		else{
			throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
		}
	}
}
