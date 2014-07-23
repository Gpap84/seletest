package com.automation.seletest.core.selenium.mobileAPI;

/**
 * Appium Controller interface
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
public interface AppiumController<T> {



    /**
     * This method launch application in mobile device
     * @param <T>
     * @param remoteWebDriver
     */
    T launchApp();

    /**
     * This method resets application in mobile device
     * @return
     */
    T resetApp();


    /**
     * This method resets application in mobile device
     * @return
     */
    T runAppinBackground(int sec);

    /**
     * This method closes application in mobile device
     * @return
     */
    T closeApp();



}
