/**
 *
 */
package com.automation.sele.web.services.actions;


import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.automation.sele.web.selenium.threads.SessionContext;
import com.automation.sele.web.selenium.webAPI.elements.Locators;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@Component("expectedWait")
public class WaitExpected implements ActionsSync{


    @Override
    public WebElement waitForElementPresence(long waitSeconds, String locator) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).determineLocator(locator)));
    }


    @Override
    public WebElement waitForElementVisibility(long waitSeconds, Object locator) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        if(locator instanceof String){
            return wait.until(ExpectedConditions.visibilityOfElementLocated(Locators.findByLocator((String)locator).determineLocator((String)locator)));
        }
        else if(locator instanceof WebElement){
            return wait.until(ExpectedConditions.visibilityOf((WebElement)locator));
        }
        else{
            throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
        }
    }


    @Override
    public WebElement waitForElementToBeClickable(long waitSeconds, String locator) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(Locators.findByLocator(locator).determineLocator(locator)));
    }


    @Override
    public Alert waitForAlert(long waitSeconds) {
        WebDriverWait wait = new WebDriverWait((WebDriver)SessionContext.getSession().getDriverContext().getBean("profileDriver"), waitSeconds);
        return wait.until(ExpectedConditions.alertIsPresent());
    }



}
