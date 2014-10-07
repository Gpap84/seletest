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

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.common.ActionsBuilderController;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.interfaces.MainController;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import com.automation.seletest.core.spring.SeletestWebTestBase;

/**
 * Abstract super class serves as base page object
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public abstract class AbstractPage<T> extends SeletestWebTestBase{

    /**Timeout to load a page*/
    private static final int LOAD_TIMEOUT = 30;

    /**Polling time*/
    private static final int REFRESH_RATE = 2;

    /**
     * Opens a page object
     * @param clazz
     * @return T the type of object
     */
    public T openPage(Class<T> clazz) {
        T page = PageFactory.initElements(SessionContext.getSession().getWebDriver(), clazz);
        ExpectedCondition<?>  pageLoadCondition = ((AbstractPage) page).getPageLoadCondition();
        waitForPageToLoad(pageLoadCondition);
        return page;
    }

    /**
     * Condition for loading a page object
     * @return ExpectedCondition<?> condition to load a page
     */
    protected abstract ExpectedCondition<?> getPageLoadCondition();

    /**
     * Wait for page to load
     * @param pageLoadCondition
     */
    private void waitForPageToLoad(ExpectedCondition<?> pageLoadCondition) {
        Wait wait = new FluentWait(SessionContext.getSession().getWebDriver())
                .withTimeout(LOAD_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(REFRESH_RATE, TimeUnit.SECONDS);

        wait.until(pageLoadCondition);
    }



    /**
     * Interact with element based on factory strategy
     * @return ElementController
     */
    public MainController webControl() {
        return ApplicationContextProvider.getApplicationContext().getBean(StrategyFactory.class).getControllerStrategy(SessionContext.getSession().getControllerStrategy());
    }

    public ActionsBuilderController actionsControl() {
        return ApplicationContextProvider.getApplicationContext().getBean(StrategyFactory.class).getActionsStrategy(SessionContext.getSession().getActionsStrategy());
    }

    /**
     * get String locator for @FindBy
     * @param clazz
     * @param element
     * @return String locator
     */
    public String getWebElementLocator(Class<?> clazz, String element){
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.getAnnotation(FindBy.class)!=null){
                if(field.getName().equals(element)){
                    if(!field.getAnnotation(FindBy.class).className().isEmpty()){
                        return "class="+field.getAnnotation(FindBy.class).className();
                    } else if(!field.getAnnotation(FindBy.class).id().isEmpty()){
                        return "id="+field.getAnnotation(FindBy.class).id();
                    } else if(!field.getAnnotation(FindBy.class).css().isEmpty()){
                        return "css="+field.getAnnotation(FindBy.class).css();
                    } else if(!field.getAnnotation(FindBy.class).linkText().isEmpty()){
                        return "link="+field.getAnnotation(FindBy.class).linkText();
                    } else if(!field.getAnnotation(FindBy.class).name().isEmpty()){
                        return "name="+field.getAnnotation(FindBy.class).name();
                    } else if(!field.getAnnotation(FindBy.class).xpath().isEmpty()){
                        return "xpath="+field.getAnnotation(FindBy.class).xpath();
                    }

                }
            }
        }
        return null;
    }

}
