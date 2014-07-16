package com.automation.seletest.core.listeners;

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

	/**
	 * Variables for all lifecycle
	 */
	public static String suiteName = "";
	public static String outputDir = "";

	@Override
	public void onStart(ITestContext testContext) {

	}

	@Override
	public void onFinish(ITestContext context) {

		//Remove the passed configuration methods from the report
		for(ITestNGMethod m:context.getPassedConfigurations().getAllMethods()){
			context.getPassedConfigurations().removeResult(m);
		}

		//Remove the skipped configuration methods from the report
		for(ITestNGMethod m:context.getSkippedConfigurations().getAllMethods()){
			context.getSkippedConfigurations().removeResult(m);
		}

		for(int i=0;i<context.getAllTestMethods().length;i++){	
			//remove the rerun tests result from report
			if(context.getAllTestMethods()[i].getCurrentInvocationCount()==3){
				if (context.getFailedTests().getResults(context.getAllTestMethods()[i]).size() == 2 || context.getPassedTests().getResults(context.getAllTestMethods()[i]).size() == 1){
					//The following if ensure that the method is not a test method called from dataProvider
					if(context.getAllTestMethods()[i].getParameterInvocationCount()==1){
						context.getFailedTests().removeResult(context.getAllTestMethods()[i]);
					}
				}
			}
		}
	}



	@Override
	public void onTestSuccess(ITestResult testResult) {
		log.debug("Test "+ testResult.getName()+" passed after: "+(int) (((testResult.getEndMillis()-testResult.getStartMillis()) / 1000) % 60)+" seconds!!");
	}


	@Override
	public void onTestSkipped(ITestResult testResult) {
		log.debug("Test "+ testResult.getName()+" skipped after: "+(int) (((testResult.getEndMillis()-testResult.getStartMillis()) / 1000) % 60)+" seconds!!");
	}


	@Override
	public void onTestFailure(ITestResult testResult) {
		log.debug("Test "+ testResult.getName()+" failed after: "+(int) (((testResult.getEndMillis()-testResult.getStartMillis()) / 1000) % 60)+" seconds!!");
	}

	@Override
	public void onTestStart(ITestResult result) {
		log.debug("Test "+ result.getName()+" started after: "+(int) (((result.getEndMillis()- result.getStartMillis()) / 1000) % 60)+" seconds!!");
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


}
