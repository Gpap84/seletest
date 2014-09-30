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
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.UnsupportedCommandException;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;


/**
 * (AppiumDriverController api for iOS-Android native app interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class AppiumDriverController<T extends AppiumDriver> extends DriverBaseController<T> implements AppiumController{

    @Override
    public void launchApp() {
        webDriver().launchApp();
    }

    @Override
    public void resetApp() {
        webDriver().resetApp();
    }

    @Override
    public void runAppinBackground(int sec) {
        webDriver().runAppInBackground(sec);
    }

    @Override
    public void closeApp() {
        webDriver().closeApp();
    }

    @Override
    public void installApp(String appPath, String bundleId) {
        if(!isAppInstalled(bundleId)) {
            Map<String, String> args = new HashMap<String, String>();
            args.put("appPath", appPath);
            webDriver().installApp(appPath);
        }
    }

    @Override
    public void performTouchAction(TouchAction e) {
        webDriver().performTouchAction(e);
    }

    @Override
    public void performMultiTouchAction(MultiTouchAction e) {
        webDriver().performMultiTouchAction(e);
    }

    @Override
    public void hideKeyboard() {
        webDriver().hideKeyboard();
    }

    @Override
    public void rotate(ScreenOrientation e) {
        webDriver().rotate(e);
    }

    @Override
    public ScreenOrientation getscreen() {
        return webDriver().getOrientation();
    }

    @Override
    public MultiTouchAction getMultiTouchAction() {
        return new MultiTouchAction((webDriver()));
    }

    @Override
    public boolean isAppInstalled(String bundleId) {
        return webDriver().isAppInstalled(bundleId);
    }

    @Override
    public void pinch(int x, int y) {
        webDriver().pinch(x, y);
    }

    @Override
    public void lockScreen(int sec) {
        webDriver().lockScreen(sec);
    }

    @Override
    public void tap(int finger, int y, int z, int duration) {
        webDriver().tap(finger,y,z,duration);
    }


    @Override
    public void zoom(int x, int y) {
        webDriver().zoom(x, y);
    }

    @Override
    public void swipe(int startx, int starty, int endx, int endy,
            int duration) {
        webDriver().swipe(startx, starty, endx, endy, duration);
    }

    @Override
    public void executeScript(String driverCommand, HashMap<String, ?> parameters) {
        webDriver().execute(driverCommand, parameters);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#getCurrentActivity()
     */
    @Override
    public String getCurrentActivity() {
        return  ((AndroidDriver)webDriver()).currentActivity();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#scrollTo(java.lang.String)
     */
    @Override
    public void scrollTo(String text) {
        if(webDriver() instanceof AndroidDriver) {
            String locator = uiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") + uiScrollable("new UiSelector().textContains(\"" + text + "\")");
            waitController().waitForElementPresence(locator);
        } else {
            waitController().waitForElementPresence("class=\"UIATableView\"");
        }
        webDriver().scrollTo(text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#scrollToExact(java.lang.String)
     */
    @Override
    public void scrollToExact(String text) {
        if(webDriver() instanceof AndroidDriver) {
            String locator = uiScrollable("new UiSelector().descriptionContains(\"" + text + "\")") + uiScrollable("new UiSelector().textContains(\"" + text + "\")");
            waitController().waitForElementPresence(locator);
        } else {
            waitController().waitForElementPresence("class=\"UIATableView\"");
        }
        webDriver().scrollToExact(text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.mobileAPI.AppiumController#setNetworkConnection(boolean, boolean, boolean)
     */
    @Override
    public void setNetworkConnection(boolean airplaneMode, boolean wifi,
            boolean data) {
        if(webDriver() instanceof AndroidDriver) {
            NetworkConnectionSetting network=new NetworkConnectionSetting(false, true, true);
            network.setAirplaneMode(airplaneMode);
            network.setData(data);
            network.setWifi(wifi);
            ((AndroidDriver)webDriver()).setNetworkConnection(network);
        } else {
            throw new UnsupportedCommandException("The command setNetworkConnection is not used with IOSDriver");
        }

    }



}
