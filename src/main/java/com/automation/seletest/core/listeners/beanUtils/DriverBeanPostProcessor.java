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

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    /**
     * Actions after initializing beans
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        /**
         * Set browser capabilities
         */
        if(bean instanceof DesiredCapabilities) {
            String browserType=Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.BROWSERTYPE.get());
            if(browserType!=null){
                if(browserType.compareTo("chrome")==0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.chrome());
                } else if(browserType.compareTo("firefox")==0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.firefox());
                } else if(browserType.compareTo("ie")==0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.internetExplorer());
                } else if(browserType.compareTo("phantomJs")==0) {
                    ((DesiredCapabilities) bean).merge(DesiredCapabilities.phantomjs());
                }
            }
        }
        return bean;
    }

}
