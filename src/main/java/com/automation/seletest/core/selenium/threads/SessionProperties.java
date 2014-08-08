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
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.automation.seletest.core.selenium.webAPI.WebController;
import com.automation.seletest.core.selenium.webAPI.WebController.CloseSession;
import com.automation.seletest.core.services.PerformanceUtils;
import com.automation.seletest.core.testNG.assertions.Assertion;


/**
 * Custom objects per session
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class SessionProperties {

    /**Performance api*/
    @Getter @Setter
    PerformanceUtils performanceUtils;

    /** Array List to store various object*/
    @Getter @Setter
    ArrayList controllers;

    /**The web driver context*/
    @Getter @Setter
    AnnotationConfigApplicationContext driverContext;

    /** The Assertion. */
    @Getter @Setter
    Assertion assertion;

    /**The actions builder instance*/
    @Getter @Setter
    Actions actions;

    /**Timeout for waiting until condition fullfilled */
    @Getter @Setter
    int waitUntil = 5;

    /** Wait Strategy*/
    @Getter @Setter
    String waitStrategy="DEFAULT";

    /**WebElement per session*/
    @Getter @Setter
    WebElement webElement;

    public void cleanSession() throws Exception{

        //Performance collection data
        if(performanceUtils!=null){
            performanceUtils.getPerformanceData(performanceUtils.getServer());
            performanceUtils.writePerformanceData(new File("./target/test-classes/logs/").getAbsolutePath(), performanceUtils.getHar());
            performanceUtils.stopServer(performanceUtils.getServer());
            performanceUtils=null;
            log.info("Performance data collected!!!");
        }

        //Quits driver
        for(int i=0; i<controllers.size(); i++)
        {
            Object controller = controllers.get(i);
            if(controller instanceof WebController<?> && controller !=null){
                ((WebController) controller).quit(CloseSession.QUIT);
            }
        }

        //Initialize Objects of Array List
        if(controllers!=null){
            controllers.clear();
        }

        //Initialize actions Builder
        actions=null;

        //Destroy the webdriver application context
        driverContext.destroy();

        //Turn waitStrategy to default after closing a session
        waitStrategy="DEFAULT";

        log.info("Session closed!!!");
    }
}
