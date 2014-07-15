package com.automation.sele.web.testNG.assertions;

import lombok.Getter;
import lombok.Setter;



/**
 * This class represents the Assertion API
 * @author Giannis Papadakis
 *
 */
public class Assertion implements Assertable{
	
	@Getter @Setter
    private Object assertion;
	
	public Assertion(){
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		 if(assertion==null) {
	            setAssertionType(AssertionType.SOFT);
	        }
	}
	

    /**
     * Specify the type of assertion (Hard or Soft)
     * @param assertionType
     * @return
     */
    public Assertion setAssertionType(AssertionType assertionType){
        if(assertionType.equals(AssertionType.HARD)) {
            setAssertion(new Assertion());
        }
        else {
            setAssertion(new SoftAssert());
        }
        return this;
    }
    
    /**
     * Defines the assertion type
     * @author PI000003
     *
     */
    public enum AssertionType{SOFT,HARD};
}
