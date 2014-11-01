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

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.LogUtils;
import com.automation.seletest.core.services.annotations.JSHandle;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.VerifyLog;
import com.automation.seletest.core.services.properties.CoreProperties;
import com.automation.seletest.core.testNG.assertions.SoftAssert;
import com.thoughtworks.selenium.SeleniumException;

/**
 * Error Handling Aspect class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Aspect
@Component
@Slf4j
public class ExceptionHandler extends SuperAspect {

    /**The log service*/
    @Autowired
    LogUtils report;

    /** Environment instance*/
    @Autowired
    Environment env;

    /**
     * Handle Exceptions...
     * @param pjp
     * @return value from ProceedingJoinPoint
     * @throws Throwable
     */
    @Around(value="logPOs() || actionsBuilderController() || takeScreenCap() || waitConditions() || sendMail()")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
            String arguments=arguments(pjp).isEmpty() ? "" : "for["+arguments(pjp)+"]";
            if(pjp.getSignature().toString().contains("pageObjects")) {
                report.warn("Page Object function: "+pjp.getSignature().getName()+" "+arguments+" executed successfully");
            }
        } catch (Exception ex) {
            if (ex instanceof TimeoutException || ex instanceof SeleniumException) {
                if(Reporter.getCurrentTestResult().getAttribute("verification")==null) {
                    report.error("Exception: "+ex.getMessage().split("\n")[0]);
                }
                throw ex;
            } else{
                log.warn(String.format("%s: Failed with exception '%s'",pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),ex.getMessage()));
            }
        }
        return returnValue;
    }

    /**
     * Around Advice for
     * @param pjp
     * @return value from ProceedingJoinPoint
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
     * @return value from ProceedingJoinPoint
     * @throws Throwable
     */
    @Around("retryExecution(retry)")
    public Object retry(ProceedingJoinPoint pjp, RetryFailure retry) throws Throwable {
        Object returnValue = null;
        for (int attemptCount = 1; attemptCount <= (1+retry.retryCount()); attemptCount++) {
            try {
                returnValue = pjp.proceed();
                report.info("Command: "+pjp.getSignature().getName()+" for ["+arguments(pjp)+"] executed!","\"color:#3366FF; font-weight: 500;\"");
                break;
            } catch (Exception ex) {
                handleRetryException(pjp, ex, attemptCount, retry);
            }
        }
        return returnValue;
    }


    /**
     * Around advice for performing JS scripts
     * @param pjp
     * @return proxied object
     * @throws Throwable
     */
    @Around("jsHandle(js)")
    public Object executeJS(ProceedingJoinPoint pjp,JSHandle js) throws Throwable {
        Object returnValue = null;
        SessionControl.webControl().changeStyle((pjp).getArgs()[0],"backgroundColor", CoreProperties.ACTION_COLOR.get());
        SessionControl.webControl().changeStyle((pjp).getArgs()[0],"borderStyle", CoreProperties.DOTTED_BORDER.get());
        returnValue = pjp.proceed();
        return returnValue;
    }


    /**
     * around advice for verification methods
     * @param pjp
     * @return proxied object
     * @throws Throwable
     */
    @Around("logVerify(verify)")
    public Object verify(ProceedingJoinPoint pjp, VerifyLog verify) throws Throwable {
        Object returnValue=null;
        try {
            Reporter.getCurrentTestResult().setAttribute("verification", true);
            returnValue = pjp.proceed();
            if(!((SessionContext.getSession().getAssertion()).getAssertion() instanceof SoftAssert)) {
                if((pjp).getArgs().length==1){
                    report.info(env.getProperty(verify.message())+" "+(pjp).getArgs()[0]+" "+env.getProperty(verify.messagePass()), "color:green; margin-left:20px;");
                } else {
                    report.info(env.getProperty(verify.message())+" "+(pjp).getArgs()[0]+" "+env.getProperty(verify.messagePass()) + " "+(pjp).getArgs()[1], "color:green; margin-left:20px;");
                }
            }
        } catch(AssertionError ex) {
            report.verificationError("[Failed Assertion]: "+env.getProperty(verify.message())+" "+arguments(pjp)+" "+env.getProperty(verify.messageFail()));
            if(verify.screenShot()) {
                SessionControl.webControl().takeScreenShot();
            }
            throw ex;
        }
        return returnValue;
    }

    @Around("webControl()")
    public Object webControl(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue=null;
        Reporter.getCurrentTestResult().removeAttribute("verification");
        returnValue = pjp.proceed();
        return returnValue;
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
            log.debug("Do not retry execution due to exception {}",ex);
            throw ex;
        } if (attemptCount == 1 + retry.retryCount()) {
            log.debug("Retry execution exceeded for method {}",invokedMethod(pjp));
            throw new RuntimeException(retry.message()+" for method: "+invokedMethod(pjp), ex);
        } else {
            report.error(String.format("%s: Attempt %d of %d failed with exception '%s'. Will retry immediately. %s",
                    pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                    attemptCount,
                    retry.retryCount(),
                    ex.getClass().getCanonicalName(),
                    ex.getMessage()));
            Thread.sleep(retry.sleepMillis());
        }
    }

    /**
     * Handle exceptions for Boolean-Integer returning type methods in web controller
     * @param pjp
     * @param ex
     * @return Proxied Object
     * @throws Throwable
     */
    private Object handleException(Object type,ProceedingJoinPoint pjp, Throwable ex) throws Throwable {
        MethodSignature signature = (MethodSignature ) pjp.getSignature();
        Class<?> returnType = signature.getReturnType();
        if(returnType.getName().compareTo("int")==0){
            log.debug("Return 0 value for method {}",invokedMethod(pjp));
            return 0;
        } else if(returnType.getName().compareTo("boolean")==0 && !pjp.getSignature().toString().contains("Not")){
            log.debug("Return false value for method {}",invokedMethod(pjp));
            return false;
        } else if(returnType.getName().compareTo("boolean")==0 && pjp.getSignature().toString().contains("Not")){
            log.debug("Return true value for method {}",invokedMethod(pjp));
            return true;
        } return null;
    }



}
