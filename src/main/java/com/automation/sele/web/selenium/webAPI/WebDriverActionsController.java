/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;


import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.automation.sele.web.aspectJ.RetryIfFails;
import com.automation.sele.web.services.actions.WaitExpected;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the implementation of webDriver API 
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@SuppressWarnings({"hiding","unchecked"})
@Component
@Scope("prototype")
public class WebDriverActionsController<T> extends ActionsBase{

	@Autowired
	WaitExpected<T> waitFor;
	
	/**The webDriver object*/
	@Getter @Setter
	WebDriver driver;

	
	@Override
	public void getTargetHost(String url) {
        driver.get(url);
	}

	@Override
	public <T extends WebDriver> T getDriverInstance() {
		return (T) getDriver();
	}
	
	@Override
	public void quit(CloseSession type) {
		switch (type) {
		case QUIT:
			driver.quit();
			break;
		case CLOSE:
			driver.close();
			break;
		default:
			driver.quit();
			break;
		}
	} 

	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController<T> clickTo(Object locator, long timeout) {
		waitFor.waitForElementToBeClickable(driver, timeout, (String)locator).click();
		return this;
	}

	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController<T> enterTo(Object locator, String text, long timeout) {
		waitFor.waitForElementPresence(driver, timeout, (String)locator).sendKeys(text);
		return this;
	}

}
