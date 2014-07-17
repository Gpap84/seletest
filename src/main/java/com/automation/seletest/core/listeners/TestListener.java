package com.automation.seletest.core.listeners;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;


/**
 * Test Listener
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class TestListener implements ITestListener,IAnnotationTransformer{

    private final String screenShots="./target/surefire-reports/screenshots";
    private final String logs="./target/surefire-reports/logs";

    @Override
    public void onStart(ITestContext testContext) {
        log.info("Suite: "+testContext.getSuite().getName()+" started at: "+testContext.getStartDate());

        createDirectory(screenShots);
        createDirectory(logs);
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("Suite: "+context.getSuite().getName()+" ended at: "+context.getEndDate());

        //Remove the passed configuration methods from the report
        for(ITestNGMethod m:context.getPassedConfigurations().getAllMethods()){
            context.getPassedConfigurations().removeResult(m);
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
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.debug("Test "+ result.getName()+" started!!");
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @SuppressWarnings("rawtypes")
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
            Constructor testConstructor, Method testMethod) {

        //Set retry analyzer class for all @Test methods
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry==null){
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }

    }

    /**
     * Create a directory
     * @param dir
     */
    private void createDirectory(String dir){
        File currentPath = new File(dir);
        if(!currentPath.exists()){
            currentPath.mkdirs();
        }
    }


}
