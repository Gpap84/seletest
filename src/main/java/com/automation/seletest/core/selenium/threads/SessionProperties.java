package com.automation.seletest.core.selenium.threads;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.automation.seletest.core.selenium.webAPI.ActionsController;
import com.automation.seletest.core.selenium.webAPI.ActionsController.CloseSession;
import com.automation.seletest.core.services.Performance;
import com.automation.seletest.core.testNG.assertions.Assertion;


/**
 * Custom objects per session
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class SessionProperties {

    @Getter @Setter
    Performance performance;

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
    int waitUntil = 5;

    @Getter @Setter
    String waitStrategy="DEFAULT";

    public void cleanSession() throws Exception{

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

        //performance measurement
        if(performance!=null){
            performance.getPerformanceData(performance.getServer());
            performance.writePerformanceData("./target/test-classes/test.txt", performance.getHar());
            performance.stopServer(performance.getServer());
            performance=null;
            log.info("Performance data collected!!!");
        }

        log.info("Session closed");
    }
}
