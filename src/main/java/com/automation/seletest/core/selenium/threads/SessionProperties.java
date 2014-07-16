package com.automation.seletest.core.selenium.threads;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.automation.seletest.core.selenium.webAPI.ActionsController;
import com.automation.seletest.core.selenium.webAPI.ActionsController.CloseSession;
import com.automation.seletest.core.testNG.assertions.Assertion;


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

    /** The web actions interface. */
    @Getter @Setter
    ActionsController<?> actionscontroller;

    /**The web driver context*/
    @Getter @Setter
    AnnotationConfigApplicationContext driverContext;

    /** The Assertion. */
    @Getter @Setter
    Assertion assertion;

    /**Timeout for waiting until condition fullfilled */
    @Getter @Setter
    int waitUntil = 10;

    /**Timeout for waiting an event to occur */
    @Getter @Setter
    long waitForEvent = 20000;

    /**Locality*/
    @Getter @Setter
    String locality;

    @Getter @Setter
    String waitStrategy="DEFAULT";

    public void cleanSession(){

        //quit driver
        if(actionscontroller!=null){
            actionscontroller.quit(CloseSession.QUIT);
        }

        //initialize assertion
        assertion=null;

        //destroy the webdriver application context
        driverContext.destroy();

        //turn waitStrategy to default after closing a session
        waitStrategy="DEFAULT";

        //reset soft failures
        softFailures=0;

        log.info("Session closed");
    }
}
