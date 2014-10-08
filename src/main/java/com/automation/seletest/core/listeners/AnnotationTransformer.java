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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.TimeoutException;
import org.testng.IAnnotationTransformer2;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.automation.seletest.core.services.annotations.DataSource;
import com.automation.seletest.core.services.annotations.DataSource.Data;
import com.automation.seletest.core.testNG.DataSources;

/**
 * Test AnnotationTransformer
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class AnnotationTransformer implements IAnnotationTransformer2{

    /**The name of the DataProvider used to load properties for data driven testing*/
    private final String dataPropertiesSource="GenericDataProvider";

    /**The name of the DataProvider used to load data from excel for data driven testing*/
    private final String dataExcelSource="ExcelDataProvider";

    @Override
    @SuppressWarnings("rawtypes")
    public void transform(final ITestAnnotation test, final Class testClass, final Constructor testConstructor,
            final Method testMethod) {

        //Set DataProvider for the test
        if (testMethod != null){
            if(dataType(testMethod).equals(Data.PROPERTIES)) {
                test.setDataProviderClass(DataSources.class);
                test.setDataProvider(dataPropertiesSource);
            } else if(dataType(testMethod).equals(Data.EXCEL)){
                test.setDataProviderClass(DataSources.class);
                test.setDataProvider(dataExcelSource);
            }
        }

        //Set retry analyzer class for all @Test methods
        IRetryAnalyzer retry = test.getRetryAnalyzer();
        if (retry==null){
            test.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }

    /**
     * Condition for use of custom dataprovider
     * @param testMethod
     * @return Enum Data for dataType(csv-excel-properties)
     */
    private Data dataType(final Method testMethod) {
        if(testMethod.getAnnotation(DataSource.class) != null){
            return testMethod.getAnnotation(DataSource.class).dataType();
        } else if(testMethod.getDeclaringClass().getAnnotation(DataSource.class) != null){
            return testMethod.getDeclaringClass().getAnnotation(DataSource.class).dataType();
        }
        return Data.PROPERTIES;
    }

    /* (non-Javadoc)
     * @see org.testng.IAnnotationTransformer2#transform(org.testng.annotations.IConfigurationAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void transform(IConfigurationAnnotation annotation,  Class testClass,
           Constructor testConstructor, Method testMethod) {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see org.testng.IAnnotationTransformer2#transform(org.testng.annotations.IDataProviderAnnotation, java.lang.reflect.Method)
     */
    @Override
    public void transform(IDataProviderAnnotation annotation, Method method) {
         annotation.setParallel(usesParallelDataProvider(method));
    }

    /* (non-Javadoc)
     * @see org.testng.IAnnotationTransformer2#transform(org.testng.annotations.IFactoryAnnotation, java.lang.reflect.Method)
     */
    @Override
    public void transform(IFactoryAnnotation annotation, Method method) {
        // TODO Auto-generated method stub
    }

    /**
     * If DataProvider run in parallel for input data
     * @param testMethod
     * @return
     */
    private boolean usesParallelDataProvider(final Method testMethod) {
           return testMethod.getAnnotation(DataProvider.class).parallel();
    }


    /**
     * Retry analyzer class
     * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
     *
     */
    static class RetryAnalyzer implements IRetryAnalyzer{

        private int count = 0;

        private int maxCount = 1;

        public RetryAnalyzer() {
            setCount(maxCount);
        }

        @Override
        public boolean retry(ITestResult result) {

            if ((!result.isSuccess() &&
                    (!(result.getThrowable() instanceof TimeoutException)
                            || !(result.getThrowable() instanceof AssertionError)))) {
                if (count < maxCount) {
                    count++;
                    log.info(Thread.currentThread().getName() + "Error in "
                            + result.getName() + " with status "
                            + result.getStatus() + " Retrying " + count + " times");
                    return true;
                }

            }
            else{
                Reporter.log("<font color=\"#FF00FF\"/>"+Thread.currentThread().getName() + "Error in "
                        + result.getName() + " with status "
                        + result.getStatus() + "</font><br>");
            }
            return false;

        }

        public void setCount(int count) {
            maxCount = count;
        }
    }

}
