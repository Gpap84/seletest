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
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.Logging;
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
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.click*(..))")
    private void clickController() {}//expression pointcut for function  starting with click...

    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.enter*(..))")
    private void enterController() {}//expression pointcut for function  starting with enter...

    @Pointcut("execution(* com.automation.seletest.core.selenium.common.ActionsBuilder.*(..))") // expression
    private void actionsBuilderController() {}

    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.takeScreenShot*(..))") // expression
    private void takeScreenCap() {}


    /****************************************************
     **************\\\\ADVICES\\\\***********************
     ****************************************************
     */
    @Around(value="actionsBuilderController() || takeScreenCap()")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable
    {
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
            log.info("Command: "+pjp.getSignature().getName()+" executed successfully with arguments: "+arguments(pjp));
        } catch (Exception ex) {
            if (ex instanceof TimeoutException) {
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

    @AfterThrowing(pointcut="clickController() || enterController() || actionsBuilderController()", throwing = "ex")
    public void takeScreenCap(final JoinPoint joinPoint, Throwable ex) throws IOException{
        log.warn("Take screenshot after exception: "+ex.getMessage().split("Build")[0].trim());
        SessionControl.actionsController().takeScreenShot();
    }

    @Before(value="clickController() || enterController() || actionsBuilderController()")
    public void logBefore(final JoinPoint pjp){
        log.warn("Command is about to be executed: "+pjp.getSignature().getName()+" with arguments: "+arguments((ProceedingJoinPoint)pjp));


        //Wait for element to be visible before any action
        if(methodArguments((ProceedingJoinPoint)pjp)[0] instanceof WebElement) {
            factoryStrategy.getWaitStrategy(
                    SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(
                            methodArguments((ProceedingJoinPoint)pjp)[0]);
        } else if(methodArguments((ProceedingJoinPoint)pjp)[0] instanceof String){
            factoryStrategy.getWaitStrategy(
                    SessionContext.getSession().getWaitStrategy()).waitForElementVisibility(
                            methodArguments((ProceedingJoinPoint)pjp)[0]);
        }

    }



}
