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
package com.automation.seletest.core.aspectJ;


import java.io.IOException;
import java.text.NumberFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.LogUtils;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.properties.CoreProperties;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Aspect that handles logging,screenshots etc.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Aspect
@Component
public class ActionsHandler extends SuperAspect {

    /**Log service*/
    @Autowired
    LogUtils log;

    /**Constant for taking screenshot*/
    private final String takeScreencap="Take screenshot after exception: ";

    /**
     * Log returning value for get** methods
     * @param jp
     * @param returnVal
     */
    @AfterReturning(pointcut ="getReturningValue()",returning="returnVal")
    public void afterReturningAdvice(final JoinPoint jp,Object returnVal) {
        log.info("Command: "+jp.getSignature().getName()+" for["+arguments((ProceedingJoinPoint)jp)+"]"+" returned value: "+returnVal);
    }

    /**
     * Take screencap after exceptions...
     * @param joinPoint
     * @param ex Throwable
     * @throws IOException
     */
    @AfterThrowing(pointcut="waitConditions()", throwing = "ex")
    public void takeScreenCap(final JoinPoint joinPoint, Throwable ex) throws IOException {
        if(ex instanceof TimeoutException || ex instanceof SeleniumException){
            log.warn(takeScreencap+ex.getMessage().split("Build")[0].trim(),"color:orange;");
            element().takeScreenShot();
        }
    }

    /**
     * Wait for elements before any action....
     * @param pjp
     */
    @Before(value="waitElement()")
    public void waitFor(final JoinPoint pjp) {
        WaitCondition waitFor=invokedMethod(pjp).getAnnotation(WaitCondition.class);
        if(waitFor==null || waitFor.value().equals(WaitCondition.waitFor.VISIBILITY) || (waitFor.value().equals(WaitCondition.waitFor.PRESENCE) && (methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement))) {
            SessionContext.getSession().setWebElement(waitFor().waitForElementVisibility(methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.CLICKABLE)) {
            SessionContext.getSession().setWebElement(waitFor().waitForElementToBeClickable(methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.PRESENCE) && !(methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement)) {
            SessionContext.getSession().setWebElement(waitFor().waitForElementPresence((String)methodArguments((ProceedingJoinPoint)pjp)[0]));
        }
    }

    /**Report execution for method @Monitor*/
    @Around(value="monitor()")
    public Object monitorLogs(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = null;
        long start = System.currentTimeMillis();
        try {
            returnValue = pjp.proceed();
        } catch (Exception ex) {}
        long elapsedTime = System.currentTimeMillis() - start;
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.APILOGS.get())!=null &&
                Boolean.parseBoolean(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.APILOGS.get()))) {
        log.info("Execution time for method \"" + pjp.getSignature().getName() + "\": " + elapsedTime + " ms. ("+ elapsedTime/60000 + " minutes)","\"color:#0066CC;\"");
        }
        return returnValue;
    }


    /**Log memory usage before execution of method*/
    @Before("monitor()")
    public void memoryBefore(final JoinPoint pjp) {
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.JVMLOGS.get())!=null &&
                Boolean.parseBoolean(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.JVMLOGS.get()))) {
            NumberFormat format = NumberFormat.getInstance();

            log.info("JVM memory in use = "+ format.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024)+ " before executing method: "+pjp.getSignature().getName());
        }
    }

    /**Log memory usage after execution of method*/
    @After("monitor()")
    public void memoryAfter(final JoinPoint pjp) {
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.JVMLOGS.get())!=null &&
                Boolean.parseBoolean(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.JVMLOGS.get()))) {
            NumberFormat format = NumberFormat.getInstance();

            log.info("JVM memory in use = "+ format.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024)+ " before executing method: "+pjp.getSignature().getName());
        }
    }

}
