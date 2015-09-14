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

import com.opera.core.systems.OperaDriver;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Configuration class for bean definitions.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Configuration
@EnableMBeanExport(defaultDomain = "seletest.mbeans")
@PropertySources({@PropertySource({"BrowserSettings/browser.properties","core.properties"})})
@ImportResource({
        "classpath*:META-INF/spring/app-context.xml",
        "classpath*:META-INF/spring/mail-context.xml",
        "classpath*:META-INF/spring/thread-pool-context.xml",
        "classpath*:META-INF/spring/cache-context.xml" })
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Slf4j
public class ConfigurationDriver {

    @Autowired
    Environment env;

    /**
     * Chrome bean
     * @param capabilities Desirecapabilities for WebDriver
     * @return WebDriver instance
     */
    @Bean(name="chrome")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chrome(DesiredCapabilities capabilities) {
        try {
            System.setProperty("webdriver.chrome.driver", URLDecoder.decode(getClass().getClassLoader().getResource(".").getPath(), "UTF-8")
                    + System.getProperty("file.separator") + "downloads" + System.getProperty("file.separator") + "chromedriver.exe");
        } catch (UnsupportedEncodingException e) {
            log.error("Exception occurred constructing path to chrome executable: {}", e);
        }
        return new ChromeDriver(capabilities);
    }

    /**
     * Chrome with Options loaded bean
     * @return WebDriver instance
     * @throws Exception
     */
    @Bean(name="chromeOptions")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chromeOptions() throws Exception {
        try {
            System.setProperty("webdriver.chrome.driver", URLDecoder.decode(getClass().getClassLoader().getResource(".").getPath(), "UTF-8")
                    + System.getProperty("file.separator") + "downloads" + System.getProperty("file.separator") + "chromedriver.exe");
        } catch (UnsupportedEncodingException e) {
            log.error("Exception occurred constructing path to chrome executable: {}", e);
        }
        return new ChromeDriver(chromeOptions(new File(env.getProperty("browser.chrome.properties")).getAbsolutePath()));
    }

    /**
     * Load chrome options from properties file
     * @param optionsPath String path to prprties file that stored the chrome options
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
     * @param capabilities Desirercapabilities for WebDriver
     * @return WebDriver instance
     */
    @Bean(name="firefox")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver firefox(DesiredCapabilities capabilities){
        return new FirefoxDriver(capabilities);
    }

    /**
     * The defaultTaskExecutor for thread management
     * @return ThreadPoolTaskExecutor the default thread pool task executor
     */
    @Bean(name="SeletestTaskExecutor")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("Seletest Thread Pool - ");
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setMaxPoolSize(20);
        return taskExecutor;
    }

    /**
     * Internet Explorer bean
     * @param capabilities Desirercapabilities for WebDriver
     * @return WebDriver instance
     */
    @Bean(name="ie")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver internetExplorer(DesiredCapabilities capabilities){
        try {
            System.setProperty("webdriver.ie.driver", URLDecoder.decode(getClass().getClassLoader().getResource(".").getPath(), "UTF-8") +System.getProperty("file.separator")+"downloads"+System.getProperty("file.separator")+"IEDriverServer.exe");
        } catch (UnsupportedEncodingException e) {
            log.error("Exception occurred constructing path to ie executable: {}", e);
        }
        DesiredCapabilities ieCap = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        return new InternetExplorerDriver(capabilities.merge(ieCap));
    }

    /**
     * Opera bean
     * @param capabilities Desirercapabilities for WebDriver
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
     * @param capabilities Desirercapabilities for WebDriver
     * @return WebDriver instance
     */
    @Bean(name="phantomJS")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver phantomJS(DesiredCapabilities capabilities){
        DesiredCapabilities phantomJSCap = new DesiredCapabilities();
        phantomJSCap.setJavascriptEnabled(true);
        try {
            phantomJSCap.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, URLDecoder.decode(getClass()
                    .getClassLoader().getResource(".").getPath(), "UTF-8")+System.getProperty("file.separator")+"downloads"+System.getProperty("file.separator")+"phantomjs-2.0.0-windows"+System.getProperty("file" +
                    ".separator")+"phantomjs.exe");
        } catch (UnsupportedEncodingException e) {
            log.error("Exception occured constructing path to executable: {}", e);
        }
        phantomJSCap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{
                "--ignore-ssl-errors=yes",
                "--web-security=false",
                "--ssl-protocol=any"});
        phantomJSCap.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
        phantomJSCap.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        phantomJSCap.setCapability("elementScrollBehavior",1);
        return new PhantomJSDriver(capabilities.merge(phantomJSCap));
    }

    /**
     * Selenium Grid bean
     * @param url String url of Selenium Grid
     * @param cap Desirercapabilities for WebDriver
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Bean(name="seleniumGrid")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver seleniumGrid(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new Augmenter().augment(new RemoteWebDriver(new URL(url),cap));
    }

    /**
     * Android bean
     * @param url String url of selenium Grid
     * @param cap Desirercapabilities for WebDriver
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Bean(name="androidGrid")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver android(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new AndroidDriver(new URL(url),cap);
    }

    /**
     * iOS bean
     * @param url String url of selenium grid
     * @param cap Desirercapabilities for WebDriver
     * @return WebDriver instance
     * @throws MalformedURLException
     */
    @Bean(name="iOSGrid")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver iOS(String url, DesiredCapabilities cap) throws MalformedURLException{
        return new IOSDriver(new URL(url),cap);
    }


    /**
     * WebDriver wait bean
     * @param driver WebDriver
     * @param timeout int timeout in seconds
     * @return WebDriverWait instance
     */
    @Bean(name="webdriverwait")
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait wait(WebDriver driver, int timeout){
        return new WebDriverWait(driver, timeout);
    }


    /**
     * Capabilities bean
     * @return DesiredCapabilities
     */
    @Bean
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DesiredCapabilities capabilities(){
        return new DesiredCapabilities();
    }

    /**
     * Selenium bean
     * @param driver WebDriver
     * @param baseUrl Url of web app
     * @return Selenium
     */
    @Bean
    @Lazy(true)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Selenium selenium(WebDriver driver, String baseUrl){
        return new WebDriverBackedSelenium(driver, baseUrl);
    }

    /**
     * android capabilities bean
     * @param appPath String path to apk stored
     * @param appActivity String activity to launch
     * @param appPackage String package of app
     * @param autolaunch String autolaunch specifies if application will be autolaunched or not
     * @return DesiredCapabilities
     */
    @Bean
    @Lazy(true)
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
     * @param appPath String path to app or ipa stored
     * @param udid String udid of the device
     * @param bundleId String bundleid is the package name in iOS
     * @param autolaunch String autolaunch specifies if application will be autolaunched or not
     * @return DesiredCapabilities
     */
    @Bean
    @Lazy(true)
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
