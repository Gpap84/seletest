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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;

import com.automation.seletest.core.listeners.beanUtils.Events.InitializationEvent;
import com.automation.seletest.core.listeners.beanUtils.Events.TestNGEvent;
import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.services.utilities.PerformanceUtils;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.testNG.assertions.Assert;
import com.thoughtworks.selenium.Selenium;

/*
 * ApplicationListener for event handling
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings({"deprecation","unchecked"})
@Component
@Slf4j
public class EventListener implements ApplicationListener<ApplicationEvent> {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		Time sessionTime=new Time(event.getTimestamp());
		try {
			if (event instanceof InitializationEvent) {
				log.debug(((InitializationEvent) event).getMessage(), sessionTime);
				new Initialize().initializeSession(event);
			} else if(event instanceof TestNGEvent) {
				log.debug(((TestNGEvent) event).getMessage(), sessionTime);
				SessionContext.session().setVerifications(new ArrayList<Future<Boolean>>());
				SessionContext.session().setAssertion(ApplicationContextProvider.getApplicationContext().getBean(Assert.class));
				SessionContext.session().setActions(new Actions(SessionContext.session().getWebDriver()));
				SessionControl.verifyController().setAssertionType(((TestNGEvent) event).getTest().assertion());

				if(((TestNGEvent) event).getTest()!=null){
					log.debug("Setting aliases for --> {}", ((TestNGEvent) event).getTest().toString());
					if(((TestNGEvent) event).getTest().driver().equals(DriverType.WEBDRIVER)) {
						SessionContext.session().setControllerStrategy("webDriverControl");
						SessionContext.session().setActionsStrategy("webDriverActions");
					} else if(((TestNGEvent) event).getTest().driver().equals(DriverType.SELENIUM)) {
						SessionContext.session().setControllerStrategy("seleniumControl");
						SessionContext.session().setWaitStrategy("seleniumWait");
						SessionContext.session().setActionsStrategy("seleniumActions");
					}
				}
			}
		}
		catch (Exception e) {
			log.error("Error in Test Initialization phase with exception: {}", e.getMessage());
		}
	}

	/**
	 * Initialization class
	 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
	 *
	 */
	@Configurable
	static class Initialize {

		@Autowired
		AppiumController<?> mobileControl;
		
		@Autowired
		Environment env;

		/**
		 * Initialize Web or Mobile session
		 * @param event
		 * @throws Exception
		 */
		public void initializeSession(ApplicationEvent event) throws Exception{
			WebDriver driver=null;
			Selenium selenium=null;
			ITestContext textcontext=((InitializationEvent) event).getTestcontext();

			String gridHost=textcontext.getCurrentXmlTest().getParameter(env.getProperty("grid_host"));
			String gridPort=textcontext.getCurrentXmlTest().getParameter(env.getProperty("grid_port"));
			String profileDriver=textcontext.getCurrentXmlTest().getParameter(env.getProperty("profile_Web"));
			String appPath = textcontext.getCurrentXmlTest().getParameter(env.getProperty("app_path"));
			String appPackage = textcontext.getCurrentXmlTest().getParameter(env.getProperty("app_package"));
			String autoLaunch=textcontext.getCurrentXmlTest().getParameter(env.getProperty("auto_lauch"));
			String appActivity = textcontext.getCurrentXmlTest().getParameter(env.getProperty("app_act"));
			String udid = textcontext.getCurrentXmlTest().getParameter(env.getProperty("udid"));

			DesiredCapabilities cap = (DesiredCapabilities) ApplicationContextProvider.getApplicationContext().getBean(env.getProperty("selenium_cap"));

			/**Capabilities for android-ios appium*/
			if(profileDriver.contains("android")) {
				DesiredCapabilities androidcap =  (DesiredCapabilities) ApplicationContextProvider.getApplicationContext().getBean(env.getProperty("android_cap"),new Object[] {appPath,appActivity,appPackage,autoLaunch});
				cap.merge(androidcap);
			} else if (profileDriver.contains("iOS")) {
				DesiredCapabilities ioscap =  (DesiredCapabilities) ApplicationContextProvider.getApplicationContext().getBean(env.getProperty("ios_cap"),new Object[] {appPath,udid,appPackage,autoLaunch});
				cap.merge(ioscap);
			}

			/**Performance with browser-mob proxy for local environment*/
			if(((InitializationEvent) event).isPerformance() && !profileDriver.contains("Grid")){
				PerformanceUtils performance = ApplicationContextProvider.getApplicationContext().getBean(PerformanceUtils.class);
				int proxyPort=new Random().nextInt(5000);
				performance.proxyServer(proxyPort);
				performance.newHar("Har created at: "+ new Time(event.getTimestamp()));
				cap.setCapability(CapabilityType.PROXY, performance.proxy(proxyPort));
				SessionContext.session().setPerformance(performance);
			}

			if(profileDriver.contains("Grid")) {
				driver=(WebDriver) ApplicationContextProvider.getApplicationContext().getBean(profileDriver, new Object[]{gridHost+":"+gridPort+"/wd/hub",cap});
			} else {
				driver=(WebDriver) ApplicationContextProvider.getApplicationContext().getBean(profileDriver, new Object[]{cap});
			}

			if(driver instanceof RemoteWebDriver && !(driver instanceof AppiumDriver)) {
				driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
				SessionContext.session().setWebDriver((RemoteWebDriver)driver);
				selenium=(Selenium)ApplicationContextProvider.getApplicationContext().getBean("selenium", new Object[] {driver,((InitializationEvent) event).getHostUrl()});
				SessionContext.session().setSelenium(selenium);
				driver.get(((InitializationEvent) event).getHostUrl());
			} else {
				if(driver instanceof AndroidDriver) {
					SessionContext.session().setWebDriver((AndroidDriver)driver);
				} else if(driver instanceof IOSDriver){
					SessionContext.session().setWebDriver((IOSDriver)driver);
				}
				SessionContext.session().setTouchAction(new TouchAction((AppiumDriver) driver));
				mobileControl.installApp(appPath,appPackage);
				if(!Boolean.parseBoolean(textcontext.getCurrentXmlTest().getParameter(autoLaunch))){
					mobileControl.launchApp();
				}
			}
			SessionContext.setSessionProperties();

		}
	}

}