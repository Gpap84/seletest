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
package com.automation.seletest.core.selenium.configuration;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.interactions.Actions;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.WebController;
import com.automation.seletest.core.services.webSync.WaitFor;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.assertions.Assert;
import com.thoughtworks.selenium.Selenium;

/**
 * This class returns all the interfaces - objects used for testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public final class SessionControl {

    /**
     * AssertTest
     * @return AssertTest instance
     */
    public static Assert<?> verifyController(){
        return SessionContext.session().getAssertion();
    }

    /**
     * Actions builder
     * @return Actions instance
     */
    public static Actions actionsBuilder(){
        return SessionContext.session().getActions();
    }


    /**
     * TouchAction builder
     * @return TouchAction instance
     */
    public static TouchAction touchactionsBuilder(){
        return SessionContext.session().getTouchAction();
    }

    /**
     * Selenium
     * @return Selenium instance
     */
    public static Selenium selenium(){
        return SessionContext.session().getSelenium();
    }

    /**
     * Wait Strategy
     * @return WaitFor
     */
    public static WaitFor waitController() {
        return ApplicationContextProvider.getApplicationContext().getBean(StrategyFactory.class).getWaitStrategy(SessionContext.getSession().getWaitStrategy());
    }

    /**
     * Web Strategy
     * @return WebController
     */
    public static WebController webController() {
        return ApplicationContextProvider.getApplicationContext().getBean(StrategyFactory.class).getControllerStrategy(SessionContext.getSession().getControllerStrategy());
    }
}
