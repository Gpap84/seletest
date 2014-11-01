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

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import com.automation.seletest.core.services.annotations.JSHandle;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.VerifyLog;
import com.automation.seletest.core.services.factories.StrategyFactory;

/**
 * Super class with common functions
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public abstract class SuperAspect {

    /**Factories Strategy*/
    @Autowired
    StrategyFactory<?> factoryStrategy;

    /**Methods in classpath that have @WaitCondition*/
    @Pointcut("execution(@com.automation.seletest.core.services.annotations.WaitCondition * *(..))")
    protected void waitElement() {}

    /**Methods in classpath that have @Monitor*/
    @Pointcut("execution(@com.automation.seletest.core.services.annotations.Monitor * *(..))")
    protected void monitor() {}

    /**All methods in ActionsBuilderController*/
    @Pointcut("execution(* com.automation.seletest.core.selenium.common.ActionsBuilderController.*(..))")
    protected void actionsBuilderController() {}

    /**Methods for taking screenshots!!*/
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.interfaces.WebController.takeScreenShot*(..))")
    protected void takeScreenCap() {}

    /**All Methods of WebController!!*/
    @Pointcut("execution(!boolean com.automation.seletest.core.selenium.webAPI.interfaces.WebController.*(..))")
    protected void webControl() {}

    /**Methods for wait conditions*/
    @Pointcut("execution(* com.automation.seletest.core.services.actions.*WaitStrategy.*(..))")
    protected void waitConditions() {}

    /**Methods that are returning objects*/
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.*.get*(..))")
    protected void getReturningValue() {}

    /**Methods for sending email*/
    @Pointcut("execution(* com.automation.seletest.core.services.MailUtils.*(..))")
    protected void sendMail() {}

    /** Pointcut for boolean methods inside WebAPI*/
    @Pointcut("execution(boolean com.automation.seletest.core.selenium.webAPI..*(..))")
    protected void componentsStatus() {}

    /** Pointcut for reexecuting methods*/
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI..*(..)) && @annotation(retry)")
    protected void retryExecution(RetryFailure retry) {}

    /** Pointcut for logging in Custom Verify methods*/
    @Pointcut("execution(* com.automation.seletest.core.testNG.assertions.AssertTest.*(..)) && @annotation(verify)")
    protected void logVerify(VerifyLog verify) {}

    /** Pointcut for logging PO methods*/
    @Pointcut("execution(* com.automation.seletest.pagecomponents.pageObjects..*(..))")
    protected void logPOs() {}

    /**PointCut for executing JS scripts*/
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI..*(..)) && @annotation(jshandle)")
    protected void jsHandle(JSHandle jshandle) {}


    /**
     * Type of arguments of an executed method
     * @param proceedPoint
     * @return String arguments
     */
    public String arguments(ProceedingJoinPoint proceedPoint){
        StringBuilder arguments = new StringBuilder();
        for(int i=0; i < proceedPoint.getArgs().length ;i++ ){
            MethodSignature sig = (MethodSignature)proceedPoint.getSignature();
            String methodArgument="";
            if(proceedPoint.getArgs()[i].toString().contains("->")){
                methodArgument=proceedPoint.getArgs()[i].toString().split("->")[1].replace("]", "");
            } else{
                methodArgument=proceedPoint.getArgs()[i].toString();
            }
            arguments.append("("+sig.getParameterNames()[i].toString()+" ---> "+methodArgument+") ");
        } if(arguments.toString().isEmpty()){
            return "";
        } else {
            return arguments.toString().trim();
        }
    }

    /**
     * Get method arguments
     * @param proceedPoint
     * @return arguments of proxied methods
     */
    public Object[] methodArguments(ProceedingJoinPoint proceedPoint){
        return proceedPoint.getArgs();
    }

    /**
     * Return invoked method
     * @param pjp
     * @return invoked Method
     */
    public Method invokedMethod(JoinPoint pjp) {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method m = ms.getMethod();
        return m;
    }


}
