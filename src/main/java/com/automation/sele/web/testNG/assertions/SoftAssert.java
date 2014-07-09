package com.automation.sele.web.testNG.assertions;

/**
 * This class represents the soft assertions (without throwing exceptions)
 * @author Giannis Papadakis
 *
 */
public class SoftAssert implements Assertable{

	public SoftAssert(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
}
