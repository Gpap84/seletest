
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

import java.util.Stack;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.target.ThreadLocalTargetSource;

import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.mobileAPI.AppiumDriverController;
import com.automation.seletest.core.selenium.webAPI.WebController;
import com.automation.seletest.core.selenium.webAPI.WebDriverController;
import com.automation.seletest.core.spring.ApplicationContextProvider;


/**
 * SessionContext
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class SessionContext {


    /**
     * Get the thread in parallel execution from a target Source
     * @return
     */
    public static SessionProperties getSession(){
        return (SessionProperties) innerContext("threadLocalTargetSource").getTarget();
    }
    /**
     * Return the ThreadLocalTargetSource
     * @param targetBean
     * @return
     */
    protected static ThreadLocalTargetSource innerContext(String targetBean) {
        return (ThreadLocalTargetSource) ApplicationContextProvider.getApplicationContext().getBean(targetBean);
    }

    /**
     * Destroy instances of the thread
     * @throws Exception
     */
    public static void cleanSession() throws Exception{
        threadStack.removeElement(getSession());//remove element from thread stack
        log.debug("*********************Object removed from thread stack, new size is: {}*****************************",threadStack.size());
        getSession().cleanSession();
        innerContext("threadLocalTargetSource").releaseTarget(getSession());
        innerContext("threadLocalTargetSource").destroy();
    }

    /**
     * Log thread instance
     * @param sessionObjects
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void setSessionProperties(){
        threadStack.push(getSession());//push instanse of Session to stack
        String driver="";
        if(getSession().controllers.get(WebController.class)!=null) {
            driver=((WebDriverController)getSession().controllers.get(WebController.class)).getWebDriver().toString().split(":")[0];
        } else if(getSession().controllers.get(AppiumController.class)!=null) {
            driver=((AppiumDriverController)getSession().controllers.get(AppiumController.class)).getAppiumDriver().toString().split(":")[0];
        }
        log.info("Session started with type of driver: {}", driver);
        Thread.currentThread().setName("SeletestFramework ["+driver+"] - session Active "+System.currentTimeMillis()%2048);
    }

    /**Clean all active threads stored in stack
     * @throws Exception
     *
     */
    public static void cleanSessionsFromStack() throws Exception {
        for(int i=0; i < threadStack.size();i++){
            SessionContext.stopSession(i);
        }
    }
    /**
     * Clean specific thread from a Stack with threads
     * @param index
     * @throws Exception
     */
    public static void stopSession(int index) throws Exception {
        threadStack.get(index).cleanSession();
        innerContext("threadLocalTargetSource").destroy();
        threadStack.removeElement(threadStack.get(index));
        log.debug("*********************Object removed from thread stack, new size is: {}*****************************",threadStack.size());
    }



    /**Stack for storing instances of thread objects*/
    @Getter @Setter
    public static Stack<SessionProperties> threadStack = new Stack<SessionProperties>();

}
