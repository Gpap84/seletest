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
package com.automation.seletest.core.selenium.webAPI;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.FilesUtils;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;
import com.automation.seletest.core.services.factories.StrategyFactory;

/**
 * This class contains the implementation of webDriver API
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 * @param <T>
 *
 */
@SuppressWarnings({"unchecked"})
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WebDriverController implements WebController<WebDriverController>{

    @Autowired
    FilesUtils fileService;

    @Autowired
    StrategyFactory<?> factoryStrategy;

    /**The webDriver object*/
    @Getter @Setter
    WebDriver driver;

    @Getter @Setter
    JavascriptExecutor jsExec;

    @Override
    public void goToTargetHost(String url) {
        driver.get(url);
    }

    @Override
    public WebDriver driverInstance() {
        return getDriver();
    }

    @Override
    public void quit(CloseSession type) {
        switch (type) {
        case QUIT:
            driver.quit();
            break;
        case CLOSE:
            driver.close();
            break;
        default:
            driver.quit();
            break;
        }
    }

    /*************************************************************
     ************************ACTIONS SECTION*********************
     *************************************************************
     */

    @Override
    @WaitCondition(waitFor.CLICKABLE)
    @RetryFailure(retryCount=1)
    public WebDriverController clickTo(Object locator) {
        SessionContext.getSession().getWebElement().click();
        return this;
    }

    @Override
    @WaitCondition(waitFor.VISIBILITY)
    @RetryFailure(retryCount=1)
    public WebDriverController enterTo(Object locator, String text) {
        SessionContext.getSession().getWebElement().sendKeys(text);
        return this;
    }

    @WaitCondition(waitFor.VISIBILITY)
    @Override
    public WebDriverController changeStyle(Object locator, String attribute, String attributevalue) {
        jsExec.executeScript("arguments[0].style."+attribute+"=arguments[1]",SessionContext.getSession().getWebElement(),attributevalue);
        return this;
    }

    /*************************************************************
     ************************SCREENSHOTS SECTION*********************
     *************************************************************
     */

    @Override
    public void takeScreenShot() throws IOException{
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File file = fileService.createScreenshotFile();
        FileUtils.copyFile(scrFile, file);
        fileService.reportScreenshot(file);
    }

    @Override
    public void takeScreenShotOfElement(String locator) throws IOException {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage  fullImg = ImageIO.read(screenshot);
        WebElement element=factoryStrategy.getWaitStrategy(getWait()).waitForElementVisibility(locator);
        Point point = element.getLocation();
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        File file = fileService.createScreenshotFile();
        FileUtils.copyFile(screenshot, file);
        fileService.reportScreenshot(file);
    }

    /*************************************************************
     ************************WINDOWS SECTION*********************
     *************************************************************
     */
    @Override
    public WebDriverController switchToLatestWindow() {
        Iterator<String> iterator = driver.getWindowHandles().iterator();
        String lastWindow = null;
        while (iterator.hasNext()) {
            lastWindow = iterator.next();
        }
        driver.switchTo().window(lastWindow);
        return this;
    }

    /**
     * Gets the strategy for Wait<WebDriver>
     * @return
     */
    private String getWait(){
        return SessionContext.getSession().getWaitStrategy();
    }


    /*************************************************************
     ************************COOKIES SECTION*********************
     *************************************************************
     */
    @Override
    public WebDriverController deleteCookieNamed(String name) {
        driver.manage().deleteCookieNamed(name);
        return this;
    }

    @Override
    public WebDriverController cookiesAllDelete() {
        driver.manage().deleteAllCookies();
        return this;
    }


    @Override
    public WebDriverController cookieAdd(Cookie cookie) {
        driver.manage().addCookie(cookie);
        return this;
    }


    @Override
    public Set<Cookie> getCookies() {
        Set<Cookie> cookies=driver.manage().getCookies();
        return cookies;
    }


    @Override
    public WebDriverController cookieDelete(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
        return this;
    }

    @Override
    public WebElement findElement(String locator) {
        return factoryStrategy.getWaitStrategy(getWait()).waitForElementVisibility(locator);
    }




    /**************************************
     **Returning type methods**************
     ***************************************/
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public String getText(Object locator) {
        return SessionContext.getSession().getWebElement().getText();
    }

    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public String getTagName(Object locator) {
        return SessionContext.getSession().getWebElement().getTagName();
    }

    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public Point getLocation(Object locator) {
        return SessionContext.getSession().getWebElement().getLocation();
    }

    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public Dimension getElementDimensions(Object locator) {
        return SessionContext.getSession().getWebElement().getSize();
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    /**************************************
     *Verification type methods**************
     ***************************************/

    @Override
    public boolean isWebElementPresent(String locator) {
        factoryStrategy.getWaitStrategy(getWait()).waitForElementPresence(locator);
        return true;
    }


    @Override
    public boolean isTextPresent(String text) {
        if(getPageSource().contains(text)){
            return true;
        } else {
            return false;
        }
    }

}
