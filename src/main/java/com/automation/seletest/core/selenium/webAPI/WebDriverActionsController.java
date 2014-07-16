/**
 *
 */
package com.automation.seletest.core.selenium.webAPI;


import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.aspectJ.RetryFailure;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.factories.WaitStrategyFactory;

/**
 * This class contains the implementation of webDriver API
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@SuppressWarnings({"hiding","unchecked"})
@Component
@Scope("prototype")
public class WebDriverActionsController<T> extends ActionsBase{

    @Autowired
    WaitStrategyFactory waitStrategy;

    /**The webDriver object*/
    @Getter @Setter
    WebDriver driver;

    @Getter @Setter
    JavascriptExecutor jsExec;

    @Override
    public void getTargetHost(String url) {
        driver.get(url);
    }

    @Override
    public <T extends WebDriver> T getDriverInstance() {
        return (T) getDriver();
    }

    @Override
    public void quit(CloseSession type) {
        switch (type) {
        case QUIT:
            driver.quit();
            break;
        case CLOSE:
            driver.close();
            break;
        default:
            driver.quit();
            break;
        }
    }

    @Override
    @RetryFailure(retryCount=1)
    public WebDriverActionsController<T> clickTo(String locator, long timeout) {
        waitStrategy.
        getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementToBeClickable(timeout, locator).
        click();
        return this;
    }

    @Override
    @RetryFailure(retryCount=1)
    public WebDriverActionsController<T> enterTo(String locator, String text, long timeout) {
        waitStrategy.
        getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementPresence(timeout, locator).sendKeys(text);
        return this;
    }

	@Override
	public WebDriverActionsController<T> highlight(String locator, String color) {
        jsExec.executeScript("arguments[0].style.backgroundColor=arguments[1]",waitStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(SessionContext.getSession().getWaitForElement(), locator),color);
		return this;
	}

}
