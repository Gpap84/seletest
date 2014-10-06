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
package com.automation.seletest.core.selenium.mobileAPI;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.UnsupportedCommandException;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.services.annotations.Monitor;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;


/**
 * (AppiumDriverController api for iOS-Android native app interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class AppiumDriverController<T extends AppiumDriver> extends DriverBaseController<T> implements AppiumController<AppiumDriverController<T>>{

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController launchApp() {
        webDriver().launchApp();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController resetApp() {
        webDriver().resetApp();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController runAppinBackground(int sec) {
        webDriver().runAppInBackground(sec);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController closeApp() {
        webDriver().closeApp();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController installApp(String appPath, String bundleId) {
        if(!isAppInstalled(bundleId)) {
            Map<String, String> args = new HashMap<String, String>();
            args.put("appPath", appPath);
            webDriver().installApp(appPath);
        } return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController performTouchAction(TouchAction e) {
        webDriver().performTouchAction(e);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController performMultiTouchAction(MultiTouchAction e) {
        webDriver().performMultiTouchAction(e);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController hideKeyboard() {
        webDriver().hideKeyboard();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController rotate(ScreenOrientation e) {
        webDriver().rotate(e);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public ScreenOrientation getscreen() {
        return webDriver().getOrientation();
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public MultiTouchAction getMultiTouchAction() {
        return new MultiTouchAction((webDriver()));
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public boolean isAppInstalled(String bundleId) {
        return webDriver().isAppInstalled(bundleId);
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController pinch(int x, int y) {
        webDriver().pinch(x, y);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController lockScreen(int sec) {
        webDriver().lockScreen(sec);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController tap(int finger, int y, int z, int duration) {
        webDriver().tap(finger,y,z,duration);
        return this;
    }


    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController zoom(int x, int y) {
        webDriver().zoom(x, y);
        return this;
    }

    @Override
    @Monitor
    public AppiumDriverController swipe(int startx, int starty, int endx, int endy, int duration) {
        webDriver().swipe(startx, starty, endx, endy, duration);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public Object executeScript(String driverCommand, HashMap<String, ?> parameters) {
        Object response=webDriver().execute(driverCommand, parameters);
        return response;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#getCurrentActivity()
     */
    @Override
    @RetryFailure(retryCount=1)
    public String getCurrentActivity() {
        return  ((AndroidDriver)webDriver()).currentActivity();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#scrollTo(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public MobileElement scrollTo(String text) {
        if(webDriver() instanceof AndroidDriver) {
            String locator = "androidUIAutomator="+uiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") + uiScrollable("new UiSelector().textContains(\"" + text + "\")");
            waitController().waitForElementPresence(locator);
        } else {
            waitController().waitForElementPresence("class=\"UIATableView\"");
        }
        return webDriver().scrollTo(text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#scrollToExact(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public MobileElement scrollToExact(String text) {
        if(webDriver() instanceof AndroidDriver) {
            String locator = "androidUIAutomator="+uiScrollable("new UiSelector().description(\"" + text + "\")") + uiScrollable("new UiSelector().text(\"" + text + "\")");
            waitController().waitForElementPresence(locator);
        } else {
            waitController().waitForElementPresence("class=\"UIATableView\"");
        }
        return webDriver().scrollToExact(text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#setNetworkConnection(boolean, boolean, boolean)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public AppiumDriverController setNetworkConnection(boolean airplaneMode, boolean wifi, boolean data) {
        if(webDriver() instanceof AndroidDriver) {
            NetworkConnectionSetting network=new NetworkConnectionSetting(false, true, true);
            network.setAirplaneMode(airplaneMode);
            network.setData(data);
            network.setWifi(wifi);
            ((AndroidDriver)webDriver()).setNetworkConnection(network);
        } else {
            throw new UnsupportedCommandException("The command setNetworkConnection is not used with IOSDriver");
        }
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#zoom(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public AppiumDriverController<T> zoom(Object locator) {
        webDriver().zoom(SessionContext.getSession().getWebElement());
        return this;
    }



}
