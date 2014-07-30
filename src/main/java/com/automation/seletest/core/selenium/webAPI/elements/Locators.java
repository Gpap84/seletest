package com.automation.seletest.core.selenium.webAPI.elements;


import org.openqa.selenium.By;

public enum Locators {

    //--------------------------------------------------    CoreProperties for Locators --------------------->>>>>>>>>>>>>>>>>
    /** The Constant XPATH. */
    XPATH("xpath"){

        @Override
        public By setLocator(String locator) {
            return By.xpath(findLocatorSubstring(locator));
        }
    },

    /** The Constant CSS. */
    CSS("css"){

        @Override
        public By setLocator(String locator) {
            return By.cssSelector(findLocatorSubstring(locator));
        }
    },

    /** The Constant XPATHEXPR. */
    XPATHEXPR("//"){

        @Override
        public By setLocator(String locator) {
            return By.xpath(locator);
        }
    },


    /** The Constant NAME. */
    NAME("name"){

        @Override
        public By setLocator(String locator) {
            return By.name(findLocatorSubstring(locator));
        }
    },


    /** The Constant LINK. */
    LINK("link"){

        @Override
        public By setLocator(String locator) {
            return By.linkText(findLocatorSubstring(locator));
        }
    },

    /** The Constant ID. */
    ID("id"){

        @Override
        public By setLocator(String locator) {
            return By.id(findLocatorSubstring(locator));
        }
    },

    /** The Constant TAGNAME. */
    TAGNAME("tagname"){

        @Override
        public By setLocator(String locator) {
            return By.tagName(findLocatorSubstring(locator));
        }
    },

    /** The Constant JQUERY. */
    JQUERY("jquery"){

        @Override
        public By setLocator(String locator) {
            return ByJQuerySelector.ByJQuery(findLocatorSubstring(locator));
        }
    },
   ;
    public abstract By setLocator(String locator);

    /**The value of enum type*/
    private String value;

    private Locators(final String locator) {
        this.value = locator;

    }

    synchronized static String findLocatorSubstring(String locator){
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
