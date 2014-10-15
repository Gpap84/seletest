/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
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

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.automation.setest.groovy.configuration.WebDriverOptions;
import com.opera.core.systems.OperaDriver;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * Configuration class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@PropertySources({@PropertySource({"BrowserSettings/browser.properties","core.properties"})})
@ImportResource({
    "classpath*:META-INF/spring/app-context.xml",
    "classpath*:META-INF/spring/mail-context.xml",
    "classpath*:META-INF/spring/thread-pool-context.xml"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ConfigurationDriver {

    @Autowired
    Environment env;

    /**
     * Chrome bean
     * @param capabilities
     * @return WebDriver instance
     */
    @Lazy(true)
    @Bean(name="chrome")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chrome(DesiredCapabilities capabilities) {
        File chromeDriverExecutable=new File(env.getProperty("ChromeDriverPath"));
        WebDriverOptions.downloadDriver(chromeDriverExecutable, env.getProperty("ChromeDriverURL"));
        System.setProperty("webdriver.chrome.driver", new File(env.getProperty("ChromeDriverPath")).getAbsolutePath());
        return new ChromeDriver(capabilities);
    }

    /**
     * Chrome with Options loaded bean
     * @return WebDriver instance
     * @throws Exception
     */
    @Lazy(true)
    @Bean(name="chromeOptions")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chromeOptions() throws Exception {
        File chromeDriverExecutable=new File(env.getProperty("ChromeDriverPath"));
        WebDriverOptions.downloadDriver(chromeDriverExecutable, env.getProperty("ChromeDriverURL"));
        System.setProperty("webdriver.chrome.driver", new File(env.getProperty("ChromeDriverPath")).getAbsolutePath());
        return new ChromeDriver(chromeOptions(new File(env.getProperty("ChromeProperties")).getAbsolutePath()));
    }

    /**
     * Load chrome options from properties file
     * @param optionsPath
     * @return ChromeOptions the chrome options loaded from properties file
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

    /**
     * Firefox bean
     * @param capabilities
     * @return WebDriver instance
     */
    @Bean(name="firefox")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver firefox(DesiredCapabilities capabilities){
        return new FirefoxDriver(capabilities);
    }

    /**
     * Internet Explorer bean
     * @param capabilities
     * @return WebDriver instance
     */
    @Bean(name="internetExplorer")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver internetExplorer(DesiredCapabilities capabilities){
        File ieDriverExecutable=new File(env.getProperty("IEDriverPath"));
        WebDriverOptions.downloadDriver(ieDriverExecutable, env.getProperty("IEDriverURL"));
        System.setProperty("webdriver.ie.driver", new File(env.getProperty("IEDriverPath")).getAbsolutePath());
        DesiredCapabilities ieCap = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        return new InternetExplorerDriver(capabilities.merge(ieCap));
    }

    /**
     * Opera bean
     * @param capabilities
     * @return WebDriver instance
     */
    @Bean(name="opera")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver opera(DesiredCapabilities capabilities){
        return new OperaDriver(capabilities);
    }

    /**
     * PhantomJS bean
     * @param capabilities
     * @return WebDriver instance
     */
    @Bean(name="phantomJS")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver phantomJS(DesiredCapabilities capabilities){
        File phantomJSDriverExecutable=new File(env.getProperty("PhantomJSDriverPath"));
        WebDriverOptions.downloadDriver(phantomJSDriverExecutable, env.getProperty("PhantomJSDriverPathURL"));
        DesiredCapabilities phantomJSCap = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,new File(env.getProperty("PhantomJSDriverPath")).getAbsolutePath());
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--ignore-ssl-errors=yes","--web-security=false","--ssl-protocol=any"});
        return new PhantomJSDriver(capabilities.merge(phantomJSCap));
    }

    /**
     * Selenium Grid bean
     * @param url
     * @param cap
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Lazy(true)
    @Bean(name="seleniumGrid")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver seleniumGrid(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new Augmenter().augment(new RemoteWebDriver(new URL(url),cap));
    }

    /**
     * Android bean
     * @param url
     * @param cap
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Lazy(true)
    @Bean(name="androidGrid")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver android(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new AndroidDriver(new URL(url),cap);
    }

    /**
     * iOS bean
     * @param url
     * @param cap
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Lazy(true)
    @Bean(name="iOSGrid")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver iOS(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new IOSDriver(new URL(url),cap);
    }


    /**
     * WebDriver wait bean
     * @param driver
     * @param timeout
     * @return WebDriverWait instance
     */
    @Lazy(true)
    @Bean(name="webdriverwait")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait wait(WebDriver driver, int timeout){
        return new WebDriverWait(driver, timeout);
    }


    /**
     * Capabilities bean
     * @return DesiredCapabilities
     */
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities capabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        return capabilities;
    }

    /**
     * Selenium bean
     * @param driver
     * @param baseUrl
     * @return Selenium
     */
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Selenium selenium(WebDriver driver, String baseUrl){
        return new WebDriverBackedSelenium(driver, baseUrl);
    }

    /**
     * android capabilities bean
     * @param appPath
     * @param appActivity
     * @param appPackage
     * @param autolaunch
     * @return DesiredCapabilities
     */
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities androidcapabilities(String appPath, String appActivity, String appPackage, String autolaunch){
        File app=new File(appPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");//how long (in seconds) Appium will wait for a new command from the client before assuming the client quit and ending the session
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("autoLaunch", Boolean.parseBoolean(autolaunch));
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY,appActivity);
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        return capabilities;
    }

    /**
     * iOS capabilities bean
     * @param appPath
     * @param udid
     * @param bundleId
     * @param autolaunch
     * @return DesiredCapabilities
     */
    @Lazy(true)
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities iOScapabilities(String appPath, String udid, String bundleId, String autolaunch){
        File app=new File(appPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");//how long (in seconds) Appium will wait for a new command from the client before assuming the client quit and ending the session
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("autoLaunch", Boolean.parseBoolean(autolaunch));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, bundleId);
        capabilities.setCapability("udid", udid);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        return capabilities;
    }


}
