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

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.services.webSync.WaitFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ActionsSeleniumController class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumActions")
public class ActionsSeleniumController implements ActionsController {

    @Autowired
    StrategyFactory factoryStrategy;

    /**
     * WaitFor Controller
     * @return WaitFor
     */
    private WaitFor<String> waitController() {
        return factoryStrategy.getWaitStrategy(SessionContext.session().getWaitStrategy());
    }

    /**
     * Define locator
     * @param locator Object locator
     * @return String locator
     */
    private synchronized String defineLocator(Object locator){
        if(((String)locator).startsWith("jquery=")){
            return ((String)locator).replace("jquery=", "css=");
        } else{
            return (String)locator;
        }
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#click(java.lang.Object)
     */
    @Deprecated
    @Override
    public ActionsSeleniumController click(Object locator) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseOver(java.lang.Object)
     */
    @Override
    public ActionsSeleniumController mouseOver(Object locator) {
        SessionContext.getSession().getSelenium().mouseOver(waitController().waitForElementVisibility(defineLocator(locator)));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseUp(org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumController mouseUp(KeyInfo key) {
        SessionContext.getSession().getSelenium().keyUpNative(key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseDown(org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumController mouseDown(KeyInfo key) {
        SessionContext.getSession().getSelenium().keyDownNative(key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseDown(java.lang.Object, org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumController mouseDown(Object locator, KeyInfo key) {
        SessionContext.getSession().getSelenium().keyDown(waitController().waitForElementVisibility(defineLocator(locator)), key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#mouseUp(java.lang.Object, org.openqa.selenium.Keys)
     */
    @Override
    public ActionsSeleniumController mouseUp(Object locator, KeyInfo key) {
        SessionContext.getSession().getSelenium().keyUp(waitController().waitForElementVisibility(defineLocator(locator)), key.getEvent());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#clickAndHold(java.lang.Object)
     */
    @Override
    public ActionsSeleniumController clickAndHold(Object locator) {
        SessionContext.getSession().getSelenium().mouseDown(waitController().waitForElementVisibility(defineLocator(locator)));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#performActions()
     */
    @Deprecated
    @Override
    public ActionsSeleniumController performActions() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#performTouchActions()
     */
    @Deprecated
    @Override
    public ActionsSeleniumController performTouchActions() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(java.lang.Object)
     */
    @Deprecated
    @Override
    public ActionsSeleniumController tap(Object locator) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(java.lang.Object, int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumController tap(Object locator, int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#tap(int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumController tap(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(int, int)
     */
    @Deprecated
    @Override
    public ActionsSeleniumController press(int x, int y) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(java.lang.Object)
     */
    @Override
    public ActionsSeleniumController press(Object locator) {
        clickAndHold(locator);
        SessionContext.getSession().getSelenium().mouseUp(waitController().waitForElementVisibility(defineLocator(locator)));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(java.lang.Object, int, int)
     */
    @Override
    public ActionsSeleniumController press(Object locator, int x, int y) {
        SessionContext.getSession().getSelenium().mouseDownAt(waitController().waitForElementVisibility(defineLocator(locator)),String.valueOf(x)+ "," + String.valueOf(y));
        SessionContext.getSession().getSelenium().mouseUpAt(waitController().waitForElementVisibility(defineLocator(locator)),String.valueOf(x)+ "," + String.valueOf(y));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#dragndrop(java.lang.Object, java.lang.Object)
     */
    @Override
    public ActionsSeleniumController dragndrop(Object draglocator, Object droplocator) {
        SessionContext.getSession().getSelenium().dragAndDropToObject(waitController().waitForElementVisibility(defineLocator(draglocator)), waitController().waitForElementVisibility(defineLocator(droplocator)));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.common.ActionsBuilderController#press(com.automation.seletest.core.selenium.common.KeyInfo)
     */
    @Override
    public ActionsSeleniumController press(KeyInfo key) {
        SessionContext.getSession().getSelenium().keyPressNative(key.getEvent());
        return this;
    }

}
