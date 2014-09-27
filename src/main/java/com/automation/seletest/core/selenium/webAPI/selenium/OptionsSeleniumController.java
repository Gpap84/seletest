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
package com.automation.seletest.core.selenium.webAPI.selenium;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.logging.LogEntries;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * OptionsSeleniumController class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumOptions")
public class OptionsSeleniumController<T extends DefaultSelenium> extends DriverBaseController<T> implements OptionsController{

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookieByName(java.lang.String)
     */
    @Override
    public void deleteCookieByName(String name) {
        selenium().deleteCookie(name, "");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookie(org.openqa.selenium.Cookie)
     */
    @Deprecated
    @Override
    public void deleteCookie(Cookie cookie) {
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteAllCookies()
     */
    @Override
    public void deleteAllCookies() {
        selenium().deleteAllVisibleCookies();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#addCookie(org.openqa.selenium.Cookie)
     */
    @Deprecated
    @Override
    public void addCookie(Cookie cookie) {

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getCookies()
     */
    @Override
    @Deprecated
    public Set<Cookie> getCookies() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void implicitlyWait(long timeout, TimeUnit timeunit) {
        // TODO Auto-generated method stub
        //HttpCommandProcessor command = new  HttpCommandProcessor();
        //command.doCommand("setImplicitWaitLocator", new String[] {"10000",});

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void pageLoadTimeout(long timeout, TimeUnit timeunit) {
        selenium().setTimeout(String.valueOf(timeout));
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#scriptLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Deprecated
    @Override
    public void scriptLoadTimeout(long timeout, TimeUnit timeunit) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowPosition(org.openqa.selenium.Point)
     */
    @Override
    public void setWindowPosition(Point point) {
        int x=point.getX();
        int y=point.getY();
        selenium().getEval("window.resizeTo(" + x + ", " + y + "); window.moveTo(0,0);");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowDimension(org.openqa.selenium.Dimension)
     */
    @Override
    public void setWindowDimension(Dimension dimension) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getWindowPosition()
     */
    @Override
    public Point getWindowPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getWindowDimension()
     */
    @Override
    public Dimension getWindowDimension() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#maximizeWindow()
     */
    @Override
    public void maximizeWindow() {
       selenium().windowMaximize();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#logs(java.lang.String)
     */
    @Deprecated
    @Override
    public LogEntries logs(String logtype) {
        return null;
    }



}
