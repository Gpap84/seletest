package com.automation.sele.web.testNG;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.testng.ITestContext;


@Service
public class TestNG {

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
    public String parallelSetup(ITestContext context){
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
