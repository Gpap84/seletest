package com.automation.sele.web.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * With this class we set application context in a static variable
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    //gets the ApplicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    @Override
    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        // Assign the ApplicationContext into a static variable
        this.applicationContext = applicationContext;
    }

    //gets the ConfigurableApplicationContext
    public static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return (ConfigurableApplicationContext)applicationContext;
    }
}