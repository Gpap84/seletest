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
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.utilities.LogUtils;

/**
 * Aspect that handles logging,screenshots etc.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings("unchecked")
@Aspect
@Component
public class ActionsHandler extends SuperAspect {

    /**Log service*/
    @Autowired
    LogUtils log;

    /**Constant for taking screenshot*/
    private static final String takeScreencap="Take screenshot after exception: ";

    /**
     * Log returning value for get** methods
     * @param jp JoinPoint
     * @param returnVal Object for returning value
     */
    @AfterReturning(pointcut ="getReturningValue()",returning="returnVal")
    public void afterReturningAdvice(final JoinPoint jp, Object returnVal) {
        log.info("Command: "+jp.getSignature().getName()+" for["+arguments((ProceedingJoinPoint)jp)+"]"+" returned value: "+returnVal);
    }

    /**
     * Take screencap after exceptions...
     * @param joinPoint JoinPoint
     * @param ex Throwable
     * @throws IOException
     */
    @AfterThrowing(pointcut="waitConditions()", throwing = "ex")
    public void takeScreenCap(final JoinPoint joinPoint, Throwable ex) throws IOException {
        if(Reporter.getCurrentTestResult().getAttribute("verification")==null) {
            log.warn(takeScreencap+ex.getMessage().split("Build")[0].trim(),"color:orange;");
            SessionControl.webController().takeScreenShot();
        }
    }

    /**
     * Wait for elements before any action....
     * @param pjp JoinPoint
     */
    @Before(value="waitElement()")
    public void waitFor(final JoinPoint pjp) {
        WaitCondition waitFor=invokedMethod(pjp).getAnnotation(WaitCondition.class);
        if(waitFor==null || waitFor.value().equals(WaitCondition.waitFor.VISIBILITY) || (waitFor.value().equals(WaitCondition.waitFor.PRESENCE) && (methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement))) {
            SessionContext.getSession().setWebElement(SessionControl.waitController().waitForElementVisibility(methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.CLICKABLE)) {
            SessionContext.getSession().setWebElement(SessionControl.waitController().waitForElementToBeClickable(methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.PRESENCE) && !(methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement)) {
            SessionContext.getSession().setWebElement(SessionControl.waitController().waitForElementPresence((String)methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.PRESENCEALL) && !(methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement)) {
            SessionContext.getSession().setWebElements(SessionControl.waitController().waitForPresenceofAllElements((String)methodArguments((ProceedingJoinPoint)pjp)[0]));
        } else if(waitFor.value().equals(WaitCondition.waitFor.VISIBILITYALL) || (waitFor.value().equals(WaitCondition.waitFor.PRESENCE) && (methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement))) {
            SessionContext.getSession().setWebElements(SessionControl.waitController().waitForVisibilityofAllElements((String)methodArguments((ProceedingJoinPoint)pjp)[0]));
        }
    }

    /**Report execution for method @Monitor*/
    @Around(value="monitor()")
    public Object monitorLogs(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue ;
        long start = System.currentTimeMillis();
        returnValue = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        if(LoggerFactory.getLogger(ActionsHandler.class).isDebugEnabled()) {
            log.info("Execution time for method \"" + pjp.getSignature().getName() + "\": " + elapsedTime + " ms. ("+ elapsedTime/60000 + " minutes)","\"color:#0066CC;\"");
        }
        return returnValue;
    }


    /**Log memory usage before execution of method*/
    @Before("monitor()")
    public void memoryBefore(final JoinPoint pjp) {
        if(LoggerFactory.getLogger(ActionsHandler.class).isDebugEnabled()) {
            NumberFormat format = NumberFormat.getInstance();
            log.info("JVM memory in use = "+ format.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024)+ " before executing method: "+pjp.getSignature().getName());
        }
    }

    /**Log memory usage after execution of method*/
    @After("monitor()")
    public void memoryAfter(final JoinPoint pjp) {
        if(LoggerFactory.getLogger(ActionsHandler.class).isDebugEnabled()) {
            NumberFormat format = NumberFormat.getInstance();
            log.info("JVM memory in use = "+ format.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024)+ " before executing method: "+pjp.getSignature().getName());
        }
    }

}
