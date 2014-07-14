/**
 * 
 */
package com.automation.sele.web.services.actions;

import org.openqa.selenium.WebDriver;





/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
public interface ActionsSync<T> {

	T waitForElementPresence(Object driver, long waitSeconds, String locator);
	T waitForElementVisibility(Object driver, long waitSeconds, Object locator);
	T waitForElementToBeClickable(WebDriver driver, long waitSeconds, Object locator);

}
