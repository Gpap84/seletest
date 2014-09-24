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
import io.appium.java_client.TouchAction;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.ScreenOrientation;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;


/**
 * driver()Controller api for iOS-Android native app interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings("rawtypes")
@Component
public class AppiumDriverController implements AppiumController<AppiumController>{

    /**
     *Appium Driver
     * @return AppiumDriver instance per thread
     */
    private AppiumDriver driver(){
        return (AppiumDriver) SessionContext.getSession().getWebDriver();
    }

    @Override
    public AppiumController launchApp() {
        driver().launchApp();
        return this;
    }

    @Override
    public AppiumController resetApp() {
        driver().resetApp();
        return this;
    }

    @Override
    public AppiumController runAppinBackground(int sec) {
        driver().runAppInBackground(sec);
        return this;
    }

    @Override
    public AppiumController closeApp() {
        driver().closeApp();
        return this;
    }

    @Override
    public AppiumController installApp(String bundleId, String appPath) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("appPath", appPath);
        driver().installApp(appPath);
        return this;
    }

    @Override
    public AppiumController performTouchAction(TouchAction e) {
        driver().performTouchAction(e);
        return this;
    }

    @Override
    public AppiumController performMultiTouchAction(MultiTouchAction e) {
        driver().performMultiTouchAction(e);
        return this;
    }

    @Override
    public AppiumController hideKeyboard() {
        driver().hideKeyboard();
        return this;
    }

    @Override
    public AppiumController rotate(ScreenOrientation e) {
        driver().rotate(e);
        return this;
    }

    @Override
    public ScreenOrientation getscreen() {
        return driver().getOrientation();
    }

    @Override
    public MultiTouchAction getMultiTouchAction() {
        return new MultiTouchAction(driver());
    }

    @Override
    public boolean isAppInstalled(String bundleId) {
        return driver().isAppInstalled(bundleId);
    }

    @Override
    public AppiumController pinch(int x, int y) {
        driver().pinch(x, y);
        return this;
    }

    @Override
    public AppiumController lockScreen(int sec) {
        driver().lockScreen(sec);
        return this;
    }

    @Override
    public AppiumController tap(int finger, int y, int z, int duration) {
        driver().tap(finger,y,z,duration);
        return this;
    }

    @Override
    public AppiumController shake() {
        driver().shake();
        return this;
    }

    @Override
    public AppiumController zoom(int x, int y) {
        driver().zoom(x, y);
        return this;
    }

    @Override
    public AppiumController swipe(int startx, int starty, int endx, int endy,
            int duration) {
        driver().swipe(startx, starty, endx, endy, duration);
        return this;
    }

    @Override
    public AppiumController executeScript(String driverCommand, HashMap<String, ?> parameters) {
        driver().execute(driverCommand, parameters);
        return this;
    }

    @Override
    public String getCurrentActivity() {
        return driver().currentActivity();
    }
}
