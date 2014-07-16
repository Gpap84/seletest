package com.automation.sele.web.selenium.webAPI.elements;

import io.appium.java_client.FindsByAccessibilityId;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public abstract class ByAppium extends By{

    public static By accessibilitySelector(final String selector) {
        if (selector == null) {
            throw new IllegalArgumentException(
                    "Cannot find elements when the selector is null");
        }

        return new ByAccessibility(selector);

    }

    public static class ByAccessibility extends By implements Serializable{

        /**
         *
         */
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
