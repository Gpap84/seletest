/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public interface WebInteraction {

	WebControl clickTo(ClickType type, Object locator);
	
	WebControl enterTo(InsertType type, Object locator, String text);
	
	enum ClickType{PRESS,CLICK,ACTIONS,JAVASCRIPT};
	enum InsertType{TYPE,JAVASCRIPT,KEYS};
}
