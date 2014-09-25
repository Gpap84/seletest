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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;


/**
 * (AppiumDriverController api for iOS-Android native app interaction
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class AppiumDriverController<T extends RemoteWebDriver> extends DriverBaseController<T> implements AppiumController{

    @Override
    public void launchApp() {
        ((AppiumDriver)webDriver()).launchApp();
    }

    @Override
    public void resetApp() {
        ((AppiumDriver)webDriver()).resetApp();
    }

    @Override
    public void runAppinBackground(int sec) {
        ((AppiumDriver)webDriver()).runAppInBackground(sec);
    }

    @Override
    public void closeApp() {
        ((AppiumDriver)webDriver()).closeApp();
    }

    @Override
    public void installApp(String bundleId, String appPath) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("appPath", appPath);
        ((AppiumDriver)webDriver()).installApp(appPath);
    }

    @Override
    public void performTouchAction(TouchAction e) {
        ((AppiumDriver)webDriver()).performTouchAction(e);
    }

    @Override
    public void performMultiTouchAction(MultiTouchAction e) {
        ((AppiumDriver)webDriver()).performMultiTouchAction(e);
    }

    @Override
    public void hideKeyboard() {
        ((AppiumDriver)webDriver()).hideKeyboard();
    }

    @Override
    public void rotate(ScreenOrientation e) {
        ((AppiumDriver)webDriver()).rotate(e);
    }

    @Override
    public ScreenOrientation getscreen() {
        return ((AppiumDriver)webDriver()).getOrientation();
    }

    @Override
    public MultiTouchAction getMultiTouchAction() {
        return new MultiTouchAction(((AppiumDriver)webDriver()));
    }

    @Override
    public boolean isAppInstalled(String bundleId) {
        return ((AppiumDriver)webDriver()).isAppInstalled(bundleId);
    }

    @Override
    public void pinch(int x, int y) {
        ((AppiumDriver)webDriver()).pinch(x, y);
    }

    @Override
    public void lockScreen(int sec) {
        ((AppiumDriver)webDriver()).lockScreen(sec);
    }

    @Override
    public void tap(int finger, int y, int z, int duration) {
        ((AppiumDriver)webDriver()).tap(finger,y,z,duration);
    }

    @Override
    public void shake() {
        ((AppiumDriver)webDriver()).shake();
    }

    @Override
    public void zoom(int x, int y) {
        ((AppiumDriver)webDriver()).zoom(x, y);
    }

    @Override
    public void swipe(int startx, int starty, int endx, int endy,
            int duration) {
        ((AppiumDriver)webDriver()).swipe(startx, starty, endx, endy, duration);
    }

    @Override
    public void executeScript(String driverCommand, HashMap<String, ?> parameters) {
        ((AppiumDriver)webDriver()).execute(driverCommand, parameters);
    }

    @Override
    public String getCurrentActivity() {
        return ((AppiumDriver)webDriver()).currentActivity();
    }
}
