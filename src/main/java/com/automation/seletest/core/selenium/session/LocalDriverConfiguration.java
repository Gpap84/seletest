/**
 *
 */
package com.automation.seletest.core.selenium.session;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
@Slf4j
@PropertySources({@PropertySource({"BrowserSettings/browser.properties"})})
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LocalDriverConfiguration {

    @Configuration
    @Profile({"chrome"})
    public abstract static class ProfileChrome implements ProfileDriver{

        @Autowired
        Environment env;

        private final String PATH_CHROME_DRIVER="./target/test-classes/BrowserSettings/";

        @Override
        @Lazy(true)
        @Bean
        public WebDriver profileDriver(){
            System.setProperty("webdriver.chrome.driver", new File(PATH_CHROME_DRIVER).getAbsolutePath()+"/chromedriver.exe");
            return new ChromeDriver();
        }

        @PostConstruct
        public void init() throws IOException, InterruptedException{
            StringBuilder profiles=new StringBuilder();
            for(String s: env.getActiveProfiles()){
                profiles.append(s+" ");
            }
            log.info("ChromeDriver initialized with active profiles: {"+profiles.toString().trim()+"}!!!");

        }

    }

    @Configuration
    @Profile({"chromeWithOptions"})
    public abstract static class ProfileChromeWithOptions implements ProfileDriver{

        @Autowired
        Environment env;

        @Override
        @Lazy(true)
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

        @PostConstruct
        public void init(){
            log.info("ChromeDriver with Options initialized!!!");

        }

    }

    /**
     * This class defines the firefox browser
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    @Configuration
    @Profile({"firefox"})
    public abstract static class ProfileFirefox implements ProfileDriver{

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new FirefoxDriver();
        }

        @PostConstruct
        public void init(){
            log.info("FirefoxDriver initialized!!!");

        }
    }

    /**
     * This class defines the ie browser
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    @Configuration
    @Profile({"ie"})
    public abstract static class ProfileInternetExplorer implements ProfileDriver{

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new InternetExplorerDriver();
        }

        @PostConstruct
        public void init(){
            log.info("IEDriver initialized!!!");

        }

    }

    /**
     * This class defines the opera browser
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    @Configuration
    @Profile({"opera"})
    public abstract static class ProfileOpera implements ProfileDriver{

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new OperaDriver();
        }

        @PostConstruct
        public void init(){
            log.info("OperaDriver initialized!!!");

        }

    }


}
