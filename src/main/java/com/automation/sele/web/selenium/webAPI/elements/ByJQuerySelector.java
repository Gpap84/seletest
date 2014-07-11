package com.automation.sele.web.selenium.webAPI.elements;

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