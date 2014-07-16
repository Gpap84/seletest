package com.automation.seletest.core.selenium.session;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

/**
 * RemoteDriverConfiguration class
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RemoteDriverConfiguration {


    @Configuration
    @Profile({"chromeGrid"})
    public abstract static class ProfileChrome implements ProfileDriver{

        private final String beanDescription="RemoteWebDriver initialized using chrome capabilities!!!";

        @Override
        @Lazy(true)
        @Bean
        public WebDriver profileDriver(String url) throws MalformedURLException{
            return new RemoteWebDriver(new URL(url),capabilities());
        }

        @Override
        public DesiredCapabilities capabilities(){
            DesiredCapabilities capabilities=new DesiredCapabilities();
            capabilities.merge(DesiredCapabilities.chrome());
            return capabilities;
        }

        @PostConstruct
        public void init(){
            log.info(beanDescription);
        }

    }
}
