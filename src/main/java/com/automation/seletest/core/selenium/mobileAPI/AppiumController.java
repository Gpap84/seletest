package com.automation.seletest.core.selenium.mobileAPI;

/**
 * Appium Controller interface
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public interface AppiumController {



    /**
     * This method launch application in mobile device
     * @param remoteWebDriver
     */
    AppiumController launchApp();

    /**
     * This method resets application in mobile device
     * @return
     */
    AppiumController resetApp();


    /**
     * This method resets application in mobile device
     * @return
     */
    AppiumController runAppinBackground(int sec);

    /**
     * This method closes application in mobile device
     * @return
     */
    AppiumController closeApp();



}
