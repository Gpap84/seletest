/**
 *
 */
package com.automation.seletest.core.selenium.webAPI;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 */
@SuppressWarnings("hiding")
public interface ActionsController<T> {

    T clickTo(String locator, long timeout);

    T enterTo(String locator, String text, long timeout);

    void quit(CloseSession type);

    public enum CloseSession{QUIT,CLOSE};

    <T extends WebDriver> T getDriverInstance();

    void getTargetHost(String url);
    
    T changeStyle(String attribute, String locator, String attributevalue);
    
	void takeScreenShot() throws IOException;
	
	void takeScreenShotOfElement(String locator) throws IOException;

}
