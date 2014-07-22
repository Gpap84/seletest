package com.automation.seletest.core.selenium.configuration;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.testng.Reporter;

import com.automation.seletest.core.services.CoreProperties;
import com.automation.setest.groovy.configuration.WebDriverOptions;
import com.opera.core.systems.OperaDriver;


/**
 * This class is served as configuration class for instantiating driver of specific browser type
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@PropertySources({
    @PropertySource({"BrowserSettings/browser.properties"})})
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class LocalDriverConfiguration {

    @Autowired
    Environment env;

    @PostConstruct
    public void init(){
        log.info("Download and set appropriate driver executables!!!");

        //download ChromeDriver.exe if not exists
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.PROFILEDRIVER.get()).startsWith("chrome")){
            File chromeDriverExecutable=new File(env.getProperty("ChromeDriverPath"));
            WebDriverOptions.downloadDriver(chromeDriverExecutable, env.getProperty("ChromeDriverURL"));
        }

        //download IEDriverServer.exe if not exists
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.PROFILEDRIVER.get()).startsWith("ie")){
            File ieDriverExecutable=new File(env.getProperty("IEDriverPath"));
            WebDriverOptions.downloadDriver(ieDriverExecutable, env.getProperty("IEDriverURL"));
        }

        //download PhantomJS.exe if not exists
        if(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.PROFILEDRIVER.get()).startsWith("phantom")){
            File phantomJSDriverExecutable=new File(env.getProperty("PhantomJSDriverPath"));
            WebDriverOptions.downloadDriver(phantomJSDriverExecutable, env.getProperty("PhantomJSDriverPathURL"));
        }
    }


    @Configuration
    @Profile({"chrome"})
    public abstract static class ProfileChrome implements ProfileDriver{

        @Autowired
        Environment env;

        @Override
        @Lazy(true)
        @Bean
        public WebDriver profileDriver(){
            return new ChromeDriver();
        }

        @PostConstruct
        public void init() throws InterruptedException {
            System.setProperty("webdriver.chrome.driver", new File(env.getProperty("ChromeDriverPath")).getAbsolutePath());
            log.info("ChromeDriver initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
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

        /**
         * Load chrome options from properties file
         * @param optionsPath
         * @return
         * @throws Exception
         */
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
            System.setProperty("webdriver.chrome.driver", new File(env.getProperty("ChromeDriverPath")).getAbsolutePath());
            log.info("ChromeDriver with Options initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
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

        @Autowired
        Environment env;

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new FirefoxDriver();
        }

        @PostConstruct
        public void init(){
            log.info("FirefoxDriver initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
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

        @Autowired
        Environment env;

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new InternetExplorerDriver(internetexplorerCap());
        }

        //Internet Explorer capabilities for security bypass
        private DesiredCapabilities internetexplorerCap(){
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            return capabilities;
        }

        @PostConstruct
        public void init(){
            System.setProperty("webdriver.ie.driver", new File(env.getProperty("IEDriverPath")).getAbsolutePath());
            log.info("IEDriver initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");

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

        @Autowired
        Environment env;

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new OperaDriver();
        }

        @PostConstruct
        public void init(){
            log.info("OperaDriver initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
        }

    }

    /**
     * Create constant with active profiles
     * @param env
     * @return
     */
    private static String activeProfiles(Environment env){
        StringBuilder profiles=new StringBuilder();
        for(String s: env.getActiveProfiles()){
            profiles.append(s+" ");
        }
        return profiles.toString();
    }

    /**
     * This class defines the opera browser
     * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
     *
     */
    @Configuration
    @Profile({"phantomJS"})
    public abstract static class ProfilePhantomJS implements ProfileDriver{

        @Autowired
        Environment env;

        @Override
        @Bean
        @Lazy(true)
        public WebDriver profileDriver(){
            return new PhantomJSDriver(capabilities());
        }

        //PhantomJS for security bypass and executable file
        @Override
        public DesiredCapabilities capabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,new File(env.getProperty("PhantomJSDriverPath")).getAbsolutePath());
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--ignore-ssl-errors=yes","--web-security=false","--ssl-protocol=any"});
            return capabilities;
        }

        @PostConstruct
        public void init(){
            log.info("PhantomJS initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
        }

    }






}
