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


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

import java.sql.Time;
import java.util.HashMap;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;

import com.automation.seletest.core.listeners.beanUtils.DriverBeanPostProcessor;
import com.automation.seletest.core.listeners.beanUtils.Events.InitializationEvent;
import com.automation.seletest.core.selenium.configuration.LocalDriverConfiguration;
import com.automation.seletest.core.selenium.configuration.RemoteDriverConfiguration;
import com.automation.seletest.core.selenium.configuration.WebDriverConfiguration;
import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.WebController;
import com.automation.seletest.core.services.PerformanceUtils;
import com.automation.seletest.core.services.properties.CoreProperties;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.assertions.AssertTest;

/**
 * ApplicationListener for event handling
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Slf4j
public class EventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Time sessionTime=new Time(event.getTimestamp());
        try {
            if (event instanceof InitializationEvent) {
                log.info(((InitializationEvent) event).getMessage(), sessionTime);
                new Initialize().initializeSession(event);
            }
        }
        catch (Exception e) {
            log.error("Error in Test Initialization phase "+e);
        }
    }

    /**
     * Initialization class
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     *
     */
    @SuppressWarnings({ "unchecked" })
    @Configurable
    static class Initialize {

        @Autowired
        WebController<?> webControl;

        @Autowired
        AppiumController<?> mobileControl;

        /**
         * Initialize Web or Mobile session
         * @param event
         * @throws Exception
         */
        public void initializeSession(ApplicationEvent event) throws Exception{
            WebDriver driver=null;
            SessionContext.getSession().setControllers(new HashMap<Class<?>, Object>());
            ITestContext textcontext=((InitializationEvent) event).getTestcontext();

            /*************************
             **XML PARAMETERS*********
             *************************/
            String gridHost=textcontext.getCurrentXmlTest().getParameter(CoreProperties.GRID_HOST.get());
            String gridPort=textcontext.getCurrentXmlTest().getParameter(CoreProperties.GRID_PORT.get());
            String profileDriver=textcontext.getCurrentXmlTest().getParameter(CoreProperties.PROFILEDRIVER.get());
            String bundleId=textcontext.getCurrentXmlTest().getParameter(CoreProperties.BUNDLEID.get());
            String appPath=textcontext.getCurrentXmlTest().getParameter(CoreProperties.APP_PATH.get());

            //Create Application Context for initializing driver based on specified @Profile
            AnnotationConfigApplicationContext app=new AnnotationConfigApplicationContext();
            app.getEnvironment().setActiveProfiles(new String[]{profileDriver});

            //register Configuration classes
            app.register(LocalDriverConfiguration.class,WebDriverConfiguration.class,RemoteDriverConfiguration.class);

            //start Container for bean initialization
            app.refresh();

            //register a bean post processor for merging capabilities
            app.getBeanFactory().addBeanPostProcessor(new DriverBeanPostProcessor());

            DesiredCapabilities cap = (DesiredCapabilities) app.getBean(CoreProperties.CAPABILITIES.get());

            //If performance is enabled
            if(((InitializationEvent) event).isPerformance()){
                PerformanceUtils perf = ApplicationContextProvider.getApplicationContext().getBean(PerformanceUtils.class);
                int proxyPort=new Random().nextInt(5000);
                perf.proxyServer(proxyPort);
                perf.newHar("Har created at: "+ new Time(event.getTimestamp()));
                cap.setCapability(CapabilityType.PROXY, perf.proxy(proxyPort));
                SessionContext.getSession().getControllers().put(PerformanceUtils.class,perf);
            }

            //start a driver object with capabilities
            if(profileDriver.contains("Grid")) {
                driver=(WebDriver) app.getBean(CoreProperties.PROFILEDRIVER.get(), new Object[]{gridHost+":"+gridPort+"/wd/hub",cap});
            } else {
                driver=(WebDriver) app.getBean(CoreProperties.PROFILEDRIVER.get(), new Object[]{cap});
            }

            if(((InitializationEvent) event).isWeb()) {
                SessionContext.getSession().setWebDriver((RemoteWebDriver)driver);
                webControl.goToTargetHost(((InitializationEvent) event).getHostUrl());
            } else {
                SessionContext.getSession().setWebDriver((AppiumDriver)driver);
                SessionContext.getSession().getControllers().put(TouchAction.class, new TouchAction((AppiumDriver)SessionContext.getSession().getWebDriver()));
                mobileControl.installApp(bundleId,appPath);
                mobileControl.launchApp();
            }

            //Set objects for this test instance
            SessionContext.getSession().getControllers().put(AssertTest.class, ApplicationContextProvider.getApplicationContext().getBean(AssertTest.class));
            SessionContext.getSession().getControllers().put(Actions.class, new Actions(driver));
            SessionContext.getSession().setDriverContext(app);//set the new application context for WebDriver
            SessionContext.setSessionProperties();

        }
    }

}