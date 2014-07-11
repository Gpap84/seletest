/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.automation.sele.web.aspectJ.RetryIfFails;
import com.automation.sele.web.services.SynchronizeService;


import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the implementation of webDriver API 
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings("unchecked")
@Component
@Scope("prototype")
public class WebDriverActionsController implements WebActionsController{

	@Autowired
	SynchronizeService waitFor;
	
	@Inject
	Environment environment;
	
	/**The webDriver object*/
	@Getter @Setter WebDriver driver;
	
	
	@PostConstruct
	private void retries(){

	}
	
	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController clickTo(Object locator) {
		waitFor.waitForElementPresence(driver, 1, (String)locator).click();
		return this;
	}

	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController enterTo(Object locator, String text) {
		waitFor.waitForElementPresence(driver, 1, (String)locator).sendKeys(text);
		return this;
	}

}
