package com.automation.sele.web.services;

/**
 * Enumeration for global constants
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public enum Constants {

    /*************************************Constants used for test status*****************************************************/
    PASS_COLOR("Lime"),
    FAIL_COLOR("OrangeRed"),

    /*************************************Constants used for initialization phase*****************************************************/

    /** The Constant METHODLISTENER. */
    METHODLISTENER("methodListener"),

    /** The Constant CLASSLISTENER. */
    CLASSLISTENER("classListener"),

    /** The parameter that determines the browser type. */
    BROWSERTYPE("browserType"),

    /** The parameter that determines the performance measures. */
    PERFORMANCE("performance"),

    /** The parameter that determines the Grid host. */
    GRID_HOST("gridHost"),

    /** The parameter that determines the Grid port. */
    GRID_PORT("gridPort"),

    /** The parameter that determines the application URL. */
    HOST_URL("hostUrl"),

    /** The parameter for true string*/
    TRUE("true"),

    /** The parameter for  false string*/
    FALSE("false"),

    //----------------------------------------------------------------------->Parameters for testing Mobile Apps with Appium

    /** The parameter that determines the mobile app name*/
    BUNDLEID("bundleId"),

    /** The parameter that determines if an application is to be launched*/
    APP_LAUNCH("appLaunch"),

    /** The parameter that determines local path to app file*/
    APP_PATH("appPath"),

    /** The parameter that determines timeout before appium shutting down application*/
    APPIUM_TIMEOUT("appiumTimeout"),

    /** The parameter that determines the device type eg. iPhone, iPad*/
    DEVICE("deviceName"),

    /** The parameter that determines the platform version*/
    PLATFORMVERSION("platformVersion"),
    //----------------------------------------------------------------------->Parameters for testing Mobile Apps with Appium

    /** The parameter that determines extra node capability*/
    APPLICATION_NAME("applicationName"),

    /** The parameter that determines the localization */
    LOCALE("locale"),

    /** The parameter that determines the test mode. */
    MODE("mode"),

    //--------------------------------------------------    Constants for locators --------------------->>>>>>>>>>>>>>>>>
    /** The Constant XPATH. */
    XPATH("xpath"),

    /** The Constant CSS. */
    CSS("css"),

    /** The Constant NAME. */
    NAME("name"),

    /** The Constant LINK. */
    LINK("link"),

    /** The Constant ID. */
    ID("id"),

    /** The Constant TAGNAME. */
    TAGNAME("tagname"),

    /** The Constant JQUERY. */
    JQUERY("jquery"),
    ;

    private String myLocator;

    Constants(String locator) {
        myLocator = locator;
    }

    public String get() {
        return myLocator;
    }




}
