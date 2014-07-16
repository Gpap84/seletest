package com.automation.seletest.core.selenium.threads.exceptions;

/**
 * This class serves as custom exception for handling generic webdriver exceptions
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class ElementNotVisibleException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ElementNotVisibleException (){}

	public ElementNotVisibleException (String message) {
		super (message);
	}

	public ElementNotVisibleException (Throwable cause){
		super (cause);
	}

	public ElementNotVisibleException (String message, Throwable cause){
		super (message, cause);
	}
}