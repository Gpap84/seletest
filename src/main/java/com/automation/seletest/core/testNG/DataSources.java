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
package com.automation.seletest.core.testNG;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import com.automation.seletest.core.services.annotations.DataDriven;


/**
 * DataSources class
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class DataSources {

    /**
     * Generic dataProvider that returns data from a Map
     * @param method
     * @return Object[][] with the Map that contains properties
     * @throws Exception
     */
    @DataProvider(name = "GenericDataProvider")
    public static Object[][] getDataProvider(final Method method, ITestContext context) throws Exception {
        Map<String, String> map = readData(method,context);
        return new Object[][] { { map } };
    }


    /**
     * Read data from Properties File and return to a Map
     * @param method
     * @return
     */
    private static Map<String, String> readData(Method method,ITestContext context) {

        String inputFile=null;

        if(method.isAnnotationPresent(DataDriven.class) && method.getAnnotation(DataDriven.class).filePath() !=""){
            inputFile=new File(method.getAnnotation(DataDriven.class).filePath()).getAbsolutePath();
        } else if(method.getDeclaringClass().isAnnotationPresent(DataDriven.class) && method.getDeclaringClass().getAnnotation(DataDriven.class).filePath()!=""){
            inputFile=new File(method.getDeclaringClass().getAnnotation(DataDriven.class).filePath()).getAbsolutePath();
        } else if(context.getCurrentXmlTest().getParameter("filePath")!=null){
            inputFile=context.getCurrentXmlTest().getParameter("filePath");
        } else {
            throw new SkipException("The path to the file is undefined!!!");
        }

        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> map = new HashMap<String, String>();

        try {
            prop.load(new FileReader(inputFile));
            Enumeration<?> keys = prop.propertyNames();
            while(keys.hasMoreElements()){
                String key = (String)keys.nextElement();
                String value = (String) prop.get(key);
                if(!value.isEmpty()){
                    map.put(key,prop.getProperty(key));
                } else {
                    log.error("No value specified for key: "+key);
                }
            }
        } catch (Exception e) {
            log.error("Exception during loading test data sources: "+e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("Exception during closing InputStream for loading test data sources: "+e);
                }
            }
        }

        return map;

    }
}
