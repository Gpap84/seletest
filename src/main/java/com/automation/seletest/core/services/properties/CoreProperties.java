/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.automation.seletest.core.services.properties;

/**
 * Enumeration for global constants
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public enum CoreProperties {

    /**************************Parameters XML**********************/
    /** The parameter that determines the browser type to be used. */
    BROWSERTYPE("browserType"),

    /** The parameter that determines the type of Application under Test. */
    APPLICATION_TYPE("applicationType"),

    /** The parameter that determines that application is Web based. */
    WEBTYPE("web"),

    /** The parameter that determines that application is Mobile based (Android-iOS). */
    MOBILETYPE("mobile"),

    /** The parameter that determines the profile that will be used to start a new WebDriver object. */
    CAPABILITIES("capabilities"),

    /** The parameter that determines the capabilities to start a new WebDriver object. */
    PROFILEDRIVER("profileDriver"),

    /** The parameter that determines the performance measures. */
    PERFORMANCE("performance"),

    /** The parameter that determines the Grid host. */
    GRID_HOST("gridHost"),

    /** The parameter that determines the Grid port. */
    GRID_PORT("gridPort"),

    /**Parameter that determined the URL of the web app under test*/
    HOST_URL("hostURL"),


    /*************************MOBILE PROPERTIES*************************************************/
    /** The parameter that determines the mobile app name*/
    BUNDLEID("bundleId"),

    /** The parameter that determines if an application is to be launched*/
    APP_LAUNCH("applaunch"),

    /** The parameter that determines local path to app file*/
    APP_PATH("apppath"),

    /*************************************CoreProperties used for test status-actions on UI*****************************************************/
    PASS_COLOR("Chartreuse"),
    FAIL_COLOR("FireBrick"),
    ACTION_COLOR("BurlyWood"),

    /*************************************CoreProperties used for initialization phase*****************************************************/

    /** The parameter for true string*/
    TRUE("true"),

    /** The parameter for  false string*/
    FALSE("false"),

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
