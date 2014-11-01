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
import com.automation.seletest.core.services.FilesUtils;
import com.automation.seletest.core.services.annotations.Monitor;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * SeleniumController class..
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumControl")
@Slf4j
@SuppressWarnings("rawtypes")
public class SeleniumController<T extends DefaultSelenium> extends DriverBaseController<T>{

    /**The FileUtils*/
    @Autowired
    FilesUtils fileService;

    @Override
    public WebElement findElement(Object locator) {
        throw new UnsupportedOperationException("findElements(object locator) method is not supported for Selenium RC");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#click(java.lang.Object)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.CLICKABLE)
    @RetryFailure(retryCount=3)
    public SeleniumController click(Object locator) {
        selenium().click(defineLocator(locator));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#type(java.lang.Object, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.VISIBILITY)
    @RetryFailure(retryCount=3)
    public SeleniumController type(Object locator, String text) {
        selenium().typeKeys(defineLocator(locator), text);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#goToTargetHost(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public SeleniumController goToTargetHost(String url) {
        selenium().open(url);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#changeStyle(java.lang.Object, java.lang.String, java.lang.String)
     */
    @Override
    @WaitCondition(waitFor.VISIBILITY)
    public SeleniumController changeStyle(Object locator, String attribute, String attributevalue) {
        selenium().highlight(defineLocator(locator));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#takeScreenShot()
     */
    @Override
    @Monitor
    public SeleniumController takeScreenShot() throws IOException {
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
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#takeScreenShotOfElement(java.lang.Object)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.VISIBILITY)
    public SeleniumController takeScreenShotOfElement(Object locator) throws IOException {
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
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getText(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public String getText(Object locator) {
        return selenium().getText(defineLocator(locator));
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getTagName(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public String getTagName(Object locator) {
        return selenium().getAttribute(defineLocator(locator)+"@tagName");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getLocation(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public Point getLocation(Object locator) {
        int px =(Integer) selenium().getElementPositionLeft(defineLocator(locator));
        int py=(Integer) selenium().getElementPositionTop(defineLocator(locator));
        Point p=new Point(px, py);
        return p;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getElementDimensions(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public Dimension getElementDimensions(Object locator) {
        int height = (Integer) (selenium().getElementHeight(defineLocator(locator)));
        int width = (Integer) selenium().getElementWidth(defineLocator(locator));
        return new Dimension(width, height);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getPageSource()
     */
    @Override
    @RetryFailure(retryCount=3)
    public String getPageSource() {
        return selenium().getHtmlSource();
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
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#uploadFile(java.lang.Object, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public SeleniumController uploadFile(Object locator, String path) {
        selenium().attachFile(defineLocator(locator), path);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#executeJS(java.lang.String, java.lang.Object[])
     */
    @Override
    @RetryFailure(retryCount=3)
    public Object executeJS(String script, Object... args) {
        return selenium().getEval(script);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByValue(java.lang.String, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public SeleniumController selectByValue(String locator, String value) {
        selenium().select(defineLocator(locator),"value="+value);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#selectByVisibleText(java.lang.String, java.lang.String)
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public SeleniumController selectByVisibleText(String locator, String text) {
        selenium().select(defineLocator(locator),"label="+text);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookieByName(java.lang.String)
     */

    @Override
    @RetryFailure(retryCount=3)
    @Monitor
    public SeleniumController deleteCookieByName(String name) {
        selenium().deleteCookie(name, "");
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteCookie(org.openqa.selenium.Cookie)
     */
    @Deprecated
    @Override
    public SeleniumController deleteCookie(Cookie cookie) {
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#deleteAllCookies()
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public SeleniumController deleteAllCookies() {
        selenium().deleteAllVisibleCookies();
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#addCookie(org.openqa.selenium.Cookie)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public SeleniumController addCookie(Cookie cookie) {
        selenium().createCookie(cookie.getName(), cookie.getValue());
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getCookies()
     */
    @Override
    public Set<Cookie> getCookies() {
        selenium().getCookie();
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#implicitlyWait(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public SeleniumController implicitlyWait(long timeout, TimeUnit timeunit) {
        // TODO Auto-generated method stub
        //HttpCommandProcessor command = new  HttpCommandProcessor();
        //command.doCommand("setImplicitWaitLocator", new String[] {"10000",});
        return this;

    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#pageLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    @RetryFailure(retryCount=3)
    public SeleniumController pageLoadTimeout(long timeout, TimeUnit timeunit) {
        selenium().setTimeout(String.valueOf(timeout));
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#scriptLoadTimeout(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public SeleniumController scriptLoadTimeout(long timeout, TimeUnit timeunit) {
        throw new UnsupportedOperationException("Method scriptLoadTimeout(long timeout, TimeUnit timeunit) is not supported with Selenium RC");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowPosition(org.openqa.selenium.Point)
     */
    @Override
    @RetryFailure(retryCount=3)
    public SeleniumController setWindowPosition(Point point) {
        int x=point.getX();
        int y=point.getY();
        selenium().getEval("window.resizeTo(" + x + ", " + y + "); window.moveTo(0,0);");
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#setWindowDimension(org.openqa.selenium.Dimension)
     */
    @Override
    public SeleniumController setWindowDimension(Dimension dimension) {
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#getWindowPosition()
     */
    @Override
    @RetryFailure(retryCount=3)
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
        int width = Integer.parseInt(selenium().getEval("screen.width"));
        int height = Integer.parseInt(selenium().getEval("screen.height"));
        Dimension dimension=new Dimension(width, height);
        return dimension;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.OptionsController#maximizeWindow()
     */
    @Override
    @RetryFailure(retryCount=3)
    public SeleniumController maximizeWindow() {
       selenium().windowMaximize();
       return this;
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
    @RetryFailure(retryCount=3)
    public SeleniumController switchToLatestWindow() {
        String[] windows = selenium().getAllWindowIds();
        String[] windowsTitles = selenium().getAllWindowTitles();
        selenium().selectWindow(windows[windows.length - 1]);
        log.info("Window with title: " + windowsTitles[windowsTitles.length - 1] + " selected");
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#getNumberOfOpenedWindows()
     */
    @Override
    @RetryFailure(retryCount=3)
    public int getNumberOfOpenedWindows() {
        String[] windows=selenium().getAllWindowIds();
        return windows.length;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#acceptAlert()
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.ALERT)
    @RetryFailure(retryCount=3)
    public SeleniumController acceptAlert() {
        selenium().getEval("window.confirm = function(msg) { return true; }");
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#dismissAlert()
     */
    @Override
    @Monitor
    @WaitCondition(waitFor.ALERT)
    @RetryFailure(retryCount=3)
    public SeleniumController dismissAlert() {
        selenium().getEval("window.confirm = function(msg) { return false; }");
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#quit(com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession)
     */
    @Override
    @Monitor
    public SeleniumController quit(CloseSession type) {
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
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#switchToFrame(java.lang.String)
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public SeleniumController switchToFrame(String frameId) {
        selenium().selectFrame(frameId);
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goBack()
     */
    @Override
    @Monitor
    @RetryFailure(retryCount=3)
    public SeleniumController goBack() {
        selenium().goBack();
        waitController().waitForPageLoaded();
        return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goForward()
     */
    @Override
    public SeleniumController goForward() {
        throw new UnsupportedOperationException("Method goForward() is not supported with Selenium RC");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#findChildElements(java.lang.Object, java.lang.String)
     */
    @Override
    public List<WebElement> findChildElements(Object parent, String child) {
        throw new UnsupportedOperationException("Method findChildElements(Object parent, String child) is not supported with Selenium RC");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#rowsTable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public int getRowsTable(Object tableLocator) {
        int rows = 0;
        String locator=defineLocator(tableLocator);
        if (locator.startsWith("xpath=") || locator.startsWith("//")) {
            rows = selenium().getXpathCount(locator + "//tbody//tr").intValue();
        } else if (locator.startsWith("css=")) {
            rows = selenium().getCssCount(locator + " tbody tr").intValue();
        } return rows;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#columnsTable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public int getColumnsTable(Object tableLocator) {
        int columns = 0;
        String locator=defineLocator(tableLocator);
        if (locator.startsWith("xpath=") || locator.startsWith("//")) {
            columns = selenium().getXpathCount(locator + "//tbody//tr[1]/td").intValue();
        } else if (locator.startsWith("css=")) {
            columns = selenium().getCssCount(locator + " tbody tr:nth-child(1) td").intValue();
        } return columns;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getFirstSelectedOption(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public String getFirstSelectedOptionText(Object locator) {
        String selectedText=selenium().getSelectedLabel(defineLocator(locator));
        return selectedText;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getAllOptionsText(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    public List<String> getAllOptionsText(Object locator) {
        List<String> optionValues = new ArrayList<String>();
        String [] options=selenium().getSelectOptions(defineLocator(locator));
        for (String option:options) {
            optionValues.add(option);
        } return optionValues;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#clearSelectedOptionByText(java.lang.Object, java.lang.String)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    @Monitor
    public SeleniumController<T> clearSelectedOptionByText(Object locator, String text) {
        if(selenium().isSomethingSelected(defineLocator(locator))){
            selenium().select(defineLocator(locator),"label="+text);
        } return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#clearSelectedOption(java.lang.Object, java.lang.String)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    @RetryFailure(retryCount=3)
    @Monitor
    public SeleniumController<T> clearSelectedOption(Object locator, String value) {
        if(selenium().isSomethingSelected(defineLocator(locator))){
            selenium().select(defineLocator(locator),"value="+value);
        } return this;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#isFieldEditable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    public boolean isFieldEditable(Object locator) {
        return selenium().isEditable(defineLocator(locator));
    }


    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#isFieldNotEditable(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.PRESENCE)
    public boolean isFieldNotEditable(Object locator) {
        return !selenium().isEditable(defineLocator(locator));
    }


    /**
     * Define locator
     * @param locator
     * @return String locator
     */
    private String defineLocator(Object locator){
        if(((String)locator).startsWith("jquery=")){
            return ((String)locator).replace("jquery=", "css=");
        } else{
            return (String)locator;
        }
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#getCookieNamed(java.lang.String)
     */
    @Override
    public Cookie getCookieNamed(String name) {
        return new Cookie(name,selenium().getCookieByName(name));
    }


    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#elementsMatching(java.lang.Object)
     */
    @Override
    public int elementsMatching(String locator) {
        throw new UnsupportedOperationException("Method elementsMatching(Object locator) is not supported with Selenium RC");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.MainController#waitForAngularFinish()
     */
    @Override
    public SeleniumController<T> waitForAngularFinish() {
        throw new UnsupportedOperationException("Method waitForAngularFinish() is not supported with Selenium RC");
    }



}
