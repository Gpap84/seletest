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

package com.automation.seletest.core.listeners;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.AnnotationUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.automation.seletest.core.services.annotations.AppiumTest;
import com.automation.seletest.core.services.annotations.WebTest;
import com.automation.seletest.core.services.properties.CoreProperties;

@Slf4j
public class InitListener implements IInvokedMethodListener{

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        //Set parameter for specifying Application Type
        if(method.getTestMethod().isBeforeSuiteConfiguration()){
            log.debug("Specify browser type by finding custom annotation on class level!!!");
            Object webannotation=AnnotationUtils.findAnnotation(method.getTestMethod().getTestClass().getRealClass(), WebTest.class);
            Object mobileannotation=AnnotationUtils.findAnnotation(method.getTestMethod().getTestClass().getRealClass(), AppiumTest.class);

            if(webannotation!=null){
                testResult.getTestContext().getCurrentXmlTest().addParameter(CoreProperties.APPLICATION_TYPE.get(), CoreProperties.WEBTYPE.get());
            } else if (mobileannotation!=null){
                testResult.getTestContext().getCurrentXmlTest().addParameter(CoreProperties.APPLICATION_TYPE.get(), CoreProperties.MOBILETYPE.get());
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // TODO Auto-generated method stub

    }





}
