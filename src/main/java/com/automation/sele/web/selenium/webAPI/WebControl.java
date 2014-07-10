/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;

import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the implementation of webDriver API 
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class WebControl implements WebInteraction{

	/**The webDriver object*/
	@Getter @Setter WebDriver driver;
	
	/**The Javascript executor object*/
	@Getter @Setter JavaScriptExecutor JSexec;
	
	/* (non-Javadoc)
	 * @see com.automation.sele.web.selenium.webAPI.WebInteraction#clickTo(com.automation.sele.web.selenium.webAPI.WebInteraction.ClickType, java.lang.Object)
	 */
	@Override
	public WebControl clickTo(ClickType type, Object locator) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.automation.sele.web.selenium.webAPI.WebInteraction#enterTo(com.automation.sele.web.selenium.webAPI.WebInteraction.InsertType, java.lang.Object, java.lang.String)
	 */
	@Override
	public WebControl enterTo(InsertType type, Object locator, String text) {
		// TODO Auto-generated method stub
		return null;
	}

}
