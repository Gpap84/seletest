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


import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.elements.BySelector;
import com.automation.seletest.core.selenium.webAPI.elements.Locators;
import com.automation.seletest.core.services.annotations.JSHandle;
import com.automation.seletest.core.services.annotations.Monitor;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.utilities.FilesUtils;
import com.automation.seletest.core.services.webSync.WaitFor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This class contains the implementation of WebDriver 2 API
 * for interaction with UI
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Component("webDriverControl")
public class WebDriverController extends DriverBaseController {

    /**FileUtils*/
    @Autowired
    FilesUtils fileService;

    /**
     * WaitFor Controller
     * @return WaitFor
     */
    private WaitFor<WebElement> waitController() {
        return factoryStrategy.getWaitStrategy(SessionContext.session().getWaitStrategy());
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public WebDriverController goToTargetHost(String url) {
        webDriver().get(url);
        return this;
    }

    /*************************************************************
     ************************ACTIONS SECTION*********************
     *************************************************************
     */

    @Override
    @Monitor
    @RetryFailure(retryCount=2)
    @JSHandle
    public WebDriverController click(Object locator) {
        waitController().waitForElementVisibility(locator).click();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController type(Object locator, String text) {
        waitController().waitForElementVisibility(locator).sendKeys(text);
        return this;
    }

    @Override
    public WebDriverController changeStyle(Object locator, String attribute, String attributevalue) {
        executeJS("arguments[0].style."+attribute+"=arguments[1]",waitController().waitForElementPresence(locator),attributevalue);
        return this;
    }

    /*************************************************************
     ************************SCREENSHOTS SECTION******************
     *************************************************************
     */
    @Override
    @Monitor
    public WebDriverController takeScreenShot() throws IOException{
        File scrFile = webDriver().getScreenshotAs(OutputType.FILE);
        File file = fileService.createScreenshotFile();
        FileUtils.copyFile(scrFile, file);
        fileService.reportScreenshot(file);
        return this;
    }

    @Override
    @Monitor
    public WebDriverController takeScreenShotOfElement(Object locator) throws IOException {
        File screenshot = webDriver().getScreenshotAs(OutputType.FILE);
        BufferedImage  fullImg = ImageIO.read(screenshot);
        WebElement element=waitController().waitForElementPresence(locator);
        Point point = element.getLocation();
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        Rectangle elementScreen=new Rectangle(eleWidth, eleHeight);
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), elementScreen.width, elementScreen.height);
        ImageIO.write(eleScreenshot, "png", screenshot);
        File file = fileService.createScreenshotFile();
        FileUtils.copyFile(screenshot, file);
        fileService.reportScreenshot(file);
        return this;
    }

    @Override
    @JSHandle
    public WebElement findElement(Object locator) {
        return waitController().waitForElementVisibility(locator);
    }


    /**************************************
     **Returning type methods**************
     **************************************/

    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public String getText(Object locator) {
        return waitController().waitForElementPresence(locator).getText();
    }

    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public String getTagName(Object locator) {
        return waitController().waitForElementPresence(locator).getTagName();
    }

    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public Point getLocation(Object locator) {
        return waitController().waitForElementPresence(locator).getLocation();
    }

    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public Dimension getElementDimensions(Object locator) {
        return waitController().waitForElementPresence(locator).getSize();
    }

    @Override
    @RetryFailure(retryCount=3)
    public String getPageSource() {
        return webDriver().getPageSource();
    }

