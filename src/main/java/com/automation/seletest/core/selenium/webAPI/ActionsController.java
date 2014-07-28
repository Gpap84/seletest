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

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 */
@SuppressWarnings("hiding")
public interface ActionsController<T> {

    /**
     * Finds a web element
     * @param locator
     * @return
     */
    WebElement findElement(String locator);

	/**
	 * Click function
	 * @param locator
	 * @param timeout
	 * @return
	 */
    T clickTo(String locator);

    /**
     * Enter function
     * @param locator
     * @param text
     * @param timeout
     * @return
     */
    T enterTo(String locator, String text);


    /**
     * Quit session
     * @param type
     */
    void quit(CloseSession type);

    /**
     * Quit browser type
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public enum CloseSession{QUIT,CLOSE};

    /**
     * Gets the driver instance
     * @return
     */
    <T extends WebDriver> T getDriverInstance();

    /**
     * Gets URL
     * @param url
     */
    void getTargetHost(String url);

    /**
     * Change style of specific element
     * @param attribute
     * @param locator
     * @param attributevalue
     * @return
     */
    T changeStyle(String attribute, String locator, String attributevalue);

    /**
     * Takes screenshot
     * @throws IOException
     */
	void takeScreenShot() throws IOException;

	/**
	 * Take screenshot of an element
	 * @param locator
	 * @throws IOException
	 */
	void takeScreenShotOfElement(String locator) throws IOException;

	/**
	 * switch to latest window
	 * @return
	 */
	T switchToLatestWindow();

	 /**
     * Deletes a cookie by name
     * @param name
     * @param value
     * @return
     */
    T deleteCookieNamed(String name);

    /**
     * Deletes a cookie
     * @param cookie
     * @return
     */
    T deleteCookie(Cookie cookie);

    /**
     * Delete all cookies
     * @return
     */
    T deleteAllCookies();

    /**
     * Create a cookie
     * @param name
     * @param value
     * @return
     */
    T addCookie(Cookie cookie);

    /**
     * Gets all cookies
     * @return
     */
    Set<Cookie> getCookies();



}
