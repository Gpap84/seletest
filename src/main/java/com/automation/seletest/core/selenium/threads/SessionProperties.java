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



import io.appium.java_client.TouchAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.automation.seletest.core.selenium.webAPI.WebController.CloseSession;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.services.utilities.PerformanceUtils;
import com.automation.seletest.core.testNG.assertions.Assert;
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

    /**The wait until timeout*/
    @Getter @Setter
    int waitUntil = 5;

    /**The remoteWebDriver object*/
    @Getter @Setter
    T webDriver;

    /**The selenium object*/
    @Getter @Setter
    Selenium selenium;

    /**Actions class**/
    @Getter @Setter
    Actions actions;

    /**List of all asynchronous verifications**/
    @Getter @Setter
    ArrayList<Future<?>> verifications;

    /**Performance class**/
    @Getter @Setter
    PerformanceUtils performance;

    /**Assertions class**/
    @Getter @Setter
    Assert<?> assertion;

    /**TouchAction class**/
    @Getter @Setter
    TouchAction touchAction;

    /** Wait Strategy*/
    @Getter @Setter
    String waitStrategy="webDriverWait";

    /** WebDriver-Selenium controller strategy*/
    @Getter @Setter
    String controllerStrategy="webDriverControl";

    /** WebDriver-Selenium actions strategy*/
    @Getter @Setter
    String actionsStrategy="webDriverActions";

    /**WebElement per session*/
    @Getter @Setter
    WebElement webElement;

    /**List of web elements*/
    @Getter @Setter
    List<WebElement> webElements;

    /**
     * Initialize objects per session and close session!!!
     */
    public void cleanSession(){

        //Quits driver
        if(webDriver!=null){
            factoryStrategy.getControllerStrategy(controllerStrategy).quit(CloseSession.QUIT);
        }

        log.info("Session {} closed!!!", webDriver.toString().replace("(null)", ""));
    }
}