    @Override
    public boolean isTextPresent(String text) {
        return getPageSource().contains(text);
    }


    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#uploadFile(java.lang.String, org.openqa.selenium.WebElement)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController uploadFile(Object locator, String path) {
        LocalFileDetector detector = new LocalFileDetector();
        File localFile = detector.getLocalFile(path);
        ((RemoteWebElement)waitController().waitForElementPresence(locator)).setFileDetector(detector);
        waitController().waitForElementPresence(locator).sendKeys(localFile.getAbsolutePath());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#executeJS(java.lang.String, java.lang.Object[])
     */
    @Override
    public Object executeJS(String script, Object... args) {
        JavascriptExecutor jsExec=webDriver();
        return jsExec.executeScript(script,args);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByValue(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController selectByValue(String locator, String value) {
        new Select(waitController().waitForElementPresence(locator)).selectByValue(value);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByLabel(java.lang.String, java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController selectByVisibleText(String locator, String text) {
        new Select(waitController().waitForElementPresence(locator)).selectByVisibleText(text);
        return this;

    }

    /*************************************************************
     ************************COOKIES SECTION*********************
     *************************************************************
     */

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getCookieNamed(java.lang.String)
     */
    @Override
    public Cookie getCookieNamed(String name) {
        return webDriver().manage().getCookieNamed(name);
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public WebDriverController deleteCookieByName(String name) {
        webDriver().manage().deleteCookieNamed(name);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public WebDriverController deleteAllCookies() {
        webDriver().manage().deleteAllCookies();
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public WebDriverController addCookie(Cookie cookie) {
        webDriver().manage().addCookie(cookie);
        return this;
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public Set<Cookie> getCookies() {
        return webDriver().manage().getCookies();
    }

    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public WebDriverController deleteCookie(Cookie cookie) {
        webDriver().manage().deleteCookie(cookie);
        return this;
    }

    /*************************************************************
     ************************TIMEOUTS SECTION*********************
     *************************************************************
     */
    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController implicitlyWait(long timeout,TimeUnit timeunit) {
        webDriver().manage().timeouts().implicitlyWait(timeout, timeunit);
        return this;
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController pageLoadTimeout(long timeout, TimeUnit timeunit) {
        webDriver().manage().timeouts().pageLoadTimeout(timeout, timeunit);
        return this;
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController scriptLoadTimeout(long timeout, TimeUnit timeunit) {
        webDriver().manage().timeouts().setScriptTimeout(timeout, timeunit);
        return this;
    }

    /*************************************************************
     ************************WINDOWS SECTION*********************
     *************************************************************
     */
    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController setWindowPosition(Point point) {
        webDriver().manage().window().setPosition(point);
        return this;
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController setWindowDimension(Dimension dimension) {
        webDriver().manage().window().setSize(dimension);
        return this;
    }

    @Override
    public Point getWindowPosition() {
        return webDriver().manage().window().getPosition();
    }

    @Override
    @RetryFailure(retryCount=3)
    public Dimension getWindowDimension() {
        return webDriver().manage().window().getSize();
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController maximizeWindow() {
        webDriver().manage().window().maximize();
        return this;
    }

    /*************************************************************
     ************************LOGS SECTION*********************
     *************************************************************
     */
    @Override
    @RetryFailure(retryCount=3)
    public LogEntries logs(String logtype) {
        return webDriver().manage().logs().get(logtype);
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController switchToLatestWindow() {
        Iterator<String> iterator = webDriver().getWindowHandles().iterator();
        String lastWindow = null;
        while (iterator.hasNext()) {
            lastWindow = iterator.next();
        }
        webDriver().switchTo().window(lastWindow);
        return this;
    }

    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController acceptAlert() {
        waitController().waitForAlert().accept();
        return this;
    }


    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController dismissAlert() {
        waitController().waitForAlert().dismiss();
        return this;
    }

    @Override
    @RetryFailure(retryCount=3)
    public int getNumberOfOpenedWindows() {
        return webDriver().getWindowHandles().size();
    }

    @Override
    @Monitor
    public WebDriverController quit(CloseSession type) {
        switch (type) {
        case QUIT:
            webDriver().quit();
            break;
        case CLOSE:
            webDriver().close();
            break;
        default:
            webDriver().quit();
            break;
        }
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#switchToFrame(java.lang.String)
     */
    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController switchToFrame(String frameId) {
        webDriver().switchTo().frame(frameId);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goBack()
     */
    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController goBack() {
        webDriver().navigate().back();
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goForward()
     */
    @Override
    @RetryFailure(retryCount=3)
    public WebDriverController goForward() {
        webDriver().navigate().forward();
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#findChildElements(java.lang.Object, java.lang.Object)
     */
    @Override
    @JSHandle
    public List<WebElement> findChildElements(Object parent, String child) {
        List<WebElement> children=waitController().waitForElementPresence(parent).findElements(Locators.findByLocator(child).setLocator(child));
        return children;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#rowsTable(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public int getRowsTable(Object tableLocator) {
        return waitController().waitForElementPresence(tableLocator).findElements(By.cssSelector("tbody tr")).size();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#columnsTable(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public int getColumnsTable(Object tableLocator) {
        return waitController().waitForElementPresence(tableLocator).findElements(BySelector.ByJQuery("tbody tr:nth-child(1) td")).size();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getFirstSelectedOption(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public String getFirstSelectedOptionText(Object locator) {
        return new Select(waitController().waitForElementPresence(locator)).getFirstSelectedOption().getText();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getAllOptionsText(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public List<String> getAllOptionsText(Object locator) {
        List<String> optionValues = new ArrayList<>();
        List<WebElement> list=new Select(waitController().waitForElementPresence(locator)).getOptions();
        for (WebElement element:list) {
            optionValues.add(element.getText());
        }
        return optionValues;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#clearSelectedOptionByText(java.lang.String)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController clearSelectedOptionByText(Object locator, String text) {
        new Select(waitController().waitForElementPresence(locator)).deselectByVisibleText(text);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#clearSelectedOption(java.lang.Object, java.lang.String)
     */
    @Override
    @RetryFailure(retryCount=3)
    @JSHandle
    public WebDriverController clearSelectedOption(Object locator,String value) {
        new Select(waitController().waitForElementPresence(locator)).deselectByValue(value);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#isFieldEditable(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    public boolean isFieldEditable(Object locator) {
        return waitController().waitForElementPresence(locator).isEnabled();
    }


    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#isFieldNotEditable(java.lang.Object)
     */
    @Override
    @RetryFailure(retryCount=3)
    public boolean isFieldNotEditable(Object locator) {
        return !waitController().waitForElementPresence(locator).isEnabled();
    }


    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#numberElementsMatching(java.lang.Object)
     */
    @Override
    public int elementsMatching(String locator) {
        return ((List<WebElement>)waitController().waitForVisibilityofAllElements(locator)).size();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#waitForAngularFinish()
     */
    @Override
    public WebDriverController waitForAngularFinish() {
        webDriver().executeAsyncScript("var callback = arguments[arguments.length - 1];angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
        return this;
    }


}
