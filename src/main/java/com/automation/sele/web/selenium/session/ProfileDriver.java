/**
 *
 */
package com.automation.sele.web.selenium.session;

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


    /**The DesiredCapabilities object*/
    DesiredCapabilities capabilities();


    /**The profileDriver for RemoteWebDriver*/
    WebDriver profileDriver(String url) throws MalformedURLException;
}
