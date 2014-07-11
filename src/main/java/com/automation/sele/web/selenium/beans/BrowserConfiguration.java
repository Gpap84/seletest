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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;


/**
 * This class is served as configuration class for instantiating driver of specific browser type
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Configuration
public class BrowserConfiguration {

	@Configuration
	@PropertySources({ 
		@PropertySource({ "classpath:/BrowserSettings/browser.properties" }) //chrome properties
	})
	@Profile({"chrome"})
	static class Chrome{
		
		@Autowired
		Environment env;
		
		private final String optionsPath=env.getProperty("ChromeProperties");
		
		@Bean
		public WebDriver chrome(){
			return new ChromeDriver();	
		}

		@Bean
		public WebDriver chromeWithOptions() throws FileNotFoundException, IOException{
			return new ChromeDriver(chromeOptions());	
		}

		@Bean
		public ChromeOptions chromeOptions() throws FileNotFoundException, IOException{
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
	@Configuration
	@Profile({"firefox"})
	static class Firefox{
	
		@Bean
		public WebDriver firefox(){
			return new FirefoxDriver();	
		}
		
	}

}
