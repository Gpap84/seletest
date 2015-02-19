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
package com.automation.seletest.core.selenium.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;

/**
 * ActionsWebDriverController class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("webDriverActions")
public class ActionsWebDriverController implements ActionsController<ActionsWebDriverController> {

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsWebDriverController mouseOver(Object locator) {
        SessionControl.actionsBuilder().moveToElement(SessionContext.getSession().getWebElement());
        return this;
    }

    @Override
    public ActionsWebDriverController mouseUp(KeyInfo key) {
        SessionControl.actionsBuilder().keyUp(key.getKey());
        return this;

    }

    @Override
    public ActionsWebDriverController mouseDown(KeyInfo key) {
        SessionControl.actionsBuilder().keyDown(key.getKey());
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsWebDriverController mouseDown(Object locator, KeyInfo key) {
        SessionControl.actionsBuilder().keyDown(SessionContext.getSession().getWebElement(),key.getKey());
        return this;

    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsWebDriverController mouseUp(Object locator, KeyInfo key) {
        SessionControl.actionsBuilder().keyUp(SessionContext.getSession().getWebElement(),key.getKey());
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController clickAndHold(Object locator) {
        SessionControl.actionsBuilder().clickAndHold(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController click(Object locator) {
        SessionControl.actionsBuilder().click(SessionContext.getSession().getWebElement());
        return this;
    }

    @Override
    public ActionsWebDriverController performActions() {
        SessionControl.actionsBuilder().build().perform();
        SessionContext.getSession().setActions(new Actions(SessionContext.getSession().getWebDriver()));
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController tap(Object locator) {
        SessionControl.touchactionsBuilder().tap(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController tap(Object locator, int x, int y) {
        SessionControl.touchactionsBuilder().tap(SessionContext.getSession().getWebElement(),x,y);
        return null;
    }

    @Override
    public ActionsWebDriverController tap(int x, int y) {
        SessionControl.touchactionsBuilder().tap(x,y);
        return this;
    }

    @Override
    public ActionsWebDriverController press(int x, int y) {
        SessionControl.touchactionsBuilder().press(x,y);
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController press(Object locator) {
        SessionControl.touchactionsBuilder().press(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsWebDriverController press(Object locator, int x, int y) {
        SessionControl.touchactionsBuilder().press(SessionContext.getSession().getWebElement(),x,y);
        return this;
    }

    @Override
    public ActionsWebDriverController performTouchActions() {
        SessionControl.touchactionsBuilder().perform();
        SessionContext.getSession().setTouchAction(new TouchAction((AppiumDriver) SessionContext.getSession().getWebDriver()));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#dragndrop(java.lang.String, java.lang.String)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsWebDriverController dragndrop(Object draglocator, Object droplocator) {
        SessionControl.actionsBuilder().dragAndDrop(SessionContext.getSession().getWebElement(), (WebElement) droplocator);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(com.automation.seletest.core.selenium.common.KeyInfo)
     */
    @Override
    public ActionsWebDriverController press(KeyInfo key) {
        SessionControl.actionsBuilder().sendKeys(key.getKey());
        return this;
    }

}
