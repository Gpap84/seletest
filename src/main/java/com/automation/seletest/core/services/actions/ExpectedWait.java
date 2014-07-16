/**
 *
 */
package com.automation.seletest.core.services.actions;


import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.elements.Locators;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@Component("expectedWait")
public class ExpectedWait implements ActionsSync{


    @Override
    public WebElement waitForElementPresence(String locator, long waitSeconds) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).setLocator(locator)));
    }


    @Override
    public WebElement waitForElementVisibility(Object locator, long waitSeconds) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        if(locator instanceof String){
            return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.findByLocator((String)locator).setLocator((String)locator)));
        }
        else if(locator instanceof WebElement){
            return wait.until(ExpectedConditions.visibilityOf((WebElement)locator));
        }
        else{
            throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
        }
    }


    @Override
    public WebElement waitForElementToBeClickable(String locator, long waitSeconds) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(Locators.findByLocator(locator).setLocator(locator)));
    }


    @Override
    public Alert waitForAlert(long waitSeconds) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.alertIsPresent());
    }



}
