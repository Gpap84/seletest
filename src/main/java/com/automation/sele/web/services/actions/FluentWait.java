/**
 * 
 */
package com.automation.sele.web.services.actions;

import org.openqa.selenium.WebDriver;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class FluentWait<T> implements ActionsSync<Object>{ {

}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementPresence(java.lang.Object, long, java.lang.String)
 */
@Override
public Object waitForElementPresence(Object driver, long waitSeconds,
		String locator) {
	// TODO Auto-generated method stub
	return null;
}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementVisibility(java.lang.Object, long, java.lang.Object)
 */
@Override
public Object waitForElementVisibility(Object driver, long waitSeconds,
		Object locator) {
	// TODO Auto-generated method stub
	return null;
}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementToBeClickable(org.openqa.selenium.WebDriver, long, java.lang.Object)
 */
@Override
public Object waitForElementToBeClickable(WebDriver driver, long waitSeconds,
		Object locator) {
	// TODO Auto-generated method stub
	return null;
}
}
