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

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;

/**
 * Actions builder class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class ActionsBuilder implements ActionsBuilderController{

    /**
     * Actions
     * @return Actions instance
     */
    private Actions actionsBuilder(){
        return ((Actions) SessionContext.getSession().getControllers().get(Actions.class));
    }

    /**
     * TouchAction
     * @return TouchAction instance
     */
    private TouchAction touchactionsBuilder(){
        return ((TouchAction) SessionContext.getSession().getControllers().get(TouchAction.class));
    }


    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder mouseOver(Object locator) {
        actionsBuilder().moveToElement(SessionContext.getSession().getWebElement());
        return this;
    }

    @Override
    public ActionsBuilder mouseUp(Keys key) {
        actionsBuilder().keyUp(key);
        return this;

    }

    @Override
    public ActionsBuilder mouseDown(Keys key) {
        actionsBuilder().keyDown(key);
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder mouseDown(Object locator, Keys key) {
        actionsBuilder().keyDown(SessionContext.getSession().getWebElement(),key);
        return this;

    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder mouseUp(Object locator, Keys key) {
        actionsBuilder().keyUp(SessionContext.getSession().getWebElement(),key);
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder clickAndHold(Object locator) {
        actionsBuilder().clickAndHold(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.CLICKABLE)
    @Override
    public ActionsBuilder click(Object locator) {
        actionsBuilder().click(SessionContext.getSession().getWebElement());
        return this;
    }

    @Override
    public ActionsBuilder performActions() {
        actionsBuilder().build().perform();
        SessionContext.getSession().getControllers().remove(Actions.class);
        SessionContext.getSession().getControllers().put(Actions.class, new Actions(SessionControl.webController().driverInstance()));
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder tap(Object locator) {
        touchactionsBuilder().tap(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder tap(Object locator, int x, int y) {
        touchactionsBuilder().tap(SessionContext.getSession().getWebElement(),x,y);
        return null;
    }

    @Override
    public ActionsBuilder tap(int x, int y) {
        touchactionsBuilder().tap(x,y);
        return this;
    }

    @Override
    public ActionsBuilder press(int x, int y) {
        touchactionsBuilder().press(x,y);
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder press(Object locator) {
        touchactionsBuilder().press(SessionContext.getSession().getWebElement());
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsBuilder press(Object locator, int x, int y) {
        touchactionsBuilder().press(SessionContext.getSession().getWebElement(),x,y);
        return this;
    }

    @Override
    public ActionsBuilder performTouchActions() {
        touchactionsBuilder().perform();
        SessionContext.getSession().getControllers().remove(TouchAction.class);
        SessionContext.getSession().getControllers().put(TouchAction.class, new TouchAction((MobileDriver) SessionControl.webController().driverInstance()));
        return this;
    }

}
