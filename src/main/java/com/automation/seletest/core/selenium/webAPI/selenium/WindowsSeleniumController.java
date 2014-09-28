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

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession;
import com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController;
import com.automation.seletest.core.services.annotations.WaitCondition;
import com.automation.seletest.core.services.annotations.WaitCondition.waitFor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * WindowsSeleniumController class
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("seleniumWindows")
@Slf4j
public class WindowsSeleniumController <T extends DefaultSelenium> extends DriverBaseController<T> implements WindowsController{

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#switchToLatestWindow()
     */
    @Override
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
    public int getNumberOfOpenedWindows() {
        String[] windows=selenium().getAllWindowIds();
        return windows.length;
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#acceptAlert()
     */
    @Override
    @WaitCondition(waitFor.ALERT)
    public void acceptAlert() {
        selenium().getEval("window.confirm = function(msg) { return true; }");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#dismissAlert()
     */
    @Override
    @WaitCondition(waitFor.ALERT)
    public void dismissAlert() {
        selenium().getEval("window.confirm = function(msg) { return false; }");
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#quit(com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession)
     */
    @Override
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
    public void switchToFrame(String frameId) {
        selenium().selectFrame(frameId);
    }

    /* (non-Javadoc)
     * @see com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController#goBack()
     */
    @Override
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

}
