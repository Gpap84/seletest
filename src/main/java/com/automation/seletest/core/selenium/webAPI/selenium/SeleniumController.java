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
package com.automation.seletest.core.selenium.webAPI.selenium;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.MainController;
import com.automation.seletest.core.services.FilesUtils;
import com.automation.seletest.core.services.annotations.Monitor;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumControl")
@Slf4j
public class SeleniumController<T extends DefaultSelenium> extends DriverBaseController<T> implements MainController{

    /**The FileUtils*/
    @Autowired
    FilesUtils fileService;

    @Override
    public WebElement findElement(Object locator) {
        throw new UnsupportedOperationException("findElements(object locator) method is not supported for Selenium 1");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#click(java.lang.Object)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.CLICKABLE)
    @RetryFailure(retryCount=1)
    public void click(Object locator) {
        selenium().click((String)locator);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#type(java.lang.Object, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.VISIBILITY)
    @RetryFailure(retryCount=1)
    public void type(Object locator, String text) {
        selenium().typeKeys((String)locator, text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#goToTargetHost(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public void goToTargetHost(String url) {
        selenium().open(url);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#changeStyle(java.lang.Object, java.lang.String, java.lang.String)
     */
    @Override
    @WaitCondition(waitFor.VISIBILITY)
    public void changeStyle(Object locator, String attribute, String attributevalue) {
        selenium().highlight((String)locator);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#takeScreenShot()
     */
    @Override
    @Monitor
    public void takeScreenShot() throws IOException {
        String base64Screenshot = selenium().captureScreenshotToString();
        byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
        FileOutputStream fos = null;
        File screenShotFrame = fileService.createScreenshotFile();
        try {
            fos = new FileOutputStream(screenShotFrame);
            fos.write(decodedScreenshot);
        } finally {
            if (null != fos) {
                fos.close();
            }
            fileService.reportScreenshot(screenShotFrame);
        }

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#takeScreenShotOfElement(java.lang.Object)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.VISIBILITY)
    public void takeScreenShotOfElement(Object locator) throws IOException {
        String base64Screenshot = selenium().captureScreenshotToString();
        byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
        FileOutputStream fos = null;
        File screenShotFrame = fileService.createScreenshotFile();
        try {
            fos = new FileOutputStream(screenShotFrame);
            fos.write(decodedScreenshot);
        } finally {
            if (null != fos) {
                fos.close();
            }
            BufferedImage  fullImg = ImageIO.read(screenShotFrame);
            int px=(Integer) selenium().getElementPositionLeft((String)locator);
            int py=(Integer) selenium().getElementPositionTop((String)locator);
            int eleWidth = (Integer) selenium().getElementWidth((String)locator);
            int eleHeight = (Integer) selenium().getElementHeight((String)locator);
            BufferedImage eleScreenshot= fullImg.getSubimage(px, py, eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenShotFrame);
            File file = fileService.createScreenshotFile();
            FileUtils.copyFile(screenShotFrame, file);
            fileService.reportScreenshot(file);
        }
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getText(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public String getText(Object locator) {
        return selenium().getText((String)locator);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getTagName(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public String getTagName(Object locator) {
        return selenium().getAttribute((String)locator+"@tagName");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getLocation(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public Point getLocation(Object locator) {
        int px =(Integer) selenium().getElementPositionLeft((String)locator);
        int py=(Integer) selenium().getElementPositionTop((String)locator);
        Point p=new Point(px, py);
        return p;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getElementDimensions(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public Dimension getElementDimensions(Object locator) {
        int height = (Integer) (selenium().getElementHeight((String)locator));
        int width = (Integer) selenium().getElementWidth((String)locator);
        return new Dimension(width, height);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getPageSource()
     */
    @Override
    @RetryFailure(retryCount=1)
    public String getPageSource() {
        return selenium().getHtmlSource();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#isWebElementPresent(java.lang.String)
     */
    @Override
    public boolean isWebElementPresent(String locator) {
        waitController().waitForElementPresence(locator);
        return true;
    }

    /* (non-Java0doc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#isTextPresent(java.lang.String)
     */
    @Override
    public boolean isTextPresent(String text) {
        if(getPageSource().contains(text)){
            return true;
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#isWebElementVisible(java.lang.Object)
     */
    @Override
    public boolean isWebElementVisible(Object locator) {
        waitController().waitForElementVisibility(locator);
        return true;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#uploadFile(java.lang.Object, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public void uploadFile(Object locator, String path) {
        selenium().attachFile((String)locator, path);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#executeJS(java.lang.String, java.lang.Object[])
     */
    @Override
    @RetryFailure(retryCount=1)
    public Object executeJS(String script, Object... args) {
        return selenium().getEval(script);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByValue(java.lang.String, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public void selectByValue(String locator, String value) {
        selenium().select(locator,"value="+value);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByVisibleText(java.lang.String, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public void selectByVisibleText(String locator, String text) {
        selenium().select(locator,"label="+text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookieByName(java.lang.String)
     */

    @Override
    @RetryFailure(retryCount=1)
    @Monitor
    public void deleteCookieByName(String name) {
        selenium().deleteCookie(name, "");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookie(org.openqa.selenium.Cookie)
     */
    @Deprecated
    @Override
    public void deleteCookie(Cookie cookie) {
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteAllCookies()
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=1)
    public void deleteAllCookies() {
        selenium().deleteAllVisibleCookies();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#addCookie(org.openqa.selenium.Cookie)
     */
    @Deprecated
    @Override
    public void addCookie(Cookie cookie) {

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getCookies()
     */
    @Override
    @Deprecated
    public Set<Cookie> getCookies() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void implicitlyWait(long timeout, TimeUnit timeunit) {
        // TODO Auto-generated method stub
        //HttpCommandProcessor command = new  HttpCommandProcessor();
        //command.doCommand("setImplicitWaitLocator", new String[] {"10000",});

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    @RetryFailure(retryCount=1)
    public void pageLoadTimeout(long timeout, TimeUnit timeunit) {
        selenium().setTimeout(String.valueOf(timeout));
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#scriptLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Deprecated
    @Override
    public void scriptLoadTimeout(long timeout, TimeUnit timeunit) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowPosition(org.openqa.selenium.Point)
     */
    @Override
    @RetryFailure(retryCount=1)
    public void setWindowPosition(Point point) {
        int x=point.getX();
        int y=point.getY();
        selenium().getEval("window.resizeTo(" + x + ", " + y + "); window.moveTo(0,0);");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowDimension(org.openqa.selenium.Dimension)
     */
    @Override
    public void setWindowDimension(Dimension dimension) {

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getWindowPosition()
     */
    @Override
    @RetryFailure(retryCount=1)
    public Point getWindowPosition() {
        int width = Integer.parseInt(selenium().getEval("screen.width"));
        int height = Integer.parseInt(selenium().getEval("screen.height"));
        Point p=new Point(width, height);
        return p;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getWindowDimension()
     */
    @Override
    public Dimension getWindowDimension() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#maximizeWindow()
     */
    @Override
    @RetryFailure(retryCount=1)
    public void maximizeWindow() {
       selenium().windowMaximize();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#logs(java.lang.String)
     */
    @Deprecated
    @Override
    public LogEntries logs(String logtype) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#switchToLatestWindow()
     */
    @Override
    @RetryFailure(retryCount=1)
    public void switchToLatestWindow() {
        String[] windows = selenium().getAllWindowIds();
        String[] windowsTitles = selenium().getAllWindowTitles();
        selenium().selectWindow(windows[windows.length - 1]);
        log.info("Window with title: " + windowsTitles[windowsTitles.length - 1] + " selected");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#getNumberOfOpenedWindows()
     */
    @Override
    @RetryFailure(retryCount=1)
    public int getNumberOfOpenedWindows() {
        String[] windows=selenium().getAllWindowIds();
        return windows.length;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#acceptAlert()
     */
    @Override
    @WaitCondition(waitFor.ALERT)
    @RetryFailure(retryCount=1)
    public void acceptAlert() {
        selenium().getEval("window.confirm = function(msg) { return true; }");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#dismissAlert()
     */
    @Override
    @WaitCondition(waitFor.ALERT)
    @RetryFailure(retryCount=1)
    public void dismissAlert() {
        selenium().getEval("window.confirm = function(msg) { return false; }");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#quit(com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession)
     */
    @Override
    @Monitor
    public void quit(CloseSession type) {
        switch (type) {
        case QUIT:
            selenium().stop();
            break;
        case CLOSE:
            selenium().close();
            break;
        default:
            selenium().stop();
            break;
        }
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#switchToFrame(java.lang.String)
     */
    @Override
    @RetryFailure(retryCount=1)
    public void switchToFrame(String frameId) {
        selenium().selectFrame(frameId);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goBack()
     */
    @Override
    @RetryFailure(retryCount=1)
    public void goBack() {
        selenium().goBack();
        waitController().waitForPageLoaded();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goForward()
     */
    @Deprecated
    @Override
    public void goForward() {

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#findChildElements(java.lang.Object, java.lang.String)
     */
    @Override
    public List<WebElement> findChildElements(Object parent, String child) {
        throw new UnsupportedOperationException("Method findChildElements(Object parent, String child) is not supported with Selenium 1");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#rowsTable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public int getrowsTable(Object tableLocator) {
        int rows = 0;
        String locator=(String)tableLocator;
        if (locator.startsWith("xpath=") || locator.startsWith("//")) {
            rows = selenium().getXpathCount(locator + "//tbody//tr").intValue();
        } else if (locator.startsWith("css=") || locator.startsWith("jquery=")) {
            rows = selenium().getCssCount(locator + " tbody tr").intValue();
        } return rows;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#columnsTable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public int getcolumnsTable(Object tableLocator) {
        int columns = 0;
        String locator=(String)tableLocator;
        if (locator.startsWith("xpath=") || locator.startsWith("//")) {
            columns = selenium().getXpathCount(locator + "//tbody//tr[1]/td").intValue();
        } else if (locator.startsWith("css=") || locator.startsWith("jquery=")) {
            columns = selenium().getCssCount(locator + " tbody tr:nth-child(1) td").intValue();
        } return columns;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getFirstSelectedOption(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=1)
    public String getFirstSelectedOptionText(Object locator) {
        String selectedText=selenium().getSelectedLabel((String)locator);
        return selectedText;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getAllOptionsText(java.lang.Object)
     */
    @Override
    public List<String> getAllOptionsText(Object locator) {
        List<String> optionValues = new ArrayList<String>();
        String [] options=selenium().getSelectOptions((String)locator);
        for (String option:options) {
            optionValues.add(option);
        }
        return optionValues;
    }


}
