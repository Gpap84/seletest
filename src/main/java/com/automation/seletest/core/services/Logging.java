/**
 * 
 */
package com.automation.seletest.core.services;

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

	//For reportNG HTML
	public Logging(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
	
	/**
	 * Info.
	 *
	 * @param message
	 *            the message
	 */
	public void info(String message) {
		log.info(message);
		Reporter.log("<b><p class=\"testOutput\" style=\"color:MediumBlue; font-size:1em;\">" + message + "</p></b>");

	}

	/**
	 * Warn.
	 *
	 * @param message
	 *            the message
	 */
	public void warn(String message) {
		log.warn(message);
		Reporter.log("<b><p class=\"testOutput\" style=\"color:orange; font-size:1em;\">" + message + "</p></b>");
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(String message) {
		log.error(message);
		Reporter.log("<b><p class=\"testOutput\" style=\"color:red; font-size:1em;\">" + message + "</p></b>");
	}
	
	
	

}
