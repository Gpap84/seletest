package com.automation.sele.web.listeners;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;


/**
 * WebDriver Listener used for logging and collecting info from browser
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class WebDriverListener implements WebDriverEventListener {

 
    /**
     * Instantiates a new event driver.
     * @throws Exception
     */
    public WebDriverListener(){
        System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    private void info(String message) {
        log.info(message);
        Reporter.log(message + "<br>");
    }

    private void scriptLog(String message) {
        log.info(message);
        Reporter.log("<p class=\"testOutput\" style=\"color:blue; font-size:1em;\">"+ message + "</p>");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        info("Navigate to " + url);
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
        scriptLog("The following script executed: "+script.toString());
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

}
