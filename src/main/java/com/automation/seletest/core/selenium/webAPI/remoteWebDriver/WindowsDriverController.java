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
package com.automation.seletest.core.selenium.webAPI.remoteWebDriver;

import java.util.Iterator;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.DriverBaseController;
import com.automation.seletest.core.selenium.webAPI.interfaces.ElementController.CloseSession;
import com.automation.seletest.core.selenium.webAPI.interfaces.WindowsController;
import com.automation.seletest.core.services.factories.StrategyFactory;

/**
 * WindowsWDController class.
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("webDriverWindows")
public class WindowsDriverController<T extends RemoteWebDriver> extends DriverBaseController<T> implements WindowsController{

    @Autowired
    StrategyFactory<?> factoryStrategy;

    @Override
    public void switchToLatestWindow() {
        Iterator<String> iterator = webDriver().getWindowHandles().iterator();
        String lastWindow = null;
        while (iterator.hasNext()) {
            lastWindow = iterator.next();
        }
        webDriver().switchTo().window(lastWindow);
    }

    @Override
    public void acceptAlert() {
        factoryStrategy.getWaitStrategy(getWait()).waitForAlert().accept();
    }


    @Override
    public void dismissAlert() {
        factoryStrategy.getWaitStrategy(getWait()).waitForAlert().dismiss();
    }

    @Override
    public int getNumberOfOpenedWindows() {
        return webDriver().getWindowHandles().size();
    }

    @Override
    public void quit(CloseSession type) {
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
    }

}
