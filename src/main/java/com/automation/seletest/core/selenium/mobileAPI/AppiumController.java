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

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.util.HashMap;

import org.openqa.selenium.ScreenOrientation;



/**
 * Appium interface
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 */
public interface AppiumController {

    /**
     * This method launch application in mobile device
     */
    void launchApp();

    /**
     * This method resets application in mobile device
     */
    void resetApp();

    /**
     * This method resets application in mobile device
     **/
    void runAppinBackground(int sec);

    /**
     * This method closes application in mobile device
     */
    void closeApp();

    /**
     * This method installs an apk or ipa file to mobile device if it not already installed
     * @param appPath
     * @param bundleId (also appPackage for android)
     */
    void installApp(String appPath, String bundleId);


    /**
     * Performs a touch action
     * @param e
     */
    void performTouchAction(TouchAction e);

    /**
     * Performs multitouch action
     * @param e
     */
    void performMultiTouchAction(MultiTouchAction e);

    /**
     * Hide the kieyboard
     */
    void hideKeyboard();

    /**
     * rotate screen of mobile device
     * @param e
     */
    void rotate(ScreenOrientation e);

    /**
     * gets the screen orientation
     * @return ScreenOrientation
     */
    ScreenOrientation getscreen();

    /**
     * Gets multi touch action
     * @return MultiTouchAction
     */
    MultiTouchAction getMultiTouchAction();


    /**
     * checks if application is installed in device
     * @param bundleId
     */
    boolean isAppInstalled(String bundleId);

    /**
     * Pinch to specific coordinates
     * @param x
     * @param y
     */
    void pinch(int x,int y);

    /**
     * Locks the screen for specific amount of time
     * @param sec
     */
    void lockScreen(int sec);


    /**
     * Tap to element with coordinates for specific duration
     * @param fingers
     * @param y
     * @param z
     * @param duration
     */
    void tap(int fingers, int y, int z, int duration);


    /**
     * Zoom to element with coordinates
     * @param x
     * @param y
     */
    void zoom(int x, int y);

    /**
     * swipe gesture
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @param duration
     */
    void swipe(int startx, int starty, int endx, int endy, int duration);

    /**
     * Executes script on mobile device
     * @param driverCommand
     * @param parameters
     */
    void executeScript(String driverCommand, HashMap<String, ?> parameters);

    /**
     * Gets the current activity
     * @return the current activity
     */
    String getCurrentActivity();

    /**
     * Scroll forward to the element which has a description or name which contains the input text.
     * @param text
     * @return mobileElement after scrollTo location that contains text
     */
    MobileElement scrollTo(String text);

    /**
     * Scroll forward to the element which has a description or name which contains the input text.
     * @param text
     * @return mobileElement after scrollTo location with text
     */
    MobileElement scrollToExact(String text);

    /**
     * Sets NetworkConnectionSettings
     * @param airplaneMode
     * @param wifi
     * @param data
     */
    void setNetworkConnection(boolean airplaneMode, boolean wifi, boolean data);

}
