/**
 * 
 */
package com.automation.sele.web.selenium.session;

import com.automation.sele.web.selenium.threads.SessionContext;
import com.automation.sele.web.selenium.webAPI.WebActionsController;
import com.automation.sele.web.testNG.assertions.Assertion;

/**
 * This class returns all the interfaces - objects used for testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class SessionControl {

	
	public static <T> WebActionsController<?> actionsController(){
		return SessionContext.getSession().getActionscontroller();
	}
	
	public static Assertion assertion(){
		return SessionContext.getSession().getAssertion();
	}
	
}
