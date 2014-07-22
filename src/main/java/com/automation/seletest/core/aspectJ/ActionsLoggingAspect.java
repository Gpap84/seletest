package com.automation.seletest.core.aspectJ;

/*
 * #%L
 * WebDriver QA Automation Test Framework - Core
 * Copyright (C) 2014  GiannisPapadakis
 * Copyright (c) GiannisPapadakis Intellectual Property Limited. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * * Neither the name of the GiannisPapadakis Intellectual Property Limited nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.Logging;

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

    /************************************
     ****************Pointcuts***********
     ************************************
     */
    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.click*(..))") // expression
    private void clickController() {}//expression pointcut for function  starting with click...

    @Pointcut("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.enter*(..))") // expression
    private void enterController() {}//expression pointcut for function  starting with enter...

    @AfterThrowing(pointcut="clickController() || enterController()", throwing = "ex")
    public void takeScreenCap(final JoinPoint joinPoint, Throwable ex) throws IOException{
        log.warn("Take screenshot after exception: "+ex.getMessage().split("Build")[0].trim());
        SessionControl.actionsController().takeScreenShot();
    }
    @Before(value="clickController() || enterController()")
    public void logBefore(final JoinPoint pjp){
        log.warn("Command is about to be executed: "+pjp.getSignature().getName()+" with arguments: "+arguments((ProceedingJoinPoint)pjp)+"!!!");
    }



}
