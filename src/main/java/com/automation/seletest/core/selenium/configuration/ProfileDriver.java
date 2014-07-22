/**
 *
 */
package com.automation.seletest.core.selenium.configuration;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Defines the type of Local Driver object
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public interface ProfileDriver {

    /**Driver type*/
    WebDriver profileDriver() throws Exception;

    /**Driver type with desired capabilities*/
    WebDriver profileDriver(DesiredCapabilities capabilities) throws Exception;
    
    /**The profileDriver for RemoteWebDriver*/
    WebDriver profileDriver(String url, DesiredCapabilities cap) throws MalformedURLException;
}
