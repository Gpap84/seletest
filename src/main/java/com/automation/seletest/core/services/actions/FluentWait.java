/**
 *
 */
package com.automation.seletest.core.services.actions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;


/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("fluentWait")
public class FluentWait implements ActionsSync{

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementPresence(java.lang.String, long)
	 */
	@Override
	public WebElement waitForElementPresence(String locator, long waitSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementVisibility(java.lang.Object, long)
	 */
	@Override
	public WebElement waitForElementVisibility(Object locator, long waitSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementToBeClickable(java.lang.String, long)
	 */
	@Override
	public WebElement waitForElementToBeClickable(String locator,
			long waitSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.ActionsSync#waitForAlert(long)
	 */
	@Override
	public Alert waitForAlert(long waitSeconds) {
		// TODO Auto-generated method stub
		return null;
	} 

}
