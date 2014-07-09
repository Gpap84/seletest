package com.automation.sele.web.testNG.assertions;

/**
 * This class represents the hard verifications (throwing exceptions)
 * @author Giannis Papadakis
 *
 */
public class HardAssert implements Assertable{
	
	public HardAssert(){
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
}
