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

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * Configuration class for instantiating various beans from WebDriver API
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@SuppressWarnings("deprecation")
public class WebDriverConfiguration {

    @Lazy(true)
    @Bean(name="webdriverwait")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait wait(WebDriver driver){
        return new WebDriverWait(driver, SessionContext.getSession().getWaitUntil());
    }


    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities capabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        return capabilities;
    }

    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Selenium selenium(WebDriver driver, String baseUrl){
        return new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Profile("appiumAndroidGrid")
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities androidcapabilities(String appPath, String appActivity, String appPackage, String autolaunch){
        File app=new File(appPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");//how long (in seconds) Appium will wait for a new command from the client before assuming the client quit and ending the session
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("autoLaunch", Boolean.parseBoolean(autolaunch));
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY,appActivity);
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        return capabilities;
    }

    @Profile("appiumIOSGrid")
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities iOScapabilities(String appPath, String udid, String bundleId, String autolaunch){
        File app=new File(appPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");//how long (in seconds) Appium will wait for a new command from the client before assuming the client quit and ending the session
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("autoLaunch", Boolean.parseBoolean(autolaunch));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, bundleId);
        capabilities.setCapability("udid", udid);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        return capabilities;
    }

}
