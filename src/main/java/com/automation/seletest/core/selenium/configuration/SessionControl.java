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

import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.WebController;
import com.automation.seletest.core.testNG.assertions.AssertTest;

/**
 * This class returns all the interfaces - objects used for testing
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class SessionControl {

    /**
     * webController for this test instance
     * @return WebController<?>
     */
    public static <T> WebController<?> webController(){
        for(int i=0; i < SessionContext.getSession().getControllers().size(); i++) {
            Object controller = SessionContext.getSession().getControllers().get(i);
            if(controller !=null && controller instanceof WebController){
                return (WebController<?>) controller;
            }
        }
        return null;
    }

    /**
     * appiumController for this test instance
     * @return AppiumController<?>
     */
    public static <T> AppiumController<?> appiumController(){
        for(int i=0; i < SessionContext.getSession().getControllers().size(); i++) {
            Object controller = SessionContext.getSession().getControllers().get(i);
            if(controller !=null && controller instanceof AppiumController){
                return (AppiumController<?>) controller;
            }
        }
        return null;
    }

    /**
     * verifyController for this test instance
     * @return AssertTest instance
     */
    public static AssertTest<?> verifyController(){
        return (AssertTest<?>) SessionContext.getSession().getTestProperties().get(AssertTest.class);
    }

}
