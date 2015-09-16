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

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 */
public interface WebController<T>{

    /**
     * This method sets the locator for the HTML element
     * @param locator the locator to identify the HTML element
     * @return Object that extends By
     */
    T setLocator(Object locator);

    /**
     * Finds a web element
     * @param locator Object locator
     * @return WebElement the element found
     */
    <T> T findElement(Object locator);

    /**
     * Click function
     * @param locator Object locator
     * @return WebController
     */
    <T> T click(Object locator);

    /**
     * Enter function
     * @param locator Object locator
     * @param text text to type
     * @return WebController
     */
    <T> T type(Object locator, String text);


    /**
     * Quit browser enum class.
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public enum CloseSession{QUIT,CLOSE}


    /**
     * Gets URL
     * @param url String url
     * @return WebController
     */
    <T> T goToTargetHost(String url);

    /**
     * Change style of specific element
     * @param attribute String html attribute
     * @param locator Object locator
     * @param attributevalue String attribute value
     * @return WebController
     */
    <T> T changeStyle(Object locator, String attribute, String attributevalue);

    /**
     * Takes screenshot
     * @throws IOException
     * @return WebController
     */
    <T> T takeScreenShot() throws IOException;

    /**
     * Take screenshot of an element
     * @param locator Object locator
     * @throws IOException
     * @return WebController
     */
    <T> T takeScreenShotOfElement(Object locator) throws IOException;


    /**
     * Returns text of element
     * @param locator
     * @return String text of element
     */
    String getText(Object locator);

    /**
     * Gets the tagName of the element
     * @param locator
     * @return tagName
     */
    String getTagName(Object locator);

    /**
     * Gets the element Location
     * @param locator
     * @return Point Location of element
     */
    Point getLocation(Object locator);

    /**
     * Gets the element Dimensions
     * @param locator
     * @return Dimension for Element Dimensions
     */
    Dimension getElementDimensions(Object locator);

    /**
     * Returns the source of the current page
     * @return html source
     */
    String getPageSource();

    /**
     * Defines if a web element is present
     * @param locator
     * @return true if a web element is present
     */
    boolean isElementPresent(String locator);

    /**
     *  Defines if a web element is not visible
     * @param locator
     * @return true if element is no longer visible
     */
    boolean isElementNotVisible(String locator);

    /**
     * Defines if a web element is not present
     * @param locator
     * @return true if element is no longer present
     */
    boolean isElementNotPresent(String locator);

    /**
     * Defines if text is present in source
     * @param text
     * @return false if not text present in page source
     */
    boolean isTextPresent(String text);

    /**
     * Defines if a web element is visible
     * @param locator
     * @return true if a web element is visible
     */
    boolean isWebElementVisible(Object locator);

    /**
     *  Upload a file in dialog bog
     * @param locator Object locator
     * @param path Strng path for local file to upload
     * @return WeCntroller
     */
    <T> T uploadFile(Object locator, String path);

    /**
     * Executes JavaScript on browser
     * @param script
     * @param args
     * @return Object result of JS
     */
    Object executeJS(String script, Object... args);

    /**
     * Select option from drop down by value
     * @param value
     * @return WebController
     */
    <T> T selectByValue(String locator, String value);

    /**
     * Select option from drop down by visible text
     * @param locator
     * @param text
     * @return WebController
     */
    <T> T selectByVisibleText(String locator, String text);

    /**
     * Deletes a cookie by name
     * @param name String name for cookie
     * @return WebController
     */
    <T> T deleteCookieByName(String name);

    /**
     * Gets a cookie by name
     * @param name String name of the cookie
     * @return cookie
     */
    Cookie getCookieNamed(String name);

    /**
     * Deletes a cookie
     * @param cookie
     * @return WebController
     */
    <T> T deleteCookie(Cookie cookie);

    /**
     * Delete all cookies
     * @return WebController
     */
    <T> T deleteAllCookies();

    /**
     * Create a cookie
     * @param cookie Cookie to add
     * @return WebController
     */
    <T> T addCookie(Cookie cookie);

    /**
     * Gets all cookies
     * @return Set<Cookie>
     */
    Set<Cookie> getCookies();


    /**
     * Implicitly wait
     * @param timeout long timeout for implicit wait
     * @param timeunit TimeUnit to use
     * @return WebController
     */
    <T> T implicitlyWait(long timeout,TimeUnit timeunit);

