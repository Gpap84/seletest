/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public interface WebActionsController {

	<T> T clickTo(Object locator);
	
	<T> T enterTo(Object locator, String text);
	
}
