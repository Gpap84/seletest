package com.automation.sele.web.spring;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("SeleniumGrid")
@Configuration
public class WebProperties {

    @Bean
    public DesiredCapabilities capabilities(){
        DesiredCapabilities capabilies=new DesiredCapabilities();
        return capabilies;
    }

    @Bean
    public Actions actionsBuilder(){
        Actions actionsBuilder=new Actions(chromeDriver());
        return actionsBuilder;
    }

    @Bean
    public WebDriver chromeDriver(){
        WebDriver chromeDriver=new ChromeDriver();
        return chromeDriver;
    }
}
