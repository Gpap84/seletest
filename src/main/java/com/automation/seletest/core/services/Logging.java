/**
 *
 */
package com.automation.seletest.core.services;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.testng.Reporter;

/**
 * Methods for logging
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
@Slf4j
public class Logging {


    @PostConstruct
    public void init(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        log.debug("'escape-output' set to false for ReportNG");
    }


    /**
     * Info log to console and in HTML report
     * @param message
     * @param style
     */
    public void info(String message, String style) {
        log.info(message);
        Reporter.log("<b><p class=\"testOutput\" style="+style+">" + message + "</p></b>");

    }

    /**
     * Default info
     * @param message
     */
    public void info(String message) {
        info(message, "\"color:black; font-size:1em;\"");
    }

    /**
     * Warn log to console and in HTML report
     * @param message
     * @param style
     */
    public void warn(String message, String style) {
        log.warn(message);
        Reporter.log("<b><p class=\"testOutput\" style="+style+">" + message + "</p></b>");
    }

    /**
     *  Default warn
     * @param message
     */
    public void warn(String message) {
       warn(message, "\"color:#663366; font-size:1em;\"");
    }

   /**
     * Error log to console and in HTML report
     * @param message
     * @param style
     */
    public void error(String message, String style) {
        log.error(message);
        Reporter.log("<b><p class=\"testOutput\" style="+style+">" + message + "</p></b>");
    }

    /**
     * Default error
     * @param message
     */
    public void error(String message) {
        error(message, "\"color:red; font-size:1em;\"");
    }


}
