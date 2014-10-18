/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
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

import java.io.File;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.FilesUtils;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.automation.seletest.core.spring.ApplicationContextProvider;

/**
 * Test Listener
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class TestListener implements ITestListener{

    private final String screenShots="/html/screenshots";
    private final String logs="/html/Logs";

    @Override
    public void onStart(ITestContext testContext) {
        log.info("Suite: "+testContext.getSuite().getName()+" started at: "+testContext.getStartDate());
        createDirectory(new File(testContext.getSuite().getOutputDirectory()).getParent()+screenShots);
        createDirectory(new File(testContext.getSuite().getOutputDirectory()).getParent()+logs);
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Suite: "+context.getSuite().getName()+" ended at: "+context.getEndDate());

        //Remove the passed configuration methods from the report
        for(ITestNGMethod m:context.getPassedConfigurations().getAllMethods()){
            if(!m.isBeforeMethodConfiguration()) {
            context.getPassedConfigurations().removeResult(m);
            }
        }

        //Remove the skipped configuration methods from the report
        for(ITestNGMethod m:context.getSkippedConfigurations().getAllMethods()){
            context.getSkippedConfigurations().removeResult(m);
        }

        //remove the rerun tests result from report
        for(int i=0;i<context.getAllTestMethods().length;i++){
            if(context.getAllTestMethods()[i].getCurrentInvocationCount()==3){
                if (context.getFailedTests().getResults(context.getAllTestMethods()[i]).size() == 2 || context.getPassedTests().getResults(context.getAllTestMethods()[i]).size() == 1){
                    if(context.getAllTestMethods()[i].getParameterInvocationCount()==1){
                        context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
                    }
                }
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        log.debug("Test "+ testResult.getName()+" passed");
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        log.debug("Test "+ testResult.getName()+" skipped");
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        log.debug("Test "+ testResult.getName()+" failed");
        LogEntries entries=ApplicationContextProvider.getApplicationContext().getBean(StrategyFactory.class).getControllerStrategy(SessionContext.getSession().getControllerStrategy()).logs(LogType.BROWSER);
        StringBuilder list=new StringBuilder();
        for (LogEntry entry : entries) {
            list.append(entry.getMessage()+"\n");
        }
        ApplicationContextProvider.getApplicationContext().getBean(FilesUtils.class).createHTML("Logs for Client", list.toString(), "Logs_"+testResult.getName());
        Reporter.log("<p class=\"testOutput\"><a href=\"Logs/Logs_"+testResult.getName()+".html\">Client Logs<a/></p>");
//        ApplicationContextProvider.getApplicationContext().getBean(MailUtils.class).sendMail("seletest.giannis@gmail.com","Failure on test: "+testResult.getName(),"Exception occured is: "+testResult.getThrowable());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.debug("Test "+ result.getName()+" started!!");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    private void createDirectory(String dir){
        File currentPath = new File(dir).getAbsoluteFile();
        if(!currentPath.exists()){
            currentPath.mkdirs();
        }
    }
}
