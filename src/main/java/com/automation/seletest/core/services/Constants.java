package com.automation.seletest.core.services;

/**
 * Enumeration for global constants
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public enum Constants {

	
	/************************ Interfaces used **********************/
	WEB_ACTIONS_CONTROLLER("webActionController"),
	
	/**************************Parameters XML**********************/
	
	/**The Constant that defines parallel level*/
	PARALLEL("parallel"),
	
    /** The parameter that determines the browser type. */
    BROWSERTYPE("browserType"),

    /** The parameter that determines the performance measures. */
    PERFORMANCE("performance"),

    /** The parameter that determines the Grid host. */
    GRID_HOST("gridHost"),

    /** The parameter that determines the Grid port. */
    GRID_PORT("gridPort"),

	
    /*************************************Constants used for test status*****************************************************/
    PASS_COLOR("Chartreuse"),
    FAIL_COLOR("FireBrick"),
    ACTION_COLOR("BurlyWood"),

    /*************************************Constants used for initialization phase*****************************************************/

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

    //----------------------------------------------------------------------->Parameters for testing Mobile Apps with Appium

    /** The parameter that determines extra node capability*/
    APPLICATION_NAME("applicationName"),

    /** The parameter that determines the localization */
    LOCALE("locale"),

    /** The parameter that determines the test mode (Local environment or Grid) */
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
