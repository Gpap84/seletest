/**
 * 
 */
package com.automation.sele.web.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.testng.Reporter;

/**
 * Methods for logging
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Slf4j
public class Logging {

	
	/**
	 * Info.
	 *
	 * @param message
	 *            the message
	 */
	public void info(String message) {
		log.info(message);
		Reporter.log("<b><p class=\"testOutput\" style=\"font-size:1em;\">" + message + "</p></b>");

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