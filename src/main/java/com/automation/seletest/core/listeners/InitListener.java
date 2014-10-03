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

package com.automation.seletest.core.listeners;


import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.interactions.Actions;
import org.springframework.core.annotation.AnnotationUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.automation.seletest.core.selenium.configuration.SessionControl;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.LogUtils;
import com.automation.seletest.core.services.PerformanceUtils;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.assertions.AssertTest;

@Slf4j
@SuppressWarnings("unchecked")
public class InitListener implements IInvokedMethodListener{

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        SeleniumTest seleniumTest=AnnotationUtils.findAnnotation(method.getTestMethod().getTestClass().getRealClass(), SeleniumTest.class);

        //Set assertion type (Hard / Soft) and amount of time to wait for conditions () for this test method
        if(method.getTestMethod().isTest()){
            log.debug("Set assertion type and waitFor parameter for test method: {}!!!",method.getTestMethod().getMethodName());

            //Set session objects as testNG attribute
            testResult.setAttribute("session", SessionContext.session());

            SessionContext.session().getControllers().put(AssertTest.class, ApplicationContextProvider.getApplicationContext().getBean(AssertTest.class));
            SessionContext.session().getControllers().put(Actions.class, new Actions(SessionContext.session().getWebDriver()));

            seleniumTest=AnnotationUtils.findAnnotation(method.getTestMethod().getConstructorOrMethod().getMethod(), SeleniumTest.class);
            SessionControl.verifyController().setAssertionType(seleniumTest.assertion());
            SessionContext.session().setWaitUntil(seleniumTest.waitFor());

            if(seleniumTest.driver().equals(DriverType.WEBDRIVER)) {
                ApplicationContextProvider.getApplicationContext().getBean(LogUtils.class).info("Using WebDriver for this @Test", "color:blue");
                SessionContext.session().setControllerStrategy("webDriverControl");
                SessionContext.session().setActionsStrategy("webDriverActions");
            } else if(seleniumTest.driver().equals(DriverType.SELENIUM)) {
                ApplicationContextProvider.getApplicationContext().getBean(LogUtils.class).info("Using Selenium for this @Test", "color:blue");
                SessionContext.session().setControllerStrategy("seleniumControl");
                SessionContext.session().setWaitStrategy("seleniumWait");
                SessionContext.session().setActionsStrategy("seleniumActions");
            }
        }

        if(testResult.getTestContext().getCurrentXmlTest().getParallel()==null || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("false")==0 || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("tests")==0 &&method.getTestMethod().isAfterTestConfiguration()) {
            testResult.setAttribute("session", SessionContext.session());
        } else if(method.getTestMethod().isAfterClassConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("classes")==0){
            testResult.setAttribute("session", SessionContext.session());
        } else if (method.getTestMethod().isAfterMethodConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("methods")==0) {
            testResult.setAttribute("session", SessionContext.session());
        }

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.getTestMethod().isTest()){
            PerformanceUtils perf=(PerformanceUtils) SessionContext.session().getControllers().get(PerformanceUtils.class);
            SessionControl.verifyController().assertAll();

            //Performance collection data
            if(perf!=null){
                perf.getPerformanceData(perf.getServer());
                perf.writePerformanceData(new File("./target/surefire-reports/logs/"+testResult.getName()+".har").getAbsolutePath(), perf.getHar());
                perf.stopServer(perf.getServer());
                log.debug("Performance data collected for test method: {} !!!",method.getTestMethod().getMethodName());
            }
        }

        if(testResult.getTestContext().getCurrentXmlTest().getParallel()==null || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("false")==0 || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("tests")==0 &&method.getTestMethod().isBeforeTestConfiguration()) {
            testResult.setAttribute("session", SessionContext.session());
        } else if(method.getTestMethod().isBeforeClassConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("classes")==0){
            testResult.setAttribute("session", SessionContext.session());
        } else if (method.getTestMethod().isBeforeMethodConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("methods")==0) {
            testResult.setAttribute("session", SessionContext.session());
        }
    }

}
