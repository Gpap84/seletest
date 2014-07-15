/**
 *
 */
package com.automation.sele.web.selenium.webAPI;


import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.automation.sele.web.aspectJ.RetryIfFails;
import com.automation.sele.web.selenium.threads.SessionContext;
import com.automation.sele.web.services.actions.WaitExpected;

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
    WaitExpected waitFor;

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
    @RetryIfFails(retryCount=1)
    public WebDriverActionsController<T> clickTo(Object locator, long timeout) {
        waitFor.waitForElementToBeClickable(timeout, locator).click();
        return this;
    }

    @Override
    @RetryIfFails(retryCount=1)
    public WebDriverActionsController<T> enterTo(String locator, String text, long timeout) {
        waitFor.waitForElementPresence(timeout, locator).sendKeys(text);
        return this;
    }
    
	@Override
	@RetryIfFails(retryCount=1)
	public WebDriverActionsController<T> highlight(String locator, String color) {
        jsExec.executeScript("arguments[0].style.backgroundColor=arguments[1]",waitFor.waitForElementVisibility(SessionContext.getSession().getWaitForElement(), locator),"1px solid "+color+"");
		return this;
	}

}
