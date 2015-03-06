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
package com.automation.seletest.core.services.actions;

import org.openqa.selenium.Alert;


/**
 * Interface for waiting methods
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com) *
 */
@SuppressWarnings("hiding")
public interface WaitFor<T> {


    /** Wait for element to be present in DOM
     * @param locator the locator of the WebElement to wait to be present in DOM
     * @return WebElement that is present in screen
     */
    <T> T waitForElementPresence(String locator);

    /** Wait for element to be visible
     * @param locator the locator of the WebElement to wait for visibility
     * @return WebElement that is visible
     */
    <T> T waitForElementVisibility(Object locator);

    /**
     * Wait for element to be clickable
     * @param locator the locator of the WebElement to wait to be clickable
     * @return WebElement that is clickable
     */
    <T> T waitForElementToBeClickable(Object locator);

    /**
     * Wait for alert
     * @return Alert
     */
    Alert waitForAlert();

    /**
     * Wait for element to be invisble
     * @param locator the locator of the WebElement to wait to become invisible
     * @return true if element is invisible or false otherwise
     */
    boolean waitForElementInvisibility(String locator);

    /**
     * Wait for text to be present in Element
     * @param locator
     * @return true if text present in webElement or false otherwise
     */
    boolean waitForTextPresentinElement(Object locator,String text);

    /**
     * Wait for text to be present in attribute value
     * @param locator
     * @return true if text present in attribute value or false otherwise
     */
    boolean waitForTextPresentinValue(Object locator,String text);


    /**
     * Wait for elements with matching locator to be present in screen
     * @param locator
     * @return List<WebElement> the elements to be present in screen
     */
    <T> T waitForPresenceofAllElements(String locator);

    /**
     * Wait for elements with matching locator to be visible in screen
     * @param locator
     * @return List<WebElement> the elements to be visible in screen
     */
    <T> T waitForVisibilityofAllElements(String locator);

    /**
     * Waits for a page to load
     */
    void waitForPageLoaded();

    /**
     * Waits for ajax call to be completed
     * @param timeout the timeout to wait
     */
    void waitForAjaxCallCompleted(final long timeout);


    /**
     * Waits for element not to be present on screen
     * @param locator
     * @return true if element is not persent or false if element present
     */
    boolean waitForElementNotPresent(String locator);

    /**
     * Waits for element to become invisible
     * @param locator
     * true if element is invisible
     */
    boolean waitForElementInvisible(String locator);

    /**
     *Wait for title of page
     * @param title
     * @return true if title is the one provided
     */
    boolean waitForPageTitle(String title);


    /**
     * Wait for element to become not clickable
     * @param locator
     * @return true if element is no more clickable
     */
    boolean waitForElementNotClickable(Object locator);

}
