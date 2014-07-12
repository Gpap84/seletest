/**
 * 
 */
package com.automation.sele.web.selenium.beans;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * This class is served as configuration class for instantiating driver of specific browser type
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class BrowserConfiguration {

	@Component
	@Profile({"chrome"})
	public static class Chrome{
		
		public WebDriver chrome(){
			return new ChromeDriver();	
		}

		public WebDriver chromeWithOptions(String optionsPath) throws FileNotFoundException, IOException{
			return new ChromeDriver(chromeOptions(optionsPath));	
		}

		public ChromeOptions chromeOptions(String optionsPath) throws FileNotFoundException, IOException{
			ChromeOptions options=new ChromeOptions();
			Properties configProp = new Properties();
			configProp.load(new FileReader(optionsPath));
			Enumeration<?> keys = configProp.propertyNames();
			while(keys.hasMoreElements()){
				String key = (String)keys.nextElement();
				String value = (String) configProp.get(key);
				if(!value.isEmpty()){
					options.addArguments(key+"="+value);
				} else {
					options.addArguments(key);
				}
			}
			return options;
		}
	}
	
	/**
	 * This class defines the firefox browser
	 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
	 *
	 */
	@Component
	@Profile({"firefox"})
	public static class Firefox{
	
		public WebDriver firefox(){
			return new FirefoxDriver();	
		}
		
	}

}
