package com.automation.seletest.core.common;

import org.openqa.selenium.By;

/**
 * Enum for locators returning By object providing input in findElement
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public enum Locators {

    //--------------------------------------------------    CoreProperties for Locators --------------------->>>>>>>>>>>>>>>>>
    /** The Constant XPATH. */
    XPATH("xpath"){

        @Override
        public By determineLocator(String locator) {
            return By.xpath(findActualLocator(locator));
        }
    },

    /** The Constant CSS. */
    CSS("css"){

        @Override
        public By determineLocator(String locator) {
            return By.cssSelector(findActualLocator(locator));
        }
    },

    /** The Constant XPATHEXPR. */
    XPATHEXPR("//"){

        @Override
        public By determineLocator(String locator) {
            return By.xpath(locator);
        }
    },


    /** The Constant NAME. */
    NAME("name"){

        @Override
        public By determineLocator(String locator) {
            return By.name(findActualLocator(locator));
        }
    },


    /** The Constant LINK. */
    LINK("link"){

        @Override
        public By determineLocator(String locator) {
            return By.linkText(findActualLocator(locator));
        }
    },

    /** The Constant ID. */
    ID("id"){

        @Override
        public By determineLocator(String locator) {
            return By.id(findActualLocator(locator));
        }
    },

    /** The Constant TAGNAME. */
    TAGNAME("tagname"){

        @Override
        public By determineLocator(String locator) {
            return By.tagName(findActualLocator(locator));
        }
    },
   ;

    public abstract By determineLocator(String locator);

    /**The value of enum type*/
    private String value;

    private Locators(final String locator) {
        this.value = locator;

    }

    public synchronized static String findActualLocator(String locator){
        return locator.substring(locator.indexOf('=')+1);
    }

    /**Get locator by value
     *
     * @return
     */
    public String getLocator() {
        return value;
    }

    /**Return enum for given value*/
    static synchronized public Locators findByLocator(String locator) {
        if (locator != null) {
            for (Locators locatorUsed : Locators.values()) {
                if (locator.startsWith(locatorUsed.getLocator())) {
                    return locatorUsed;
                }
            }
        }

        return null;
    }



}
