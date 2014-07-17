/**
 *
 */
package com.automation.seletest.core.services.actions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;


/**
 * Interface for waiting methods
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com) *
 */
public interface ActionsSync {


    /** Wait for element to be present in DOM
     *
     * @param driver
     * @param waitSeconds
     * @param locator
     * @return
     */
    WebElement waitForElementPresence(String locator,long waitSeconds);

    /** Wait for element to be visible
     * @param driver
     * @param waitSeconds
     * @param locator
     * @return
     */
    WebElement waitForElementVisibility(Object locator,long waitSeconds);

    /**
     * Wait for element to be clickable
     * @param driver
     * @param waitSeconds
     * @param locator
     * @return
     */
    WebElement waitForElementToBeClickable(String locator,long waitSeconds);

    /**
     * Wait for alert
     * @param driver
     * @param waitSeconds
     * @return
     */
    Alert waitForAlert(long waitSeconds);
}
