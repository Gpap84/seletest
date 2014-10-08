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
package DemoTest;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.annotations.DataSource;
import com.automation.seletest.core.services.annotations.SeleniumTest;
import com.automation.seletest.core.services.annotations.SeleniumTest.AssertionType;
import com.automation.seletest.core.services.annotations.SeleniumTest.DriverType;
import com.automation.seletest.core.spring.SeletestWebTestBase;
import com.automation.seletest.pagecomponents.pageObjects.GitHubSearchPage;
import com.automation.seletest.pagecomponents.pageObjects.GitHubSearchResultPage.GitHubSearchPageLocators;


@DataSource(filePath="./target/test-classes/DataSources/demoTest.properties")
@SeleniumTest
public class GitHubSearch extends SeletestWebTestBase{

    @Autowired
    GitHubSearchPage gitHub;

    @SeleniumTest(assertion=AssertionType.HARD, driver=DriverType.SELENIUM)
    @Test
    public void gitHubSearch(Map<String, String> map) throws InterruptedException, ExecutionException{
        gitHub.openPage().searchRepository(map.get("gitHubsearch"));
        SessionControl.verifyController().elementPresent(GitHubSearchPageLocators.TXT_RESULT_HEADER.getWithParams(map.get("gitHubExpectedResult"))).get();
    }

}
