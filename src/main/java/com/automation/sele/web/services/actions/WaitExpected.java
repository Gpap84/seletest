/**
 * 
 */
package com.automation.sele.web.services.actions;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.automation.sele.web.selenium.webAPI.elements.Locators;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@Service
public class WaitExpected<T> implements ActionsSync<Object>{

	/** Wait for element to be present in DOM
	 * @param <T>
	 * 
	 * @param driver
	 * @param waitSeconds
	 * @param locator
	 * @return
	 */
	@Override
	public WebElement waitForElementPresence(Object driver, long waitSeconds, String locator) {
		WebDriverWait wait = new WebDriverWait((WebDriver)driver, waitSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).determineLocator(locator)));
	}
	
	/** Wait for element to be visible
	 * @param driver
	 * @param waitSeconds
	 * @param locator
	 * @return
	 */
	@Override
	public WebElement waitForElementVisibility(Object driver, long waitSeconds, Object locator) {
		WebDriverWait wait = new WebDriverWait((WebDriver) driver, waitSeconds);
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
	@Override
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
