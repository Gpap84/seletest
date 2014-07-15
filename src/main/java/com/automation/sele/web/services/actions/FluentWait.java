/**
 *
 */
package com.automation.sele.web.services.actions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Scope("prototype")
public class FluentWait implements ActionsSync{ {

}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementPresence(java.lang.Object, long, java.lang.String)
 */
@Override
public WebElement waitForElementPresence(long waitSeconds, String locator) {
    // TODO Auto-generated method stub
    return null;
}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementVisibility(java.lang.Object, long, java.lang.Object)
 */
@Override
public WebElement waitForElementVisibility(long waitSeconds, Object locator) {
    // TODO Auto-generated method stub
    return null;
}

/* (non-Javadoc)
 * @see com.automation.sele.web.services.actions.ActionsSync#waitForElementToBeClickable(org.openqa.selenium.WebDriver, long, java.lang.Object)
 */
@Override
public WebElement waitForElementToBeClickable( long waitSeconds, Object locator) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public Alert waitForAlert(long waitSeconds) {
    // TODO Auto-generated method stub
    return null;
}

@Override
public WebDriver switchToFrame(long waitSeconds, String locator) {
    // TODO Auto-generated method stub
    return null;
}
}
