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


import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.thoughtworks.selenium.Selenium;


/**
 * Custom objects per session
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 */
@Slf4j
@SuppressWarnings("deprecation")
public class SessionProperties<T extends RemoteWebDriver> {

    /**Factories Strategy*/
    @Autowired
    StrategyFactory<?> factoryStrategy;

    /**Map with various controllers for test session*/
    @Getter @Setter
    Map<Class<?>,Object> controllers;

    /**The web driver context*/
    @Getter @Setter
    AnnotationConfigApplicationContext driverContext;

    /**Timeout for waiting until condition fullfilled */
    @Getter @Setter
    int waitUntil = 5;

    /**The remoteWebDriver object*/
    @Getter @Setter
    T webDriver;

    /**The selenium object*/
    @Getter @Setter
    Selenium selenium;

    /** Wait Strategy*/
    @Getter @Setter
    String waitStrategy="DEFAULT";

    /** WebDriver-Selenium element strategy*/
    @Getter @Setter
    String elementStrategy="webDriverElement";

    /** WebDriver-Selenium options strategy*/
    @Getter @Setter
    String optionsStrategy="webDriverOptions";

    /** WebDriver-Selenium windows strategy*/
    @Getter @Setter
    String windowsStrategy="webDriverWindows";

    /** WebDriver-Selenium actions strategy*/
    @Getter @Setter
    String actionsStrategy="webDriverActions";

    /**WebElement per session*/
    @Getter @Setter
    WebElement webElement;

    /**
     * Initialize objects per session and close session!!!
     */
    public void cleanSession(){

        //Quits driver
        if(webDriver!=null){
            factoryStrategy.getWindowsControllerStrategy(windowsStrategy).quit(CloseSession.QUIT);
        }

        //Initialize Controllers Array List
        controllers.clear();

        //Destroy the webdriver application context
        driverContext.destroy();

        log.info("Session closed!!!");
    }
}
