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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

/**
 * RemoteDriverConfiguration class
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RemoteDriverConfiguration {


    @Configuration
    @Profile({"seleniumGrid"})
    public abstract static class ProfileSeleniumGrid implements ProfileDriver{

        private final String beanDescription="Web Session on Selenium Grid 2 is about to execute!!";

        @Override
        @Lazy(true)
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(String url, DesiredCapabilities cap) throws MalformedURLException{
            return new Augmenter().augment(new RemoteWebDriver(new URL(url),cap));
        }


        @PostConstruct
        public void init(){
            log.info(beanDescription);
        }

    }

    @Configuration
    @Profile({"appiumAndroidGrid"})
    public abstract static class ProfileAppiumAndroid implements ProfileDriver{

        private final String beanDescription="Appium session for Android on Selenium Grid 2 is about to execute!!";

        @Override
        @Lazy(true)
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(String url, DesiredCapabilities cap) throws MalformedURLException{
            return new AndroidDriver(new URL(url),cap);
        }

        @PostConstruct
        public void init(){
            log.info(beanDescription);
        }

    }

    @Configuration
    @Profile({"appiumIOSGrid"})
    public abstract static class ProfileAppiumIOS implements ProfileDriver{

        private final String beanDescription="Appium session for IOS on Selenium Grid 2 is about to execute!!";

        @Override
        @Lazy(true)
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(String url, DesiredCapabilities cap) throws MalformedURLException{
            return new IOSDriver(new URL(url),cap);
        }

        @PostConstruct
        public void init(){
            log.info(beanDescription);
        }

    }
}
