package com.automation.sele.web.testNG.assertions;

/**
 * This class represents the Assertion API
 * @author Giannis Papadakis
 *
 */
public class Assertion implements Assertable{
	
	public Assertion(){
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
}
