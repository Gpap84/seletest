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

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.ElementController;
import com.automation.seletest.core.services.FilesUtils;
import com.automation.seletest.core.services.annotations.RetryFailure;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;
import com.automation.seletest.core.services.factories.StrategyFactory;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumElement")
public class ElementSeleniumController<T extends DefaultSelenium> extends DriverBaseController<T> implements ElementController{

    @Autowired
    FilesUtils fileService;

    @Autowired
    StrategyFactory<?> factoryStrategy;

    @Deprecated
    @Override
    public WebElement findElement(Object locator) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#click(java.lang.Object)
     */
    @Override
    @WaitCondition(waitFor.CLICKABLE)
    @RetryFailure(retryCount=1)
    public void click(Object locator) {
        selenium().click((String)locator);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#type(java.lang.Object, java.lang.String)
     */
    @Override
    @WaitCondition(waitFor.VISIBILITY)
    @RetryFailure(retryCount=1)
    public void type(Object locator, String text) {
        selenium().typeKeys((String)locator, text);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#goToTargetHost(java.lang.String)
     */
    @Override
    public void goToTargetHost(String url) {
        selenium().open(url);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#changeStyle(java.lang.Object, java.lang.String, java.lang.String)
     */
    @Override
    public void changeStyle(Object locator, String attribute,
            String attributevalue) {
        selenium().highlight((String)locator);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#takeScreenShot()
     */
    @Override
    @RetryFailure(retryCount=1)
    public void takeScreenShot() throws IOException {
        String base64Screenshot = selenium().captureEntirePageScreenshotToString("");
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
    @WaitCondition(waitFor.VISIBILITY)
    @RetryFailure(retryCount=1)
    public void takeScreenShotOfElement(Object locator) throws IOException {
        String base64Screenshot = selenium().captureEntirePageScreenshotToString("");
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
    public Dimension getElementDimensions(Object locator) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#getPageSource()
     */
    @Override
    public String getPageSource() {
        return selenium().getHtmlSource();
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.ElementController#isWebElementPresent(java.lang.String)
     */
    @Override
    public boolean isWebElementPresent(String locator) {
        factoryStrategy.getWaitStrategy(getWait()).waitForElementPresence(locator);
        return true;
    }

    /* (non-Javadoc)
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
        factoryStrategy.getWaitStrategy(getWait()).waitForElementVisibility(locator);
        return true;
    }

}
