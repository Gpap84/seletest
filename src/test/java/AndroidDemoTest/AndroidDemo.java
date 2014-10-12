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
package AndroidDemoTest;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.core.testNG.PreConfiguration;
import com.automation.seletest.pagecomponents.pageObjects.Android.CalculatorPage;
import com.automation.seletest.pagecomponents.pageObjects.Android.CalculatorPage.CalculatorLocators;

/**
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class AndroidDemo extends SeletestWebTestBase{

    @Autowired
    CalculatorPage calculate;

        @SeleniumTest
        @Test
        @PreConfiguration(classReference = CalculatorPage.class, method = "clearResult")
        public void CalculateMultiplication() throws InterruptedException, ExecutionException{
            calculate.calculateMultiplication(CalculatorLocators.BTN_1.get(), CalculatorLocators.BTN_3.get());
            SessionControl.verifyController().textPresentinElement(CalculatorLocators.IPF_RESULT.get(), "3").get();
        }

}
