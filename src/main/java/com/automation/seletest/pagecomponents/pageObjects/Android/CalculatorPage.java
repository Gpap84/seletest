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
package com.automation.seletest.pagecomponents.pageObjects.Android;

import java.text.MessageFormat;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.mobileAPI.AppiumController;
import com.automation.seletest.core.selenium.webAPI.elements.Locators;
import com.automation.seletest.pagecomponents.pageObjects.AbstractPage;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class CalculatorPage extends AbstractPage<CalculatorPage>{

    @Autowired
    AppiumController<?> appium;

    public enum CalculatorLocators {

        IPF_RESULT("id=my.android.calc:id/input") {
            @Override
            public String log() {
               return "Result field " + IPF_RESULT.get() + "'!!!";
            }
        },

        BTN_1("id=my.android.calc:id/b030") {
            @Override
            public String log() {
               return "Button 1 " + BTN_1.get() + "'!!!";
            }
        },
        BTN_3("id=my.android.calc:id/b032") {
            @Override
            public String log() {
                return "Button 3: '" + BTN_3.get() + "'!!!";
            }
        },
        BTN_X("id=my.android.calc:id/b023") {
            @Override
            public String log() {
                return "Button x: '" + BTN_X.get() + "'!!!";
            }
        },
        BTN_EQUALS("id=my.android.calc:id/b044") {
            @Override
            public String log() {
                return "Button equals: '" + BTN_EQUALS.get() + "'!!!";
            }
        },

        BTN_CLEAR("id=my.android.calc:id/b000") {
            @Override
            public String log() {
                return "Button clear: '" + BTN_CLEAR.get() + "'!!!";
            }
        },
        ;

    private final String myLocator;

    CalculatorLocators(String locator) {
        myLocator = locator;
    }

    public String get() {
        return myLocator;
    }

    public String getWithParams(Object... params) {
        return MessageFormat.format(myLocator, params);
    }

    // Abstract method which need to be implemented
    public abstract String log();

}

    /**
     * Press the "C" button to clear previous result
     * @return CalculatorPage
     */
    public CalculatorPage clearResult() {
        webControl().click(CalculatorLocators.BTN_CLEAR.get());
        return this;
    }

    /**
     * Executes Multiplication for two numbers
     * @param button1
     * @param button2
     * @return CalculatorPage
     */
    public CalculatorPage calculateMultiplication(String button1, String button2) {
        actionsControl().
        tap(button1).
        tap(CalculatorLocators.BTN_X.get()).
        tap(button2).
        tap(CalculatorLocators.BTN_EQUALS.get()).
        performTouchActions();
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.pagecomponents.pageObjects.AbstractPage#getPageLoadCondition()
     */
    @Override
    protected ExpectedCondition<?> getPageLoadCondition() {
        return ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(CalculatorLocators.BTN_X.get()).setLocator(CalculatorLocators.BTN_X.get()));

    }

}
