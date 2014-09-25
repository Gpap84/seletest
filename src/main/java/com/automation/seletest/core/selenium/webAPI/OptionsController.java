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
package com.automation.seletest.core.selenium.webAPI;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.logging.LogEntries;

/**
 * OptionsController interface
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public interface OptionsController {

    /**
     * Deletes a cookie by name
     * @param name
     * @param value
     * @return
     */
    void deleteCookieByName(String name);

    /**
     * Deletes a cookie
     * @param cookie
     * @return
     */
    void deleteCookie(Cookie cookie);

    /**
     * Delete all cookies
     * @return
     */
    void deleteAllCookies();

    /**
     * Create a cookie
     * @param name
     * @param value
     * @return
     */
    void addCookie(Cookie cookie);

    /**
     * Gets all cookies
     * @return
     */
    Set<Cookie> getCookies();


    /**
     * Implicitly wait
     * @param timeout
     * @param timeunit
     */
    void implicitlyWait(long timeout,TimeUnit timeunit);

    /**
     * Timeout waiting for page to load
     * @param timeout
     * @param timeunit
     */
    void pageLoadTimeout(long timeout,TimeUnit timeunit);

    /**
     * Timeout waiting for script to finish
     * @param timeout
     * @param timeunit
     */
    void scriptLoadTimeout(long timeout,TimeUnit timeunit);

    /**
     * Set window position
     * @param point
     */
    void setWindowPosition(Point point);

    /**
     * Set window size
     * @param dimension
     */
    void setWindowDimension(Dimension dimension);

    /**
     * Gets window position
     * @return
     */
    Point getWindowPosition();

    /**
     * Gets window dimension
     * @return
     */
    Dimension getWindowDimension();

    /**
     * Maximize window
     */
    void maximizeWindow();

    /**
     * Get logs (client,server,performance....etc.)
     * @param logtype
     * @return LogEntries the log entries
     */
    LogEntries logs(String logtype);

}
