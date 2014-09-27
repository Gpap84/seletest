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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.interfaces.ElementController;
import com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController;
import com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.spring.SeletestWebTestBase;

/**
 * Abstract super class serves as base page object
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 * @param <T>
 */
public abstract class AbstractPage<T> extends SeletestWebTestBase{

    @Autowired
    StrategyFactory<?> factoryStrategy;

    /**
     * Opens a page object
     * @param clazz
     * @return T the type of object
     */
    public T openPage(Class<T> clazz) {
        T page = PageFactory.initElements((WebDriver) SessionContext.getSession().getWebDriver(), clazz);
        return page;
    }


    /**
     * Interact with element based on factory strategy
     * @return ElementController
     */
    public ElementController element() {
        return factoryStrategy.getElementControllerStrategy(SessionContext.getSession().getElementStrategy());
    }

    /**
     * OptionsController
     * @return OptionsController
     */
    public OptionsController options() {
        return factoryStrategy.getOptionsControllerStrategy(SessionContext.getSession().getOptionsStrategy());
    }

    /**
     * Windows interaction
     * @return WindowsController
     */
    public WindowsController windows() {
        return factoryStrategy.getWindowsControllerStrategy(SessionContext.getSession().getWindowsStrategy());
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
