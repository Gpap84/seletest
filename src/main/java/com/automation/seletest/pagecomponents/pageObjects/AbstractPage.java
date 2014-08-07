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
package com.automation.seletest.pagecomponents.pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.spring.SeletestWebTestBase;

/**
 * Abstract super class serves as base page object
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractPage<T> extends SeletestWebTestBase{

    private static final int LOAD_TIMEOUT = 30;
    private static final int REFRESH_RATE = 2;

    public T openPage(Class<T> clazz) {
        T page = PageFactory.initElements(SessionControl.webController().driverInstance(), clazz);
        ExpectedCondition<?>  pageLoadCondition = ((AbstractPage) page).getPageLoadCondition();
        waitForPageToLoad(pageLoadCondition);
        Object action=((AbstractPage) page).getAction();
        SessionContext.getSession().setActions((Actions) action);
        return page;
    }

    protected abstract ExpectedCondition<?> getPageLoadCondition();


    protected abstract Object getAction();

    private void waitForPageToLoad(ExpectedCondition<?> pageLoadCondition) {
        Wait wait = new FluentWait(SessionControl.webController().driverInstance())
                .withTimeout(LOAD_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(REFRESH_RATE, TimeUnit.SECONDS);

        wait.until(pageLoadCondition);
    }

}
