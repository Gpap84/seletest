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

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;

/**
 * Actions builder class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class ActionsBuilder implements ActionsBuilderController{

    @Override
    public ActionsBuilder mouseOver(Object locator) {
         SessionContext.getSession().getActions().moveToElement(findElement(locator));
         return this;
    }

    @Override
    public ActionsBuilder mouseUp(Keys key) {
        SessionContext.getSession().getActions().keyUp(key);
        return this;

    }

    @Override
    public ActionsBuilder mouseDown(Keys key) {
        SessionContext.getSession().getActions().keyDown(key);
        return this;
    }

    @Override
    public ActionsBuilder mouseDown(Object locator, Keys key) {
        SessionContext.getSession().getActions().keyDown(findElement(locator),key);
        return this;

    }

    @Override
    public ActionsBuilder mouseUp(Object locator, Keys key) {
        SessionContext.getSession().getActions().keyUp(findElement(locator),key);
        return this;
    }

    @Override
    public ActionsBuilder clickAndHold(Object locator) {
        SessionContext.getSession().getActions().clickAndHold(findElement(locator));
        return this;
    }

    @Override
    public ActionsBuilder click(Object locator) {
        SessionContext.getSession().getActions().click(findElement(locator));
        return this;
    }

    /**
     * Returns WebElement based on arguments
     * @param locator
     * @return
     */
    private WebElement findElement(Object locator){
        if(locator instanceof WebElement){
            return (WebElement) locator;
        } else if(locator instanceof String){
            WebElement element=SessionContext.getSession().getWebactionscontroller().findElement((String)locator);
            return element;
        }
        return null;
    }

    @Override
    public ActionsBuilder performActions() {
        SessionContext.getSession().getActions().build().perform();
        return this;
    }





}
