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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

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


    @Configuration
    @Profile({"chrome"})
    public abstract static class ProfileChrome implements ProfileDriver{

        @Autowired
        Environment env;

        @Override
        @Lazy(true)
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(DesiredCapabilities capabilities){
            return new ChromeDriver(capabilities);
        }

        @PostConstruct
        public void init() throws InterruptedException {
            File chromeDriverExecutable=new File(env.getProperty("ChromeDriverPath"));
            WebDriverOptions.downloadDriver(chromeDriverExecutable, env.getProperty("ChromeDriverURL"));
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
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
            File chromeDriverExecutable=new File(env.getProperty("ChromeDriverPath"));
            WebDriverOptions.downloadDriver(chromeDriverExecutable, env.getProperty("ChromeDriverURL"));
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
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(DesiredCapabilities capabilities){
            return new FirefoxDriver(capabilities);
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
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(DesiredCapabilities capabilities){
            return new InternetExplorerDriver(capabilities.merge(cap()));
        }

        private DesiredCapabilities cap(){
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            return capabilities;
        }

        @PostConstruct
        public void init(){
            File ieDriverExecutable=new File(env.getProperty("IEDriverPath"));
            WebDriverOptions.downloadDriver(ieDriverExecutable, env.getProperty("IEDriverURL"));
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
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(DesiredCapabilities capabilities){
            return new OperaDriver(capabilities);
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
     * This class defines the phantomJS support
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
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public WebDriver profileDriver(DesiredCapabilities capabilities){
            return new PhantomJSDriver(capabilities.merge(securityCap()));
        }

        private DesiredCapabilities securityCap(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,new File(env.getProperty("PhantomJSDriverPath")).getAbsolutePath());
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--ignore-ssl-errors=yes","--web-security=false","--ssl-protocol=any"});
            return capabilities;
        }

        @PostConstruct
        public void init(){
            File phantomJSDriverExecutable=new File(env.getProperty("PhantomJSDriverPath"));
            WebDriverOptions.downloadDriver(phantomJSDriverExecutable, env.getProperty("PhantomJSDriverPathURL"));
            log.info("PhantomJS initialized with active profiles: {"+LocalDriverConfiguration.activeProfiles(env).trim()+"}!!!");
        }

    }
}
