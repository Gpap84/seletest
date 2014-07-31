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
package com.automation.seletest.core.selenium.webAPI.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ByJQuerySelector extends By {


    /**
     * JQuery selector expressions can be found in http://www.w3schools.com/jquery/jquery_ref_selectors.asp
     * @param selector
     * @return
     */
    public static By ByJQuery(final String selector) {
        return new ByJQuerySelectorExtended(selector);
    }

   public static class ByJQuerySelectorExtended extends ByJQuerySelector{

       private final String ownSelector;

       public ByJQuerySelectorExtended(String selector) {
           super();
           ownSelector = selector;
       }

    @SuppressWarnings("unchecked")
    @Override
    public List<WebElement> findElements(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o= ((JavascriptExecutor) context)
                .executeScript("return $('" + ownSelector+ "');");
    return (List<WebElement>) o;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        if (context instanceof WebDriver) {
            context = null;
        }
        Object o=((JavascriptExecutor) context)
                .executeScript("return $('" + ownSelector+ "').get(0);");
    	return (WebElement) o;
    }

    @Override
    public String toString() {
      return "By.jQuerySelector: " + ownSelector;
    }
   }

}