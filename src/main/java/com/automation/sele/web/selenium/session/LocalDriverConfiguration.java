/**
 * 
 */
package com.automation.sele.web.selenium.session;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.opera.core.systems.OperaDriver;


/**
 * This class is served as configuration class for instantiating driver of specific browser type
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@PropertySources({@PropertySource({"BrowserSettings/browser.properties"})})
@Configuration
public class LocalDriverConfiguration {
	
	
	@Configuration
	@Profile({"chrome"})
	public static class ProfileChrome implements ProfileDriver{
		
		private final String PATH_CHROME_DRIVER="./target/test-classes/BrowserSettings/chromedriver.exe";
		
		@Override		
		@Lazy(value = true)
		@Bean
		public WebDriver profileDriver(){
			System.setProperty("webdriver.chrome.driver", new File(PATH_CHROME_DRIVER).getAbsolutePath());
			return new ChromeDriver();	
		}
		
	}
	
	@Configuration
	@Profile({"chromeWithOptions"})
	public static class ProfileChromeWithOptions implements ProfileDriver{

		@Autowired
		Environment env;
		
		@Override
		@Lazy(value = true)
		@Bean
		public WebDriver profileDriver() throws Exception{
			return new ChromeDriver(chromeOptions(new File(env.getProperty("ChromeProperties")).getAbsolutePath()));	
		}

		public ChromeOptions chromeOptions(String optionsPath) throws Exception{
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
	public static class ProfileFirefox implements ProfileDriver{
	
		@Override
		@Bean
		@Lazy(value = true)
		public WebDriver profileDriver(){
			return new FirefoxDriver();	
		}
		
	}
	
	/**
	 * This class defines the ie browser
	 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
	 *
	 */
	@Configuration
	@Profile({"ie"})
	public static class ProfileInternetExplorer implements ProfileDriver{
	
		@Override
		@Bean
		@Lazy(value = true)
		public WebDriver profileDriver(){
			return new InternetExplorerDriver();	
		}

	}
	
	/**
	 * This class defines the opera browser
	 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
	 *
	 */
	@Configuration
	@Profile({"opera"})
	public static class ProfileOpera implements ProfileDriver{
	
		@Override
		@Bean
		@Lazy(value = true)
		public WebDriver profileDriver(){
			return new OperaDriver();	
		}

	}

}
