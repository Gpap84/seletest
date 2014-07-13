package com.automation.sele.web.selenium.threads;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.automation.sele.web.selenium.webAPI.WebActionsController;
import com.automation.sele.web.selenium.webAPI.WebActionsController.CloseSession;
import com.automation.sele.web.testNG.assertions.Assertion;


/**
 * Custom objects per session
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class SessionProperties {

    /** The current thread*/
    @Getter @Setter
    Thread thread;

    /**The number of soft failures*/
    @Getter @Setter
    int softFailures;

    /** The web actions controller. */
    @Getter @Setter
    WebActionsController<?> actionscontroller;

    /** The Assertion. */
    @Getter @Setter
    Assertion assertion;

    /**Timeout for waiting an element to be present in screen */
    @Getter @Setter
    int waitForElement = 10;

    /**Timeout for waiting an event to occur */
    @Getter @Setter
    long waitForEvent = 20000;

    /**Timeout for waiting an element to be invisible */
    @Getter @Setter
    int waitForElementInvisibility = 5;

    /**Locality*/
    @Getter @Setter
    String locality;


    public void cleanSession(){
    	
    	if(actionscontroller!=null){
    		actionscontroller.quit(CloseSession.QUIT);
    	}
    	
        softFailures=0;
        log.info("Session closed");
        Thread.currentThread().setName("CoreFramework - session closed!!!");
    }
}
