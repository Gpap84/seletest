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
package com.automation.seletest.core.selenium.common;

import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumActions")
@SuppressWarnings("deprecation")
public class ActionsSeleniumBuilder implements ActionsBuilderController<ActionsSeleniumBuilder>{

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#click(java.lang.Object)
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder click(Object locator) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseOver(java.lang.Object)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder mouseOver(Object locator) {
        SessionContext.getSession().getSelenium().mouseOver((String)locator);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseUp(org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumBuilder mouseUp(KeyInfo key) {
        SessionContext.getSession().getSelenium().keyUpNative(key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseDown(org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumBuilder mouseDown(KeyInfo key) {
        SessionContext.getSession().getSelenium().keyDownNative(key.getEvent());
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseDown(java.lang.Object, org.openqa.selenium.Keys)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder mouseDown(Object locator, KeyInfo key) {
        SessionContext.getSession().getSelenium().keyDown((String)locator, key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseUp(java.lang.Object, org.openqa.selenium.Keys)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder mouseUp(Object locator, KeyInfo key) {
        SessionContext.getSession().getSelenium().keyUp((String)locator, key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#clickAndHold(java.lang.Object)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder clickAndHold(Object locator) {
        SessionContext.getSession().getSelenium().mouseDown((String)locator);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#performActions()
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder performActions() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#performTouchActions()
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder performTouchActions() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(java.lang.Object)
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder tap(Object locator) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(java.lang.Object, int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder tap(Object locator, int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder tap(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumBuilder press(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(java.lang.Object)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder press(Object locator) {
        clickAndHold(locator);
        SessionContext.getSession().getSelenium().mouseUp((String)locator);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(java.lang.Object, int, int)
     */
    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public ActionsSeleniumBuilder press(Object locator, int x, int y) {
        SessionContext.getSession().getSelenium().mouseDownAt((String)locator,String.valueOf(x)+ "," + String.valueOf(y));
        SessionContext.getSession().getSelenium().mouseUpAt((String)locator,String.valueOf(x)+ "," + String.valueOf(y));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#dragndrop(java.lang.Object, java.lang.Object)
     */
    @Override
    public ActionsSeleniumBuilder dragndrop(Object draglocator, Object droplocator) {
        SessionContext.getSession().getSelenium().dragAndDropToObject((String)draglocator, (String) droplocator);
        return this;
    }

}
