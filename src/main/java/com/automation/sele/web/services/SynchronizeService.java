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
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class SynchronizeService {


	public WebElement waitForElementPresence(WebDriver driver, long waitSeconds, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, waitSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).determineLocator(locator)));
	}
	
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
}
