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
package com.automation.seletest.pagecomponents.pageObjects.Web;

import java.text.MessageFormat;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.elements.Locators;
import com.automation.seletest.pagecomponents.pageObjects.AbstractPage;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class GitHubSearchResultPage extends AbstractPage<GitHubSearchResultPage>{
    public enum GitHubSearchPageLocators {

        TXT_RESULT_HEADER("jquery=a:contains({0})") {
            @Override
            public String log() {
                return "Header of the result with locator " + TXT_RESULT_HEADER.get() + "!!!";
            }
        },
        DIV_SEARCH_MENU("css=div.search-menu-container") {
            @Override
            public String log() {
                return "Search menu with locator " + DIV_SEARCH_MENU.get() + "!!!";
            }
        },
        ;

        private final String myLocator;

        GitHubSearchPageLocators(String locator) {
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

    /* (non-Javadoc)
     * @see com.automation.seletest.pagecomponents.pageObjects.AbstractPage#getPageLoadCondition()
     */
    @Override
    protected ExpectedCondition<?> getPageLoadCondition() {
        return ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(GitHubSearchPageLocators.DIV_SEARCH_MENU.get()).setLocator(GitHubSearchPageLocators.DIV_SEARCH_MENU.get()));

    }


}
