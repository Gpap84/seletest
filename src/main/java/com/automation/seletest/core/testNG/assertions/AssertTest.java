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

package com.automation.seletest.core.testNG.assertions;

import java.util.concurrent.Future;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.testng.asserts.Assertion;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.SeleniumTest.AssertionType;
import com.automation.seletest.core.services.annotations.VerifyLog;
import com.automation.seletest.core.services.factories.StrategyFactory;

/**
 * This class represents the Assertion API
 * @author Giannis Papadakis
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AssertTest<T extends Assertion> {

    /**Environment instance*/
    @Autowired
    Environment env;

    /** Assertion object*/
    @Getter @Setter
    private T assertion;

    /**The strategy used*/
    @Autowired
    StrategyFactory<?> strategy;


    /**
     * Specify the type of assertion (Hard or Soft) for this test
     * @param assertionType
     * @return
     */
    public AssertTest setAssertionType(AssertionType assertionType){
        if(assertionType.equals(AssertionType.HARD)) {
            setAssertion((T) new Assertion());
        } else {
            setAssertion((T) new SoftAssert());
        } return this;
    }


    /**
     * Decide test status on Soft failures
     */
    public void assertAll(){
        if(assertion instanceof SoftAssert) {
            ((SoftAssert) this.assertion).assertAll();
        }
    }

    /**
     * Verify that element is present in Screen
     * @param locator
     * @return AssertTest instance
     */
    @Async
    @VerifyLog(messageFail = "notFound" , messagePass = "found", message = "elementLocator", screenShot = false)
    public Future<Boolean> elementPresent(String locator) {
        assertion.assertTrue(strategy.getControllerStrategy(SessionContext.getSession().getControllerStrategy()).isWebElementPresent(locator),env.getProperty("elementLocator")+" "+locator+" "+env.getProperty("found"));
        return new AsyncResult<>(true);
    }

    /**
     * Verify that element is visible in Screen
     * @param locator
     * @return AssertTest instance
     */
    @Async
    @VerifyLog(messageFail = "notFoundVisible" , messagePass = "foundVisible", message = "elementLocator", screenShot = false)
    public Future<Boolean> elementVisible(String locator) {
        assertion.assertTrue(strategy.getControllerStrategy(SessionContext.getSession().getControllerStrategy()).isWebElementVisible(locator),env.getProperty("elementLocator")+" "+locator+" "+env.getProperty("foundVisible"));
        return new AsyncResult<>(true);
    }

    /**
     * Verify that text is present in element
     * @param locator
     * @param text
     * @return AssertTest instance
     */
    @Async
    @VerifyLog(messageFail = "notFoundwithText" , messagePass = "foundwithText", message = "elementLocator", screenShot = false)
    public Future<Boolean> textPresentinElement(Object locator, String text) {
        assertion.assertTrue(strategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForTextPresentinElement(locator, text),env.getProperty("elementLocator")+" "+locator+" "+env.getProperty("foundwithText") + " "+text);
        return new AsyncResult<>(true);
    }

    /**
     * Verify that text is present in value attribute of element
     * @param locator
     * @param text
     * @return AssertTest instance
     */
    @Async
    @VerifyLog(messageFail = "notFoundwithText" , messagePass = "foundwithText", message = "elementLocator", screenShot = false)
    public Future<Boolean> textPresentValueAttribute(Object locator, String text) {
        assertion.assertTrue(strategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForTextPresentinValue(locator, text),env.getProperty("elementLocator")+" "+locator+" "+env.getProperty("foundwithText") + " "+text);
        return new AsyncResult<>(true);
    }
}
