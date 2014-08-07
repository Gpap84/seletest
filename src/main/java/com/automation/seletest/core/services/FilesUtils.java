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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.testng.ITestContext;
import org.testng.Reporter;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class operates as a service for reading properties from various input types
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
@Slf4j
public class FilesUtils {


	/**
	 * Read data from CSV file lying inside a JAR
	 * @param filePathToJar
	 * @return
	 * @throws IOException
	 */
    @SuppressWarnings({ "resource", "unused" })
	public HashMap<String, String> readcsvDatafromJar(InputStream filepath)throws IOException {
    	HashMap<String, String> parametersCSV=new HashMap<>();
    	CSVReader reader = new CSVReader(new InputStreamReader(filepath));
        String [] nextLine;
        int i=0;
        while ((nextLine = reader.readNext()) != null) {
        	parametersCSV.put(nextLine[0], nextLine[1]);
            i++;
        }
        return parametersCSV;
    }


    /**
     * Read data from CSV file
     * @param filepath
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "unused", "resource" })
	public HashMap<String, String> readcsvData(String filepath)throws IOException {
    	HashMap<String, String> parametersCSV=new HashMap<>();
        CSVReader reader = new CSVReader(new FileReader(filepath));
        String [] nextLine;
        int i=0;
        while ((nextLine = reader.readNext()) != null) {
        	parametersCSV.put(nextLine[0], nextLine[1]);
            i++;
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
        Reporter.log("<a href=\"" + relativePath(file, Reporter.getCurrentTestResult().getTestContext()) + "\"><p><img width=\"878\" src=\"" + relativePath(file, Reporter.getCurrentTestResult().getTestContext()) + "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
        log.warn("Screenshot captured, path is {}", file.getAbsolutePath());
    }

    /**
     * make a filename within the reportng folders so we can use relative paths.
     * @return the file
     * @throws IOException
     */
    public File createScreenshotFile() throws IOException {
        if (Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory() != null) {
            File outputDir = new File(new File(Reporter.getCurrentTestResult().getTestContext().getSuite().getOutputDirectory()).getParent(), "screenshots");
            FileUtils.forceMkdir(outputDir);
            return new File(outputDir, "screenshot-" + System.nanoTime() + ".png");
        }
        return new File("screenshot-" + System.nanoTime() + ".png");
    }

    /**
     * Make relative path (different OS)
     * @param file
     * @param ct
     * @return
     */
    public String relativePath(File file, ITestContext ct) {
        String outputDir = new File(ct.getSuite().getOutputDirectory()).getParent();
        if (outputDir != null) {
            String absolute = file.getAbsolutePath();
            int beginIndex = absolute.indexOf(outputDir) + outputDir.length();
            String relative = absolute.substring(beginIndex);
            return "."+relative.replace('\\', '/');
        }
        return file.getPath();
    }


}
