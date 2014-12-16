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
package com.automation.seletest.core.services.actions;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebElement;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.thoughtworks.selenium.SeleniumException;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumWait")
@SuppressWarnings("deprecation")
public class SeleniumWaitStrategy extends AbstractBase.WaitBase{

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementPresence(java.lang.String)
     */
	@Cacheable("wait")
    @Override
    public WebElement waitForElementPresence(final String locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for element " + locator + " presence");
            } try {
                if (SessionControl.selenium().isElementPresent(defineLocator(locator))) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight(locator);
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementVisibility(java.lang.Object)
     */
	@Cacheable("wait")
    @Override
    public WebElement waitForElementVisibility(final Object locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for element " + locator + " visibility");
            } try {
                if (SessionControl.selenium().isVisible(defineLocator(locator))) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight((String)locator);
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementToBeClickable(java.lang.Object)
     */
	@Cacheable("wait")
    @Override
    public WebElement waitForElementToBeClickable(Object locator) {
        throw new UnsupportedCommandException("waitForElementToBeClickable(Object locator) is not used by Selenium 1");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForAlert()
     */
    @Override
    public Alert waitForAlert() {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for for alert presence");
            } try {
                if (SessionControl.selenium().isAlertPresent()) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForElementInvisibility(java.lang.String)
     */
	@Cacheable("wait")
    @Override
    public Boolean waitForElementInvisibility(final String locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for element " + locator + " invisibility");
            } try {
                if (!SessionControl.selenium().isVisible(defineLocator(locator))) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight(defineLocator(locator));
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForTextPresentinElement(java.lang.Object, java.lang.String)
     */
	@Cacheable("wait")
    @Override
    public Boolean waitForTextPresentinElement(final Object locator,final String text) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for element " + locator + " to have text " + text + "");
            } try {
                if (SessionControl.selenium().getText(defineLocator(locator)).equalsIgnoreCase(text)) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight((String)locator);
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForTextPresentinValue(java.lang.Object, java.lang.String)
     */
	@Cacheable("wait")
    @Override
    public Boolean waitForTextPresentinValue(final Object locator,final String text) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for element " + locator + " to have text " + text + " in value attribute");
            } try {
                if (SessionControl.selenium().getAttribute(defineLocator(locator)+"@value").equalsIgnoreCase(text)) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight((String)locator);
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForPresenceofAllElements(java.lang.String)
     */
	@Cacheable("wait")
    @Override
    public List<WebElement> waitForPresenceofAllElements(final String locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                throw new SeleniumException("Timeout of " + SessionContext.getSession().getWaitUntil() + " seconds waiting for elements with " + locator);
            } try {
                int elements=0;
                if(locator.startsWith("//") || locator.startsWith("xpath=")) {
                    elements=SessionControl.selenium().getXpathCount(defineLocator(locator)).intValue();
                } else if(locator.startsWith("css=")) {
                    elements=SessionControl.selenium().getCssCount(defineLocator(locator)).intValue();
                }
                if (elements > 1) {
                    break;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
        SessionControl.selenium().highlight(defineLocator(locator));
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForVisibilityofAllElements(java.lang.String)
     */
    @Deprecated
    @Override
    public List<WebElement> waitForVisibilityofAllElements(String locator) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForPageLoaded()
     */
    @Override
    public void waitForPageLoaded() {
        SessionControl.selenium().waitForPageToLoad(String.valueOf(SessionContext.getSession().getWaitUntil()));

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.ActionsSync#waitForAjaxCallCompleted(long)
     */
    @Cacheable("wait")
    @Override
    public void waitForAjaxCallCompleted(final long timeout) {
        SessionControl.selenium().waitForCondition("selenium.browserbot.getCurrentWindow().jQuery.active == 0",String.valueOf(timeout));
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementNotPresent(java.lang.String)
     */
    @Cacheable("wait")
    @Override
    public boolean waitForElementNotPresent(final String locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
                return false;
            } try {
                if (!SessionControl.selenium().isElementPresent(defineLocator(locator))) {
                    return true;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementInvisble(java.lang.String)
     */
    @Cacheable("wait")
    @Override
    public boolean waitForElementInvisible(final String locator) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
             return false;
            } try {
                if (!SessionControl.selenium().isVisible(defineLocator(locator))) {
                    return true;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.WaitFor#waitForPageTitle(java.lang.String)
     */
    @Cacheable("wait")
    @Override
    public boolean waitForPageTitle(final String title) {
        long startTime = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - startTime >= SessionContext.getSession().getWaitUntil() * 1000) {
             return false;
            } try {
                String [] windowTitles=SessionControl.selenium().getAllWindowTitles();
                if (windowTitles[windowTitles.length - 1].contains(title)) {
                    return true;
                }
            } catch (Exception e) {
            } threadSleep(100);
        } while (true);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementNotClickable(java.lang.Object)
     */
    @Cacheable("wait")
    @Override
    public boolean waitForElementNotClickable(final Object locator) {
      throw new UnsupportedCommandException("waitForElementNotClickable(Object locator) is not used by Selenium RC");
    }

}
