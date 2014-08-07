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
public class LogUtils {


    @PostConstruct
    public void init(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }


    /**
     * Info log to console and in HTML report
     * @param message
     * @param style
     */
    public void info(String message, String style) {
        log.info(message);
        Reporter.log("<p class=\"testOutput\" style="+style+">" + message + "</p>");
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
        Reporter.log("<p class=\"testOutput\" style="+style+">" + message + "</p>");
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
