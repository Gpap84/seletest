/**
 *
 */
package com.automation.seletest.core.services.actions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.common.Locators;
import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.google.common.base.Function;


/**
 * FluentWaitStrategy
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("fluentWait")
public class FluentWaitStrategy implements ActionsSync{


    private final String NOT_PRESENT="Element not present in DOM";
    private final String NOT_VISIBLE="Element not visible in Screen";
    private final String NOT_CLICKABLE="Element cannot be clicked";
    private final String ALERT_NOT_PRESENT="Alert not present";


    @Override
    public WebElement waitForElementPresence(final String locator, long waitSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SessionControl.actionsController().getDriverInstance()).
                withTimeout(waitSeconds, TimeUnit.SECONDS).
                pollingEvery(100,TimeUnit.MILLISECONDS).
                withMessage(NOT_PRESENT);

        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(Locators.findByLocator(locator).determineLocator(locator));
            }
        });
    }


    @Override
    public WebElement waitForElementVisibility(final Object locator, long waitSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SessionControl.actionsController().getDriverInstance()).
                withTimeout(waitSeconds, TimeUnit.SECONDS).
                pollingEvery(100,TimeUnit.MILLISECONDS).
                withMessage(NOT_VISIBLE);

        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element=driver.findElement(Locators.findByLocator((String)locator).determineLocator((String)locator));
                    return element.isDisplayed() ? element : null;
            }
        });
    }

    @Override
    public WebElement waitForElementToBeClickable(final String locator,
            long waitSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SessionControl.actionsController().getDriverInstance()).
                withTimeout(waitSeconds, TimeUnit.SECONDS).
                pollingEvery(100,TimeUnit.MILLISECONDS).
                withMessage(NOT_CLICKABLE);

        return wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element=driver.findElement(Locators.findByLocator(locator).determineLocator(locator));
                    return element.isEnabled() ? element : null;
            }
        });
    }

    @Override
    public Alert waitForAlert(long waitSeconds) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(SessionControl.actionsController().getDriverInstance()).
                withTimeout(waitSeconds, TimeUnit.SECONDS).
                pollingEvery(100,TimeUnit.MILLISECONDS).
                withMessage(ALERT_NOT_PRESENT);
        return wait.until(new Function<WebDriver, Alert>() {
            @Override
            public Alert apply(WebDriver driver) {
                return driver.switchTo().alert();
            }
        });
    }

}
