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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * BySelector class
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public abstract class BySelector extends By {


    /**
     * JQuery selector expressions can be found in http://www.w3schools.com/jquery/jquery_ref_selectors.asp
     * @param selector String selector
     * @return By object
     */
    public static By ByJQuery(final String selector) {
        if (selector == null) {
            throw new IllegalArgumentException(
                "Cannot find elements when the jquery expression is null.");
        }
        return new ByJQuerySelectorExtended(selector);
    }

    /**
     * ByJQuerySelectorExtended class.
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     */
    @Slf4j(topic="ByJQuerySelectorExtended")
   public static class ByJQuerySelectorExtended extends BySelector{

        private static final String JQUERY_LOAD_SCRIPT = "jQuerify.js";

       private final String ownSelector;

       public ByJQuerySelectorExtended(String selector) {
           super();
           ownSelector = selector;
       }

    @SuppressWarnings("unchecked")
    @Override
    public List<WebElement> findElements(SearchContext context) {
        try {
            String jQueryLoader = readFile(getClass().getResourceAsStream(JQUERY_LOAD_SCRIPT));
            ((JavascriptExecutor) context).executeAsyncScript(jQueryLoader);
            log.debug("JQuery library injected from file {}!!!", getClass().getClassLoader().getResource(JQUERY_LOAD_SCRIPT).getPath());
        } catch (IOException e) {
             log.error("Error trying to inject jquery: "+e);
        }

        Object o= ((JavascriptExecutor) context)
                .executeScript("return $('" + ownSelector+ "');");
    return (List<WebElement>) o;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        try {
            String jQueryLoader = readFile(getClass().getClassLoader().getResourceAsStream(JQUERY_LOAD_SCRIPT));
            ((JavascriptExecutor) context).executeAsyncScript(jQueryLoader);
            log.debug("JQuery library injected from file {}!!!", getClass().getClassLoader().getResource(JQUERY_LOAD_SCRIPT).getPath());
        } catch (IOException e) {
            log.error("Error trying to inject jquery: "+e);
        }
        Object o=((JavascriptExecutor) context)
                .executeScript("return $('" + ownSelector+ "').get(0);");
    	return (WebElement) o;
    }

    @Override
    public String toString() {
      return "By.jQuerySelector: " + ownSelector;
    }

    // helper method
    private static String readFile(InputStream file) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        try {
            Reader reader = new BufferedReader(new InputStreamReader(file, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        }
        finally {
            file.close();
        }
    }
  }

}