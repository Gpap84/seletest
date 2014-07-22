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

	/**
	 * Click function
	 * @param locator
	 * @param timeout
	 * @return
	 */
    T clickTo(String locator, long timeout);

    /**
     * Enter function
     * @param locator
     * @param text
     * @param timeout
     * @return
     */
    T enterTo(String locator, String text, long timeout);

    /**
     * Quit session
     * @param type
     */
    void quit(CloseSession type);

    /**
     * Quit browser type
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public enum CloseSession{QUIT,CLOSE};

    /**
     * Gets the driver instance
     * @return
     */
    <T extends WebDriver> T getDriverInstance();

    /**
     * Gets URL
     * @param url
     */
    void getTargetHost(String url);
    
    /**
     * Change style of specific element
     * @param attribute
     * @param locator
     * @param attributevalue
     * @return
     */
    T changeStyle(String attribute, String locator, String attributevalue);
    
    /**
     * Takes screenshot
     * @throws IOException
     */
	void takeScreenShot() throws IOException;
	
	/**
	 * Take screenshot of an element
	 * @param locator
	 * @throws IOException
	 */
	void takeScreenShotOfElement(String locator) throws IOException;
	
	/**
	 * switch to latest window
	 * @return
	 */
	T switchToLatestWindow();
	
	
}
