package com.automation.sele.web.testNG.assertions;

import lombok.Getter;
import lombok.Setter;

import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.automation.sele.web.selenium.common.SessionControl.AssertionType;

/**
 * This class represents the soft assertions (without throwing exceptions)
 * @author Giannis Papadakis
 *
 */
public class Assert implements Assertable{

    @Getter @Setter
    private Object assertion;

    public Assert(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }


    /**
     * Specify the type of assertion (Hard or Soft)
     * @param assertionType
     * @return
     */
    public Assert setAssertionType(AssertionType assertionType){
        if(assertionType.equals(AssertionType.HARD)) {
            setAssertion(new Assertion());
        }
        else {
            setAssertion(new SoftAssert());
        }
        return this;
    }

    /**
     * Verify after test complete
     * @return
     */
    public Assert assertAll(){
        if(assertion instanceof SoftAssert) {
            ((SoftAssert) this.assertion).assertAll();
        }
        return this;
    }


    @Override
    public Assert textPresent(String text) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Assert textPresentinElement(String element, String text) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Assert elementPresent(String element) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Assert numberOfElementsPresent() {
        // TODO Auto-generated method stub
        return null;
    }


}
