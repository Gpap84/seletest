package com.automation.sele.web.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * With this class we set application context in a static variable
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		// Assign the ApplicationContext into a static variable
		this.applicationContext = applicationContext;
	}
}