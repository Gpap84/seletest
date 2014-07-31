package com.automation.seletest.core.testNG.assertions;

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
     * Defines assertionType
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    public enum AssertionType{SOFT,HARD};
}
