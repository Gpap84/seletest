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
package com.automation.seletest.core.services.actions;



import java.util.List;

import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.threads.ThreadUtils;
import com.automation.seletest.core.spring.ApplicationContextProvider;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.webAPI.elements.Locators;

/**
 * ExpectedWaitStrategy class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component("webDriverWait")
@Slf4j
public class ExpectedWaitStrategy implements WaitFor{

	@Autowired
	ThreadUtils thread;

	/**Component name for WebDriverWait*/
	private final String webDriverWait="webdriverwait";


	/**
	 * Returns a new WebDriverWait instance
	 * @return WebDriverWait object
	 */
	private WebDriverWait wfExpected(){
		return (WebDriverWait) ApplicationContextProvider.getApplicationContext().getBean(webDriverWait, SessionContext.getSession().getWebDriver(),SessionContext.getSession().getWaitUntil());
	}



	@Cacheable("wait")
	@Override
	public WebElement waitForElementPresence(final String locator) {
		log.info("Wait For executed for locator: "+locator);
		return wfExpected().until(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).setLocator(locator)));
	}

	@Cacheable("wait")
	@Override
	public WebElement waitForElementVisibility(final Object locator){
			if (locator instanceof String) {
				return wfExpected().until(ExpectedConditions.visibilityOfElementLocated(Locators.findByLocator((String) locator).setLocator((String) locator)));
			} else if (locator instanceof WebElement) {
				return wfExpected().until(ExpectedConditions.visibilityOf((WebElement) locator));
			} else {
				throw new UnsupportedOperationException("The defined locator: " + locator + " is not supported!!!");
			}
	}

	@Cacheable("wait")
	@Override
	public WebElement waitForElementToBeClickable(final Object locator) {
		if(locator instanceof String){
			return wfExpected().until(ExpectedConditions.elementToBeClickable(Locators.findByLocator((String)locator).setLocator((String)locator)));
		}
		else if(locator instanceof WebElement){
			return wfExpected().until(ExpectedConditions.elementToBeClickable((WebElement)locator));
		}
		else{
			throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
		}
	}

	@Override
	public Alert waitForAlert() {
		return wfExpected().until(ExpectedConditions.alertIsPresent());
	}

	@Cacheable("wait")
	@Override
	public boolean waitForElementInvisibility(final String locator) {
		return wfExpected().until(ExpectedConditions.invisibilityOfElementLocated(Locators.findByLocator(locator).setLocator(locator)));
	}

	@Cacheable("wait")
	@Override
	public boolean waitForTextPresentinElement(final Object locator,final String text) {
		if(locator instanceof String){
			return wfExpected().until(ExpectedConditions.textToBePresentInElementLocated(Locators.findByLocator((String)locator).setLocator((String)locator),text));
		}
		else if(locator instanceof WebElement){
			return wfExpected().until(ExpectedConditions.textToBePresentInElement((WebElement)locator,text));
		}
		else{
			throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
		}
	}


	@Cacheable("wait")
	@Override
	public boolean waitForTextPresentinValue(final Object locator,final String text) {
		if(locator instanceof String){
			return wfExpected().until(ExpectedConditions.textToBePresentInElementValue(Locators.findByLocator((String)locator).setLocator((String)locator),text));
		}
		else if(locator instanceof WebElement){
			return wfExpected().until(ExpectedConditions.textToBePresentInElementValue((WebElement)locator,text));
		}
		else{
			throw new UnsupportedOperationException("The defined locator: "+locator+" is not supported!!!");
		}
	}

	@Cacheable("wait")
	@Override
	public List<WebElement> waitForPresenceofAllElements(final String locator) {
		return wfExpected().until(ExpectedConditions.presenceOfAllElementsLocatedBy(Locators.findByLocator(locator).setLocator(locator)));

	}

	@Cacheable("wait")
	@Override
	public List<WebElement> waitForVisibilityofAllElements(final String locator) {
		return wfExpected().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Locators.findByLocator(locator).setLocator(locator)));
	}

	@Override
	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> pageLoadedExpectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};

		wfExpected().until(pageLoadedExpectation);
	}

	@Cacheable("wait")
	@Override
	public void waitForAjaxCallCompleted(final long timeout) {
		ExpectedCondition<Boolean> ajaxCallExpectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				final long startTime = System.currentTimeMillis();
				final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

				while ((startTime + timeout) <= System.currentTimeMillis()) {

					final Boolean scriptResult = (Boolean) javascriptExecutor.executeScript("return jQuery.active == 0");

					if (scriptResult) {
						return true;
					}

					thread.sleep(timeout);
				}
				return false;
			}
		};

		wfExpected().until(ajaxCallExpectation);
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementNotPresent(java.lang.String)
	 */
	@Cacheable("wait")
	@Override
	public boolean waitForElementNotPresent(final String locator) {
		return wfExpected().until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(Locators.findByLocator(locator).setLocator(locator))));
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementInvisble(java.lang.String)
	 */
	@Cacheable("wait")
	@Override
	public boolean waitForElementInvisible(final String locator) {
		return wfExpected().until(ExpectedConditions.invisibilityOfElementLocated(Locators.findByLocator(locator).setLocator(locator)));
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.WaitFor#waitForPageTitle(java.lang.String)
	 */
	@Cacheable("wait")
	@Override
	public boolean waitForPageTitle(final String title) {
		return wfExpected().until(ExpectedConditions.titleContains(title));
	}

	/* (non-Javadoc)
	 * @see com.automation.seletest.core.services.actions.WaitFor#waitForElementNotClickable(java.lang.Object)
	 */
	@Cacheable("wait")
	@Override
	public boolean waitForElementNotClickable(final Object locator) {
		if(locator instanceof String){
			return wfExpected().until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(Locators.findByLocator((String)locator).setLocator((String)locator))));
		}
		else if(locator instanceof WebElement){
			return wfExpected().until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable((WebElement)locator)));
		}
		return false;
	}



}
