package com.automation.seletest.core.testNG;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.automation.seletest.core.services.annotations.AppiumTest;
import com.automation.seletest.core.services.annotations.WebTest;


@Service
public class TestNG {


    /**
     * Returns the WebTest annotation
     * @param testResult
     * @return
     */
    public WebTest getWebAnnotation(ITestResult testResult){
        if(testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(WebTest.class)){
            return testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(WebTest.class);
        } else if(testResult.getTestClass().getClass().isAnnotationPresent(WebTest.class)){
            return testResult.getTestClass().getClass().getAnnotation(WebTest.class);
        } else {
            return null;
        }
    }


    /**
     * If test method or class is Web Test
     * @param testResult
     * @return
     */
    public boolean isWeb(ITestResult testResult){
        if(testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(WebTest.class)){
            return true;
        } else if(testResult.getTestClass().getClass().isAnnotationPresent(WebTest.class)){
            return false;
        } else {
            return false;
        }
    }

    /**
     * If custom annotation that defines mode of testing exists in Class or Method level
     * @param testResult
     * @return
     */
    public boolean isAppium(ITestResult testResult){
        if(testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(AppiumTest.class)){
            return true;
        } else if(testResult.getTestClass().getClass().isAnnotationPresent(AppiumTest.class)){
            return false;
        } else {
            return false;
        }
    }

    /**
     * Returns the MobileTest annotation
     * @param testResult
     * @return
     */
    public AppiumTest getMobileAnnotation(ITestResult testResult){
        if(testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(AppiumTest.class)){
            return testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AppiumTest.class);
        } else if(testResult.getTestClass().getClass().isAnnotationPresent(AppiumTest.class)){
            return testResult.getTestClass().getClass().getAnnotation(AppiumTest.class);
        } else {
            return null;
        }
    }


    /**
     * Gets all XML parameters
     * @param context
     * @return
     */
    public Map<String,String> getAllXMLParameters(ITestContext context){
       return  context.getCurrentXmlTest().getAllParameters();
    }

    /**
     * Gets parallel setup
     * @param context
     * @return
     */
    public String getParallel(ITestContext context){
        return context.getCurrentXmlTest().getParallel();
    }

    /**
     * Gets specific parameter from xml
     * @param context
     * @param parameter
     * @return
     */
    public String getParameterXML(ITestContext context, String parameter){
        return context.getCurrentXmlTest().getParameter(parameter);
    }


}
