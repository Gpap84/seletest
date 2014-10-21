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
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.interactions.Actions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestClass;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.PerformanceUtils;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.PostConfiguration;
import com.automation.seletest.core.testNG.PreConfiguration;
import com.automation.seletest.core.testNG.assertions.AssertTest;
import com.automation.seletest.core.testNG.assertions.SoftAssert;

@Slf4j
@SuppressWarnings("unchecked")
public class InitListener implements IInvokedMethodListener{

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        PreConfiguration preconfigure = null;
        ITestClass testClass=method.getTestMethod().getTestClass();
        Class<?> webClass=testClass.getRealClass();

        SeleniumTest seleniumTest=AnnotationUtils.findAnnotation(method.getTestMethod().getTestClass().getRealClass(), SeleniumTest.class);

        if(testResult.getMethod().isBeforeClassConfiguration() && testResult.getMethod().getMethodName().equalsIgnoreCase("beforeClass")) {
            preconfigure=webClass.getAnnotation(PreConfiguration.class);
        }

        if(method.getTestMethod().isTest()){
            if(method.getTestMethod().isTest()){
                SessionContext.session().setVerifications(new ArrayList<Future<Boolean>>());
                log.debug("Set assertion type parameter for test method: {}!!!",method.getTestMethod().getMethodName());
                testResult.setAttribute("session", SessionContext.session());
                Reporter.setCurrentTestResult(testResult);
                SessionContext.session().setAssertion(ApplicationContextProvider.getApplicationContext().getBean(AssertTest.class));
                SessionContext.session().setActions(new Actions(SessionContext.session().getWebDriver()));
                seleniumTest=AnnotationUtils.findAnnotation(method.getTestMethod().getConstructorOrMethod().getMethod(), SeleniumTest.class);
                SessionControl.verifyController().setAssertionType(seleniumTest.assertion());
                preconfigure = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(PreConfiguration.class);
            }
            if(seleniumTest!=null){
                if(seleniumTest.driver().equals(DriverType.WEBDRIVER)) {
                    SessionContext.session().setControllerStrategy("webDriverControl");
                    SessionContext.session().setActionsStrategy("webDriverActions");
                } else if(seleniumTest.driver().equals(DriverType.SELENIUM) && method.getTestMethod().getCurrentInvocationCount()==0) {
                    SessionContext.session().setControllerStrategy("seleniumControl");
                    SessionContext.session().setWaitStrategy("seleniumWait");
                    SessionContext.session().setActionsStrategy("seleniumActions");
                }
            }
        }

        //Set session as testNG attribute for after configuration methods
        if((testResult.getTestContext().getCurrentXmlTest().getParallel()==null || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("false")==0 || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("tests")==0) && method.getTestMethod().isAfterTestConfiguration()) {
            testResult.setAttribute("session", SessionContext.session());
        } else if(method.getTestMethod().isAfterClassConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("classes")==0){
            testResult.setAttribute("session", SessionContext.session());
        } else if (method.getTestMethod().isAfterMethodConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("methods")==0) {
            testResult.setAttribute("session", SessionContext.session());
        }

        //Execute Method from Page Object or Page Facade prior to @Test execution
        if(preconfigure!=null && method.getTestMethod().getCurrentInvocationCount()==0) {
            log.debug("Preconfiguration steps will be executed now for @Test {} !!!",method.getTestMethod().getMethodName());
            executionConfiguration(preconfigure);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        PostConfiguration postconfigure = null;
        ITestClass testClass=method.getTestMethod().getTestClass();
        Class<?> webClass=testClass.getRealClass();

        if(testResult.getMethod().isAfterClassConfiguration() && testResult.getMethod().getMethodName().equalsIgnoreCase("afterClass")) {
            postconfigure=webClass.getAnnotation(PostConfiguration.class);
        }

        if(method.getTestMethod().isTest()){

            //Wait for verifications to complete before finishing @Test (only for SoftAssert)
            if(SessionContext.getSession().getAssertion().getAssertion() instanceof SoftAssert){
                for(Future<?> a : (ArrayList<Future<?>>)SessionContext.getSession().getVerifications()){
                    while(!a.isDone()){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            log.error("Exception waiting Future task to complete: "+e);
                        }
                    }
                }
            }

            postconfigure = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(PostConfiguration.class);
            PerformanceUtils perf=SessionContext.session().getPerformance();
            SessionControl.verifyController().assertAll();
            if(perf!=null){
                perf.getPerformanceData(perf.getServer());
                perf.writePerformanceData(new File("./target/surefire-reports/logs/"+testResult.getName()+".har").getAbsolutePath(), perf.getHar());
                perf.stopServer(perf.getServer());
                log.debug("Performance data collected for test method: {} !!!",method.getTestMethod().getMethodName());
            }
        }

        //Set session as testNG attribute for Before configuration methods
        if(testResult.getTestContext().getCurrentXmlTest().getParallel()==null || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("false")==0 || testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("tests")==0 &&method.getTestMethod().isBeforeTestConfiguration()) {
            testResult.setAttribute("session", SessionContext.session());
        } else if(method.getTestMethod().isBeforeClassConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("classes")==0){
            testResult.setAttribute("session", SessionContext.session());
        } else if (method.getTestMethod().isBeforeMethodConfiguration() && testResult.getTestContext().getCurrentXmlTest().getParallel().compareTo("methods")==0) {
            testResult.setAttribute("session", SessionContext.session());
        }

        //Execute Method from Page Object or Page Facade prior to @Test execution
        if(postconfigure!=null && method.getTestMethod().getCurrentInvocationCount()==0) {
            log.debug("Postconfiguration steps will be executed now for @Test {} !!!",method.getTestMethod().getMethodName());
            executionConfiguration(postconfigure);
        }
    }

    /**
     * Execute PreConfiguration
     * @param configure
     * @throws SkipException
     */
    private void executionConfiguration(Object configure) throws SkipException{
        try{
            String method="";
            Class<?>  classRef=null;
            if(configure instanceof PreConfiguration){
                method=((PreConfiguration)configure).method();
                classRef=((PreConfiguration)configure).classReference();
            } else if(configure instanceof PostConfiguration){
                method=((PostConfiguration)configure).method();
                classRef=((PostConfiguration)configure).classReference();
            }
            Class<?> invokedClass=ApplicationContextProvider.getApplicationContext().getBean(classRef).getClass();
            Constructor<?> constructor = invokedClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object innerObject = constructor.newInstance();
            for (Method m:invokedClass.getMethods()){
                if(m.getName().equalsIgnoreCase(method)) {
                    ReflectionUtils.invokeMethod(m,innerObject);
                }
            }
            log.debug("{} steps executed successfully for!!!", configure instanceof PreConfiguration ? "Preconfiguration" : "Postconfiguration");
        } catch (Exception e) {
            log.error("Skip the test because of failure to preconfiguration with exception "+e.getLocalizedMessage()+"!!");
            throw new SkipException("Skip the test because of failure to configuration of test with message: "+e.getCause().toString()+"!!");
        }
    }
}
