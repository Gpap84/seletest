package com.automation.seletest.core.services.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.seletest.core.selenium.threads.SessionContext;

public abstract class WaitBase implements ActionsSync{

    private final String profileDriver="profileDriver";
    private final String webDriverWait="wdWait";
    private final String fluentWait="fwWait";

    /**
     * Returns the driver instance
     * @return
     */
    public WebDriver getDriverInstance(){
        return (WebDriver) SessionContext.getSession().getDriverContext().getBean(profileDriver);
    }

    /**
     * Returns a new WebDriverWait instance
     * @param timeOutInSeconds
     * @return
     */
    public WebDriverWait wfExpected(long timeOutInSeconds){
        return (WebDriverWait) SessionContext.getSession().getDriverContext().getBean(webDriverWait, new Object[]{getDriverInstance(),timeOutInSeconds});
    }

    /**
     * Returns a new FluentWait instance
     * @param timeOutInSeconds
     * @param msg
     * @return
     */
    @SuppressWarnings("unchecked")
    public Wait<WebDriver> fluentWait(long timeOutInSeconds, String msg){
        return (Wait<WebDriver>) SessionContext.getSession().getDriverContext().getBean(fluentWait, new Object[]{getDriverInstance(),timeOutInSeconds, msg});
    }

}
