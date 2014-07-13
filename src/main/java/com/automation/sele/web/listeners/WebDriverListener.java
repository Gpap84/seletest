package com.automation.sele.web.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.automation.sele.web.services.Logging;


/**
 * WebDriver Listener used for logging and collecting info from browser
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class WebDriverListener implements WebDriverEventListener {

	@Autowired
	Logging log;
 
    /**
     * Instantiates a new log driver.
     * @throws Exception
     */
    public WebDriverListener(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

  

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
    	log.info("Navigate to " + url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
      
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
       
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
      
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {
    	log.warn("The following script executed: "+script.toString());
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

}
