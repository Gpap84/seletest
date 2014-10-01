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
package com.automation.seletest.core.selenium.webAPI.remoteWebDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController;
import com.automation.seletest.core.services.annotations.Monitor;

/**
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("webDriverOptions")
public class OptionsDriverController<T extends RemoteWebDriver> extends DriverBaseController<T> implements OptionsController {

    /*************************************************************
     ************************COOKIES SECTION*********************
     *************************************************************
     */
    @Override
    @Monitor
    public void deleteCookieByName(String name) {
        webDriver().manage().deleteCookieNamed(name);
    }

    @Override
    @Monitor
    public void deleteAllCookies() {
        webDriver().manage().deleteAllCookies();
    }

    @Override
    @Monitor
    public void addCookie(Cookie cookie) {
        webDriver().manage().addCookie(cookie);
    }

    @Override
    @Monitor
    public Set<Cookie> getCookies() {
        Set<Cookie> cookies=webDriver().manage().getCookies();
        return cookies;
    }

    @Override
    @Monitor
    public void deleteCookie(Cookie cookie) {
        webDriver().manage().deleteCookie(cookie);
    }

    /*************************************************************
     ************************TIMEOUTS SECTION*********************
     *************************************************************
     */
    @Override
    public void implicitlyWait(long timeout,TimeUnit timeunit) {
        webDriver().manage().timeouts().implicitlyWait(timeout, timeunit);
    }

    @Override
    public void pageLoadTimeout(long timeout, TimeUnit timeunit) {
        webDriver().manage().timeouts().pageLoadTimeout(timeout, timeunit);
    }

    @Override
    public void scriptLoadTimeout(long timeout, TimeUnit timeunit) {
        webDriver().manage().timeouts().setScriptTimeout(timeout, timeunit);
    }

    /*************************************************************
     ************************WINDOWS SECTION*********************
     *************************************************************
     */
    @Override
    public void setWindowPosition(Point point) {
        webDriver().manage().window().setPosition(point);
    }

    @Override
    public void setWindowDimension(Dimension dimension) {
        webDriver().manage().window().setSize(dimension);
    }

    @Override
    public Point getWindowPosition() {
        return webDriver().manage().window().getPosition();
    }

    @Override
    public Dimension getWindowDimension() {
        return webDriver().manage().window().getSize();
    }

    @Override
    public void maximizeWindow() {
        webDriver().manage().window().maximize();
    }

    /*************************************************************
     ************************LOGS SECTION*********************
     *************************************************************
     */
    @Override
    public LogEntries logs(String logtype) {
        return webDriver().manage().logs().get(logtype);
    }



}
