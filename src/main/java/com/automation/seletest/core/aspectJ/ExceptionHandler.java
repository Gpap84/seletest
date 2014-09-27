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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.LogUtils;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.VerifyLog;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.services.properties.CoreProperties;
import com.automation.seletest.core.testNG.assertions.AssertTest;
import com.automation.seletest.core.testNG.assertions.SoftAssert;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Error Handling Aspect
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Aspect
@Component
public class ExceptionHandler extends SuperAspect {

    /**The log service*/
    @Autowired
    LogUtils log;

    /** Environment instance*/
    @Autowired
    Environment env;

    /**Factories Strategy*/
    @Autowired
    StrategyFactory<?> factoryStrategy;

    /**
     * Handle Exceptions...
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value="actionsBuilderController() || takeScreenCap() || waitConditions() || sendMail()")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
            if(!pjp.getTarget().toString().contains("WaitStrategy") || invokedMethod(pjp).getName().contains("takeScreen")) {
                log.info("Command: "+pjp.getSignature().getName()+" for["+arguments(pjp)+"] executed successfully");
            }
        } catch (Exception ex) {
            if (ex instanceof TimeoutException || ex instanceof NoSuchElementException || ex instanceof SeleniumException) {
                log.error("Exception: "+ex.getMessage().split("Build")[0].trim());
                throw ex;
            } else{
                log.error(String.format("%s: Failed with exception '%s'",
                        pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                        ex.getMessage().split("Build")[0].trim()));
            }
        }
        return returnValue;
    }
    /**
     * Around Advice for
     * @param pjp
     * @return Output of executed method
     * @throws Throwable
     */
    @Around("componentsStatus()")
    public Object handleReturningFunctions(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = null;
        try {
            returnValue= pjp.proceed();
        } catch (Exception ex) {
            returnValue=handleException(returnValue, pjp, ex);
        }
        return returnValue;
    }

    /**
     * Around advice with custom annotation for retrying execution of JoinPoint
     * @param pjp
     * @param retry
     * @return
     * @throws Throwable
     */
    @Around("retryExecution(retry)")
    public Object retry(ProceedingJoinPoint pjp, RetryFailure retry) throws Throwable {
        Object returnValue = null;
        for (int attemptCount = 1; attemptCount <= (1+retry.retryCount()); attemptCount++) {
            try {
                returnValue = pjp.proceed();
                log.info("Command: "+pjp.getSignature().getName()+" for ["+arguments(pjp)+"] executed successfully");
                element().changeStyle((pjp).getArgs()[0],"backgroundColor", CoreProperties.ACTION_COLOR.get());
                break;
            } catch (Exception ex) {
                handleRetryException(pjp, ex, attemptCount, retry);
            }
        }
        return returnValue;
    }


    /**
     * around advice for verification methods
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("logVerify(verify)")
    public Object verify(ProceedingJoinPoint pjp, VerifyLog verify) throws Throwable {
        Object returnValue=null;
        try {
            returnValue = pjp.proceed();
            if(!(((AssertTest<?>) SessionContext.getSession().getControllers().get(AssertTest.class)).getAssertion() instanceof SoftAssert)) {
                if((pjp).getArgs().length==1){
                    log.info(env.getProperty(verify.message())+" "+(pjp).getArgs()[0]+" "+env.getProperty(verify.messagePass()), "color:green; margin-left:20px;");
                } else {
                    log.info(env.getProperty(verify.message())+" "+(pjp).getArgs()[0]+" "+env.getProperty(verify.messagePass() + " "+(pjp).getArgs()[1]), "color:green; margin-left:20px;");
                }
            }
        } catch(AssertionError ex) {
            log.verificationError("[Failed Assertion]: "+env.getProperty(verify.message())+" "+arguments(pjp)+" "+env.getProperty(verify.messageFail()));
            if(verify.screenShot()) {
                element().takeScreenShot();
            } throw ex;
        } return returnValue;
    }

    /**
     * Method for handling exceptions....
     * @param pjp
     * @param ex
     * @param attemptCount
     * @param retry
     * @throws Throwable
     */
    private void handleRetryException(ProceedingJoinPoint pjp, Throwable ex, int attemptCount, RetryFailure retry) throws Throwable {
        if (ex instanceof TimeoutException || ex instanceof SeleniumException) {
            throw ex;
        } if (attemptCount == 1 + retry.retryCount()) {
            throw new RuntimeException(retry.message()+" for method: "+pjp.getKind(), ex);
        } else {
            log.error(String.format("%s: Attempt %d of %d failed with exception '%s'. Will retry immediately. %s",
                    pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                    attemptCount,
                    retry.retryCount(),
                    ex.getClass().getCanonicalName(),
                    ex.getMessage().split("Build info")[0].trim()));
            Thread.sleep(retry.sleepMillis());
        }
    }

    /**
     * Handle exceptions for Boolean-Integer returning type methods in web controller
     * @param pjp
     * @param ex
     * @return
     * @throws Throwable
     */
    private Object handleException(Object type,ProceedingJoinPoint pjp, Throwable ex) throws Throwable {
        if (ex instanceof TimeoutException || ex instanceof SeleniumException ){
            MethodSignature signature = (MethodSignature ) pjp.getSignature();
            Class<?> returnType = signature.getReturnType();
            if(returnType.getName().compareTo("int")==0){
                return 0;
            } else if(returnType.getName().compareTo("boolean")==0 && !pjp.getSignature().toString().contains("Not")){
                return false;
            } else if(returnType.getName().compareTo("boolean")==0 && pjp.getSignature().toString().contains("Not")){
                return true;
            }
        } return null;
    }



}
