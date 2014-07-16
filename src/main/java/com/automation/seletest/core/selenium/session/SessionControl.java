/**
 *
 */
package com.automation.seletest.core.selenium.session;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.ActionsController;
import com.automation.seletest.core.testNG.assertions.Assertion;

/**
 * This class returns all the interfaces - objects used for testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class SessionControl {


    public static <T> ActionsController<?> actionsController(){
        return SessionContext.getSession().getActionscontroller();
    }

    public static Assertion assertion(){
        return SessionContext.getSession().getAssertion();
    }


}
