package com.automation.seletest.core.selenium.configuration;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Configuration class for instantiating various beans from WebDriver API
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class WebDriverConfiguration {

    @Lazy(true)
    @Bean
    @Scope("prototype")
    public WebDriverWait wdWait(WebDriver driver, long timeOutInSeconds){
        return new WebDriverWait(driver, timeOutInSeconds);
    }

    @Lazy(true)
    @Bean
    @Scope("prototype")
    public Wait<WebDriver> fwWait(WebDriver driver, long timeOutInSeconds, String msg){
        return new FluentWait<WebDriver>(driver).
                withTimeout(timeOutInSeconds, TimeUnit.SECONDS).
                pollingEvery(100,TimeUnit.MILLISECONDS).
                withMessage(msg);
    }

    @Lazy(true)
    @Bean
    @Scope("prototype")
    public Actions webActionsBuilder(WebDriver driver){
        return new Actions(driver);
    }

    @Lazy(true)
    @Bean
    @Scope("prototype")
    public TouchActions touchActionsBuilder(WebDriver driver){
        return new TouchActions(driver);
    }
    

	@Lazy(true)
    @Bean
    @Scope("prototype")
	public DesiredCapabilities capabilities(){
		DesiredCapabilities capabilities = new DesiredCapabilities();
		return capabilities;
	}

}
