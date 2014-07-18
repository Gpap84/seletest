package com.automation.seletest.core.services;

/**
 * Enumeration for global constants
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public enum CoreProperties {


    /************************ Interfaces used **********************/
    WEB_ACTIONS_CONTROLLER("webActionController"),

    /**************************Parameters XML**********************/

    /** The parameter that determines the profile that will be used to start a new WebDriver object. */
    PROFILEDRIVER("profileDriver"),

    /** The parameter that determines the performance measures. */
    PERFORMANCE("performance"),

    /** The parameter that determines the Grid host. */
    GRID_HOST("gridHost"),

    /** The parameter that determines the Grid port. */
    GRID_PORT("gridPort"),


    /*************************************CoreProperties used for test status-actions on UI*****************************************************/
    PASS_COLOR("Chartreuse"),
    FAIL_COLOR("FireBrick"),
    ACTION_COLOR("BurlyWood"),

    /*************************************CoreProperties used for initialization phase*****************************************************/

    /** The parameter for true string*/
    TRUE("true"),

    /** The parameter for  false string*/
    FALSE("false"),

    /** The parameter that determines the test mode (Local environment or Grid) */
    MODE("mode"),

    //--------------------------------------------------    CoreProperties for locators --------------------->>>>>>>>>>>>>>>>>
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

    CoreProperties(String locator) {
        myLocator = locator;
    }

    public String get() {
        return myLocator;
    }




}
