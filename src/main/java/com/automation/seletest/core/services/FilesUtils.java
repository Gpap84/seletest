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
package com.automation.seletest.core.services;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.testng.Reporter;
import org.testng.SkipException;

import au.com.bytecode.opencsv.CSVReader;

import com.automation.seletest.core.services.annotations.DataSource;

/**
 * This class operates as a service for reading properties from various input types
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@Slf4j
@Service
public class FilesUtils {

    /** Constant for html template*/
    private final String HTML_TEMPLATE="template.html";

    /**
     * Read data from CSV file
     * @param filepath
     * @return HashMap<String, String> with the stored data
     * @throws IOException
     */
    @SuppressWarnings({ "unused", "resource" })
    private HashMap<String, String> readcsvData(String filepath) {
        HashMap<String, String> parametersCSV=new HashMap<>();
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(filepath));
            String [] nextLine;
            int i=0;
            while ((nextLine = reader.readNext()) != null) {
                parametersCSV.put(nextLine[0], nextLine[1]);
                i++;
            }
            log.debug("CSV test data set from file {}",filepath);
        } catch (Exception e) {
            log.error("Exception during loading test data sources: "+e);
            throw new SkipException("Data not loaded for test execution!!!");
        }
        return parametersCSV;
    }

    /**
     * Log the screenshot of a failed log entry (error(...)) to
     * the logs
     * @param file
     *            file must exist
     */
    public void reportScreenshot(File file) {
        Reporter.log("<a href=\"" + file.getPath() + "\"><p><img width=\"878\" src=\"" + file.getPath() + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
        log.warn("Screenshot captured, path is {}", file.getAbsolutePath());
    }

    /**
     * make a filename within the reportng folders so we can use relative paths.
     * @return the file
     * @throws IOException
     */
    public File createScreenshotFile() throws IOException {
        if (Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory() != null) {
            File outputDir = new File(new File(Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory()).getParent(), "/html/screenshots");
            FileUtils.forceMkdir(outputDir);
            return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
        }
        return new File("screenshot-" + System.nanoTime() + ".png");
    }

    /**
     * Read data from various external sources and return to a Map
     * @param method
     * @return
     */
    public Map<String, String> readData(final Method method) {

        String inputFile=null;
        DataSource testData=null;
        Map<String, String> data = new HashMap<String, String>();

        if(method.isAnnotationPresent(DataSource.class) && method.getAnnotation(DataSource.class).filePath() !=""){
            testData=method.getAnnotation(DataSource.class);
        } else if(method.getDeclaringClass().isAnnotationPresent(DataSource.class) && method.getDeclaringClass().getAnnotation(DataSource.class).filePath()!=""){
            testData=method.getDeclaringClass().getAnnotation(DataSource.class);
        } else {
            throw new SkipException("The path to the file is undefined!!!");
        }

        inputFile=new File(testData.filePath()).getAbsolutePath();

        if(inputFile.endsWith(".properties")) {
            data=readDataFromProperties(inputFile);
        } else if(inputFile.endsWith(".csv")) {
            data=readcsvData(inputFile);
        }
        return data;
    }

    /**
     * Read Data from a properties file
     * @param inputFile
     * @return
     */
    private Map<String, String> readDataFromProperties(String inputFile){
        Properties prop=new Properties();
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
            log.debug("Test properties set from file {}",inputFile);
        } catch (Exception e) {
            log.error("Exception during loading test data sources: "+e);
            throw new SkipException("Data not loaded for test execution!!!");
        }
        return map;
    }

    /**
     * get Excel Sheet data
     * @param xlFilePath
     * @param sheetName
     * @param tableName
     * @return String[][] with excel data
     */
    public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
        String[][] tabArray=null;
        try{
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow,startCol, endRow, endCol,ci,cj;
            Cell tableStart=sheet.findCell(tableName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();
            Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);
            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
            log.debug("Excel table read for file: {}",xlFilePath);

        }
        catch (Exception e)    {
            log.error("Exception during reading from Excel file occured: "+e);
        }
        return(tabArray);
    }

    /**
     * Create an HTML file from template
     * @param title
     * @param body
     */
    public void createHTML(String title, String body, String filename) {
        try {
            File htmlTemplateFile = new File(getClass().getClassLoader().getResource(HTML_TEMPLATE).getPath()).getAbsoluteFile();
            String htmlString;
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
            htmlString = htmlString.replace("$title", title);
            htmlString = htmlString.replace("$body", body);
            File newHtmlFile = new File(new File(Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory()).getParentFile(),"/html/Logs/"+filename+".html");
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
        } catch (IOException e) {
            log.error("Exception during reading template.html: "+e);
        }
    }


}
