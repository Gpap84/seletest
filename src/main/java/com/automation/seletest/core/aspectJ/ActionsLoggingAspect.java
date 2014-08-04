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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.Logging;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.factories.StrategyFactory;

/**
 * Aspect that handles logging,screenshots etc.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Aspect
@Component
public class ActionsLoggingAspect extends SuperAspect{

    @Autowired
    Logging log;

    @Autowired
    StrategyFactory<?> factoryStrategy;

    /************************************
     ****************Pointcuts***********
     ************************************
     */
    @Pointcut("execution(@com.automation.seletest.core.services.annotations.WaitCondition * *(..))")
    private void waitAnnotation() {}//A pointcut that finds all methods marked with the @WaitCondition on the classpath

    @Pointcut("execution(* com.automation.seletest.core.selenium.common.ActionsBuilder.*(..))")
    private void actionsBuilderController() {}//A pointcut that finds all methods inside ActionsBuilder class

    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.takeScreenShot*(..))")
    private void takeScreenCap() {}

    @Pointcut("execution(* com.automation.seletest.core.services.actions.*WaitStrategy.*(..))")
    private void waitConditions() {}//A pointcut that finds all methods inside classes that contains WaitStrategy in name

    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.get*(..))")
    private void webElement() {}//A pointcut that finds all methods inside class  ActionsController that start with get***


    /****************************************************
     **************\\\\ADVICES\\\\***********************
     ****************************************************
     */

    /**
     * Log returning value for get** methods
     * @param jp
     * @param returnVal
     */
    @AfterReturning(pointcut ="webElement()",returning="returnVal")
    public void afterReturningAdvice(final JoinPoint jp,Object returnVal){
     log.info("Command: "+jp.getSignature().getName()+" for["+arguments((ProceedingJoinPoint)jp)+"]"+" returned value: "+returnVal);
    }

    /**
     * Handle Exceptions...
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value="actionsBuilderController() || takeScreenCap() || waitConditions()")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable
    {
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
            log.info("Command: "+pjp.getSignature().getName()+" for["+arguments(pjp)+"] executed successfully");
        } catch (Exception ex) {
            if (ex instanceof TimeoutException || ex instanceof NoSuchElementException) {
                log.error("Element not found in screen with exception: "+ex.getMessage().split("Build")[0].trim());
                throw ex;
            }
            else{
                log.error(String.format("%s: Failed with exception '%s'",
                        pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                        ex.getMessage().split("Build")[0].trim()));
            }
        }

        return returnValue;
    }

    /**
     * Take screencap after exceptions...
     * @param joinPoint
     * @param ex
     * @throws IOException
     */
    @AfterThrowing(pointcut="waitAnnotation() || actionsBuilderController()", throwing = "ex")
    public void takeScreenCap(final JoinPoint joinPoint, Throwable ex) throws IOException{
        if(ex instanceof WebDriverException || ex instanceof AssertionError){
            log.warn("Take screenshot after exception: "+ex.getMessage().split("Build")[0].trim());
            SessionControl.actionsController().takeScreenShot();
        } else {
            log.error("Unknown exception occured: "+ex.getMessage());
        }
    }

    /**
     * Wait for elements before any action....
     * @param pjp
     */
    @Before(value="waitAnnotation() || actionsBuilderController()")
    public void waitFor(final JoinPoint pjp){

        /**Determine the WebDriverWait condition*/
        WaitCondition waitFor=invokedMethod(pjp).getAnnotation(WaitCondition.class);

        if(waitFor==null || waitFor.value().equals(WaitCondition.waitFor.VISIBILITY) || methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement){
            factoryStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(methodArguments((ProceedingJoinPoint)pjp)[0]);
        } else if(waitFor.value().equals(WaitCondition.waitFor.CLICKABLE)) {
            factoryStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementToBeClickable((String)methodArguments((ProceedingJoinPoint)pjp)[0]);
        } else if(waitFor.value().equals(WaitCondition.waitFor.PRESENCE)) {
            factoryStrategy.getWaitStrategy(SessionContext.getSession().getWaitStrategy()).waitForElementPresence((String)methodArguments((ProceedingJoinPoint)pjp)[0]);
        }
    }



}