    /**
     * Timeout waiting for page to load
     * @param timeout long timeout for page load wait
     * @param timeunit TimeUnit to use
     * @return WebController
     */
    <T> T pageLoadTimeout(long timeout,TimeUnit timeunit);

    /**
     * Timeout waiting for script to finish
     * @param timeout long timeout for script load wait
     * @param timeunit TimeUnit to use
     * @return WebController
     */
    <T> T scriptLoadTimeout(long timeout,TimeUnit timeunit);

    /**
     * Set window position
     * @param point
     * @return WebController
     */
    <T> T setWindowPosition(Point point);

    /**
     * Set window size
     * @param dimension
     * @return WebController
     */
    <T> T setWindowDimension(Dimension dimension);

    /**
     * Gets window position
     * @return Point for window position
     * @return WebController
     */
    Point getWindowPosition();

    /**
     * Gets window dimension
     * @return
     */
    Dimension getWindowDimension();

    /**
     * Maximize window
     * @return WebController
     */
    <T> T maximizeWindow();

    /**
     * Get logs (client,server,performance....etc.)
     * @param logtype
     * @return LogEntries the log entries
     */
    LogEntries logs(String logtype);

    /**
     * Switch to latest window
     * @return WebController
     */
    <T> T switchToLatestWindow();

    /**
     * Gets the number of opened windows
     * @return Integer the number of opened windows
     */
    int getNumberOfOpenedWindows();

    /**
     * Accept alert
     * @return WebController
     */
    <T> T acceptAlert();

    /**
     * Dismiss Alert
     * @return WebController
     */
    <T> T dismissAlert();

    /**
     * Quit session or close latest window
     * @param type
     * @return WebController
     */
    <T> T quit(CloseSession type);

    /**
     * Switch control to frame located by name or id
     * @param frameId
     * @return WebController
     */
    <T> T switchToFrame(String frameId);

    /**
     *  Go backward
     * @return WebController
     */
    <T> T goBack();

    /**
     * Go forward
     * @return WebController
     */
    <T> T goForward();

    /**
     * Find all child elements of parent
     * @param parent
     * @param child
     * @return List<WebElement> with all child elements
     */
    List<WebElement> findChildElements(Object parent, String child);

    /**
     * Number of elements matching the locator
     * @param locator
     * @return integer number of elements matching locator
     */
    int elementsMatching(String locator);

    /**
     *
     * @param tableLocator
     * @return int number of Rows of a table
     */
    int getRowsTable(Object tableLocator);

    /**
     * Get the total columns of a table
     * @param tableLocator
     * @return int number of Columns of a table
     */
    int getColumnsTable(Object tableLocator);


    /**
     * Gets the selected option's text from drop down menu
     * @param locator
     * @return String the selected option's text
     */
    String getFirstSelectedOptionText(Object locator);

    /**
     * Gets the text of all options of drop down menu
     * @param locator
     * @return List<String> with all the options's text
     */
    List<String> getAllOptionsText(Object locator);

    /**
     * Deselect option  by text
     * @param locator
     * @param text
     * @return MainController
     */
    <T> T clearSelectedOptionByText(Object locator, String text);


    /**
     * Deselect Option by Value
     * @param locator
     * @param value
     * @return WebController
     */
    <T> T clearSelectedOption(Object locator, String value);

    /**
     * If field editable
     * @param locator
     * @return true if a field is editable
     */
    boolean isFieldEditable(Object locator);

    /**
     * If element is clickable
     * @param locator
     * @return true if element is clickable
     */
    boolean isElementClickable(Object locator);

    /**
     * If field is not editable
     * @param locator
     * @return true if field is not editable
     */
    boolean isFieldNotEditable(Object locator);

    /**
     * If element is not clickable
     * @param locator
     * @return true if element is not clickable
     */
    boolean isElementNotClickable(Object locator);

    /**
     * Download a file using URLConnection
     * @param url
     * @param filenamePrefix
     * @param fileExtension
     * @return The absolute path of the downloaded file
     * @throws IOException
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    String downloadFile(String url, String filenamePrefix, String fileExtension) throws IOException, InterruptedException;

    /**
     * Sleep executed thread
     * @param milliseconds
     */
    void sleep(int milliseconds);

    /**
     * If text present in element
     * @param locator
     * @param text
     * @return text
     */
    boolean isTextPresentinElement(Object locator, String text);

    /**
     * Wait for angular to finish
     * @return WebController
     */
    <T> T waitForAngularFinish();
}
