
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
package com.automation.seletest.core.selenium.threads;

import io.appium.java_client.AppiumDriver;

import java.util.Stack;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.testng.Reporter;

import com.automation.seletest.core.spring.ApplicationContextProvider;


/**
 * SessionContext
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class SessionContext {

	private final static String session="session";

	/**
	 * Get the thread in parallel execution from a target Source
	 * @return SessionProperties instance
	 */
	public static SessionProperties session(){
		return (SessionProperties) innerContext(ThreadLocalTargetSource.class).getTarget();
	}

	/**
	 * Session object
	 * @return SessionProperties instance
	 */
	public static SessionProperties getSession(){
		return (SessionProperties) Reporter.getCurrentTestResult().getAttribute(session);
	}

	/**
	 * Return the ThreadLocalTargetSource
	 * @param targetBean Class for target bean to be ThreadLocal
	 * @return ThreadLocalTargetSource instance
	 */
	protected static ThreadLocalTargetSource innerContext(Class<?> targetBean) {
		return (ThreadLocalTargetSource) ApplicationContextProvider.getApplicationContext().getBean(targetBean);
	}

	/**
	 * Destroy instances of the thread
	 * @throws Exception
	 */
	public static void cleanSession() throws Exception{
		threadStack.removeElement(getSession());//remove element from thread stack
		getSession().cleanSession();
		innerContext(ThreadLocalTargetSource.class).destroy();
		log.debug("*********************Object removed from thread stack, new size is: {}*****************************", threadStack.size());
	}

	/**
	 * Log thread instance
	 */
	public static void setSessionProperties(){
		threadStack.push(session());
		log.debug("{} stored in {}",session(),threadStack);
		String driver="";
		if(session().getWebDriver() instanceof RemoteWebDriver && !(session().getWebDriver() instanceof AppiumDriver)) {
			driver="Web Test: "+session().getWebDriver().toString().split(":")[0];
		} else if(session().getWebDriver() instanceof AppiumDriver) {
			driver="Mobile Test: "+session().getWebDriver().toString().split(":")[0];
		}
		log.info("Session started with type of driver: {}", driver);
		Thread.currentThread().setName("SeletestFramework ["+driver+"] - session Active "+System.currentTimeMillis()%2048);
	}

	/**Clean all active threads stored in stack
	 * @throws Exception
	 *
	 */
	public static void cleanSessionsFromStack() throws Exception {
		while(!threadStack.isEmpty()){
			SessionContext.stopSession(0);
		}
	}
	/**
	 * Clean specific thread from a Stack with threads
	 * @param index Index in stack
	 * @throws Exception
	 */
	public static void stopSession(int index) throws Exception {
		threadStack.get(index).cleanSession();
		innerContext(ThreadLocalTargetSource.class).destroy();
		threadStack.removeElement(threadStack.get(index));
		log.debug("*********************Object removed from thread stack, new size is: {}*****************************", threadStack.size());
	}

	/**Stack for storing instances of thread objects*/
	@Getter @Setter
	public static Stack<SessionProperties> threadStack = new Stack<>();

}
