/**
 *
 */
package com.automation.sele.web.selenium.webAPI;

import org.openqa.selenium.WebDriver;

/**
 * This interface is for all methods used by webdriver to interact with app UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@SuppressWarnings("hiding")
public interface ActionsController<T> {

    T clickTo(String locator, long timeout);

    T enterTo(String locator, String text, long timeout);

    void sleep(long milliseconds);

    void quit(CloseSession type);

    public enum CloseSession{QUIT,CLOSE};

    <T extends WebDriver> T getDriverInstance();

    void getTargetHost(String url);

    T highlight(String locator, String color);

}
