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

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.elements.Locators;

/**
 * AbstractBase contains all static abstract classes used
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBase {


    /**
     * Abstract class for Waiting Conditions
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     *
     */
    public static abstract class WaitBase implements ActionsSync{

        private final String webDriverWait="wdWait";
        private final String fluentWait="fwWait";

        public final String NOT_PRESENT="Element not present in DOM";
        public final String NOT_VISIBLE="Element not visible in Screen";
        public final String NOT_CLICKABLE="Element cannot be clicked";
        public final String ALERT_NOT_PRESENT="Alert not present";
        public final String TEXT_NOT_PRESENT="Text not present in Element";
        public final String TEXT_NOT_PRESENT_VALUE="Text not present in value";


        /**
         * Returns a new WebDriverWait instance
         * @param timeOutInSeconds
         * @return
         */
        public WebDriverWait wfExpected(){
            return (WebDriverWait) SessionContext.getSession().getDriverContext().getBean(webDriverWait, new Object[]{SessionContext.getSession().getWebDriver()});
        }

        /**
         * Returns a new FluentWait instance
         * @param timeOutInSeconds
         * @param msg
         * @return
         */
        public Wait<WebDriver> fluentWait(String msg){
            return (Wait<WebDriver>) SessionContext.getSession().getDriverContext().getBean(fluentWait, new Object[]{SessionContext.getSession().getWebDriver()});
        }

        /**
         * Element to wait for condition
         * @param driver the WebDriver instance
         * @param locator the locator of the WebElement
         * @return WebElement
         */
        public WebElement elementToWait(WebDriver driver, Object locator){
            WebElement element=null;
            if(locator instanceof String){
                element=driver.findElement(Locators.findByLocator((String)locator).setLocator((String)locator));
            } else{
                element=(WebElement)locator;
            }
            return element;
        }

        /**
         * Elements to wait for condition
         * @param driver
         * @param locator
         * @return List<WebElement>
         */
        public List<WebElement> elementsToWait(WebDriver driver, String locator){
            List<WebElement> elements=null;
            elements=driver.findElements(Locators.findByLocator(locator).setLocator(locator));
            return elements;
        }

    }


}
