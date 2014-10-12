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
package com.automation.seletest.pagecomponents.pageObjects.Web;

import java.text.MessageFormat;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.elements.Locators;
import com.automation.seletest.core.services.LogUtils;
import com.automation.seletest.pagecomponents.pageObjects.AbstractPage;

@Component
public class GooglePage extends AbstractPage<GooglePage>{

    @Autowired
    LogUtils log;

    public enum GooglePageLocators {

        IPF_SEARCH("name=q") {
            @Override
            public String log() {
                return "TextField \"Search\" used with locator: '" + IPF_SEARCH.get() + "'!!!";
            }
        },
        BTN_SUBMIT("name=btnG") {
            @Override
            public String log() {
                return "Button \"Search\" used with locator: '" + BTN_SUBMIT.get() + "'!!!";
            }
        },
        ;

        private final String myLocator;

        GooglePageLocators(String locator) {
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


    public GooglePage typeSearch(String text){
        webControl().getLocation(GooglePageLocators.IPF_SEARCH.get());
        webControl().type(GooglePageLocators.IPF_SEARCH.get(), text);
        return this;
    }

    /**
     * Press search with Actions Builder
     * @return
     */
    public GooglePage buttonSearch(){
        webControl().click(GooglePageLocators.BTN_SUBMIT.get());
        return this;
    }


    /**
     * Opens this page object
     * @return
     */
    public GooglePage open() {
        for(GooglePageLocators s:GooglePageLocators.values()){
            log.info(s.log());
        }
        return openPage(GooglePage.class);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.pagecomponents.pageObjects.AbstractPage#getPageLoadCondition()
     */
    @Override
    protected ExpectedCondition<?> getPageLoadCondition() {
        return ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(GooglePageLocators.IPF_SEARCH.get()).setLocator(GooglePageLocators.IPF_SEARCH.get()));
    }
}
