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

import io.appium.java_client.FindsByAccessibilityId;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * By Appium extended locator strategy
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public abstract class ByAppium extends By{

    public static By accessibilitySelector(final String selector) {
        if (selector == null) {
            throw new IllegalArgumentException(
                    "Cannot find elements when the selector is null");
        }

        return new ByAccessibility(selector);

    }

    /**
     * By Accessibility Id locator strategy
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     *
     */
    public static class ByAccessibility extends By implements Serializable{

        private static final long serialVersionUID = 1L;

        private final String selector;

        public ByAccessibility(String selector) {
          this.selector = selector;
        }

        @Override
        public WebElement findElement(SearchContext context) {
          if (context instanceof FindsByAccessibilityId) {
            return ((FindsByAccessibilityId) context)
                .findElementByAccessibilityId(selector);
          }

          throw new WebDriverException(
              "Driver does not support finding an element by selector: " + selector);
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {

          throw new IllegalArgumentException(
              "Driver does not support finding elements by selector: " + selector);
        }

        @Override
        public String toString() {
          return "By.selector: " + selector;
        }
      }

}
