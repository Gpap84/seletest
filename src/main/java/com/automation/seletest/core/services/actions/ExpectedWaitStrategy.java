/**
 *
 */
package com.automation.seletest.core.services.actions;



import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.elements.Locators;

/**
 * ExpectedWaitStrategy
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("expectedWait")
public class ExpectedWaitStrategy extends WaitBase{

    @Override
    public WebElement waitForElementPresence(String locator, long waitSeconds) {
        return wfExpected(waitSeconds).until
                (ExpectedConditions.
                        presenceOfElementLocated(Locators.findByLocator(locator).setLocator(locator)));
    }


    @Override
    public WebElement waitForElementVisibility(Object locator, long waitSeconds) {
        if(locator instanceof String){
            return wfExpected(waitSeconds).until(ExpectedConditions.visibilityOfElementLocated(Locators.findByLocator((String)locator).setLocator((String)locator)));
        }
        else if(locator instanceof WebElement){
            return wfExpected(waitSeconds).until(ExpectedConditions.visibilityOf((WebElement)locator));
        }
        else{
            throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
        }
    }


    @Override
    public WebElement waitForElementToBeClickable(String locator, long waitSeconds) {
        return wfExpected(waitSeconds).until(ExpectedConditions.elementToBeClickable(Locators.findByLocator(locator).setLocator(locator)));
    }


    @Override
    public Alert waitForAlert(long waitSeconds) {
        return wfExpected(waitSeconds).until(ExpectedConditions.alertIsPresent());
    }



}
