/**
 * 
 */
package com.automation.seletest.core.services;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class operates as a service for reading properties from various input types
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Service
public class Properties {

	
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
  
		
}
