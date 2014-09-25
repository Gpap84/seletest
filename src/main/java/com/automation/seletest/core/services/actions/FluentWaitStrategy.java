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

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.elements.Locators;
import com.google.common.base.Function;


/**
 * FluentWaitStrategy
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("fluentWait")
public class FluentWaitStrategy extends AbstractBase.WaitBase{

    @Override
    public WebElement waitForElementPresence(final String locator) {
        return fluentWait(NOT_PRESENT).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return elementToWait(driver, locator);
            }
        });
    }


    @Override
    public WebElement waitForElementVisibility(final Object locator) {
        return fluentWait(NOT_VISIBLE).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return elementToWait(driver, locator).isDisplayed() ? elementToWait(driver, locator) : null;
            }
        });
    }

    @Override
    public WebElement waitForElementToBeClickable(final Object locator) {
        return fluentWait(NOT_CLICKABLE).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return elementToWait(driver, locator).isEnabled() ? elementToWait(driver, locator) : null;
            }
        });
    }

    @Override
    public Alert waitForAlert() {
        return fluentWait(ALERT_NOT_PRESENT).until(new Function<WebDriver, Alert>() {
            @Override
            public Alert apply(WebDriver driver) {
                return driver.switchTo().alert();
            }
        });
    }

    @Override
    public Boolean waitForElementInvisibility(final String locator) {
        return fluentWait(NOT_VISIBLE).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return !driver.findElement(Locators.findByLocator(locator).setLocator(locator)).isDisplayed();
            }
        });
    }

    @Override
    public Boolean waitForTextPresentinElement(final Object locator, final String text) {
        return fluentWait(TEXT_NOT_PRESENT).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return elementToWait(driver, locator).getText().contains(text);
            }
        });
    }

    @Override
    public Boolean waitForTextPresentinValue(final Object locator, final String text) {
        return fluentWait(TEXT_NOT_PRESENT_VALUE).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return elementToWait(driver, locator).getAttribute("value").contains(text);
            }
        });
    }

    @Override
    public List<WebElement> waitForPresenceofAllElements(final String locator) {
        return fluentWait(NOT_PRESENT).until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                return elementsToWait(driver, locator);
            }
        });
    }

    @Override
    public List<WebElement> waitForVisibilityofAllElements(final String locator) {
        return fluentWait(NOT_VISIBLE).until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = elementsToWait(driver, locator);
                for(WebElement element : elements){
                    if(!element.isDisplayed()){
                        return null;
                    }
                }
                return elements.size() > 0 ? elements : null;
            }
        });
    }


    @Override
    public void waitForPageLoaded() {
        fluentWait(NOT_VISIBLE).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        });

    }

    @Override
    public void waitForAjaxCallCompleted(final long timeout) {
        fluentWait(AJAX_COMPLETE).until(new Function<WebDriver,Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                final long startTime = System.currentTimeMillis();
                final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

                while ((startTime + timeout) <= System.currentTimeMillis()) {

                    final Boolean scriptResult = (Boolean) javascriptExecutor.executeScript("return jQuery.active == 0");

                    if (scriptResult) {
                        return true;
                    }

                    threadSleep(timeout);
                }
                return false;
            }
        });
    }
}
