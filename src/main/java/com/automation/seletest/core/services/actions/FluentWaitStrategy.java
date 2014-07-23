/**
 *
 */
package com.automation.seletest.core.services.actions;

import org.openqa.selenium.Alert;
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
public class FluentWaitStrategy extends WaitBase{

    @Override
    public WebElement waitForElementPresence(final String locator, long waitSeconds) {
        return fluentWait(waitSeconds, NOT_PRESENT).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(Locators.findByLocator(locator).setLocator(locator));
            }
        });
    }


    @Override
    public WebElement waitForElementVisibility(final Object locator, long waitSeconds) {
        return fluentWait(waitSeconds, NOT_VISIBLE).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element=driver.findElement(Locators.findByLocator((String)locator).setLocator((String)locator));
                    return element.isDisplayed() ? element : null;
            }
        });
    }

    @Override
    public WebElement waitForElementToBeClickable(final String locator,
            long waitSeconds) {
        return fluentWait(waitSeconds, NOT_CLICKABLE).until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element=driver.findElement(Locators.findByLocator(locator).setLocator(locator));
                    return element.isEnabled() ? element : null;
            }
        });
    }

    @Override
    public Alert waitForAlert(long waitSeconds) {
        return fluentWait(waitSeconds, ALERT_NOT_PRESENT).until(new Function<WebDriver, Alert>() {
            @Override
            public Alert apply(WebDriver driver) {
                return driver.switchTo().alert();
            }
        });
    }

}
