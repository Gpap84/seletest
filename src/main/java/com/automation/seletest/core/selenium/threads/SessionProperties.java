/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.automation.seletest.core.selenium.threads;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.webAPI.ActionsController;
import com.automation.seletest.core.selenium.webAPI.ActionsController.CloseSession;
import com.automation.seletest.core.services.PerformanceUtils;
import com.automation.seletest.core.testNG.assertions.Assertion;


/**
 * Custom objects per session
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class SessionProperties {

    @Getter @Setter
    PerformanceUtils performanceUtils;

    /**The number of soft failures*/
    @Getter @Setter
    int softFailures;

    /** The web actions interface. */
    @Getter @Setter
    ActionsController<?> webactionscontroller;

    /** The mobile actions interface*/
    @Getter @Setter
    AppiumController<?> appiumactionsController;

    /**The web driver context*/
    @Getter @Setter
    AnnotationConfigApplicationContext driverContext;

    /** The Assertion. */
    @Getter @Setter
    Assertion assertion;

    /** The xml parameters. */
    @Getter @Setter
    Map<String, String> xmlParams = new HashMap<String, String>();

    /**Timeout for waiting until condition fullfilled */
    @Getter @Setter
    int waitUntil = 5;

    @Getter @Setter
    String waitStrategy="DEFAULT";

    public void cleanSession() throws Exception{

        //performance measurement
        if(performanceUtils!=null){
            performanceUtils.getPerformanceData(performanceUtils.getServer());
            performanceUtils.writePerformanceData(new File("./target/test-classes/logs/").getAbsolutePath(), performanceUtils.getHar());
            performanceUtils.stopServer(performanceUtils.getServer());
            performanceUtils=null;
            log.info("Performance data collected!!!");
        }

        //quit driver
        if(webactionscontroller!=null){
            webactionscontroller.quit(CloseSession.QUIT);
        }

        //initialize assertion
        assertion=null;

        //destroy the webdriver application context
        driverContext.destroy();

        //turn waitStrategy to default after closing a session
        waitStrategy="DEFAULT";

        //reset soft failures
        softFailures=0;

        log.info("Session closed!!!");
    }
}
