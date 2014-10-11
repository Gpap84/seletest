/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
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
package com.automation.seletest.core.listeners.beanUtils;

import java.util.logging.Level;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.automation.seletest.core.services.properties.CoreProperties;

/**
 * DriverBeanPostProcessor class
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class DriverBeanPostProcessor implements BeanPostProcessor{

    /**
     * Actions before initializing beans
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Actions after initializing beans
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String browserType=null;
        String clientLogs=null;
        ITestResult result=Reporter.getCurrentTestResult();
        if(result!=null) {
            browserType=Reporter.getCurrentTestResult().getMethod().getTestClass().getXmlTest().getAllParameters().get(CoreProperties.BROWSERTYPE.get());
            clientLogs=Reporter.getCurrentTestResult().getMethod().getTestClass().getXmlTest().getAllParameters().get(CoreProperties.CLIENTLOGS.get());
        }

        if(bean instanceof DesiredCapabilities) {

            /**Collect Javascript console errors*/
            if(clientLogs!=null && Boolean.parseBoolean(clientLogs)) {
                LoggingPreferences loggingprefs = new LoggingPreferences();
                loggingprefs.enable(LogType.BROWSER, Level.ALL);// Javascript console errors
                DesiredCapabilities logCaps=new DesiredCapabilities();
                logCaps.setCapability(CapabilityType.LOGGING_PREFS,loggingprefs);
                ((DesiredCapabilities) bean).merge(logCaps);
            }

            /**Defines browser capability for selenium grid requests*/
            if(browserType!=null) {
                if(browserType.compareTo("chrome") == 0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.chrome());
                } else if(browserType.compareTo("firefox") == 0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.firefox());
                } else if(browserType.compareTo("ie") == 0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.internetExplorer());
                } else if(browserType.compareTo("phantomJs") == 0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.phantomjs());
                }
            }

        }
        return bean;
    }

}
