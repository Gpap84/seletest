package com.automation.seletest.core.listeners;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

import com.automation.seletest.core.listeners.Event.WebInitEvent;
import com.automation.seletest.core.selenium.configuration.LocalDriverConfiguration;
import com.automation.seletest.core.selenium.configuration.RemoteDriverConfiguration;
import com.automation.seletest.core.selenium.configuration.WebDriverConfiguration;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.selenium.webAPI.WebDriverActionsController;
import com.automation.seletest.core.services.CoreProperties;
import com.automation.seletest.core.services.Performance;
import com.automation.seletest.core.spring.ApplicationContextProvider;

/**
 * ApplicationListener for event handling
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Slf4j
public class EventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        if (event instanceof WebInitEvent) {
        	try {
                new Initialize().initializeWeb(event);
            } catch (Exception e) {
                log.error("Error "+e.getMessage());
            }
        }
    }

    static class Initialize {

        /**
         * Initialize web session
         * @param event
         * @throws Exception
         */
    	public void initializeWeb(ApplicationEvent event) throws Exception{
    	Map<String, Object> controllers= new HashMap<>();

        WebDriverActionsController wdActions = ApplicationContextProvider.getApplicationContext().getBean(WebDriverActionsController.class);

        //Create Application Context for initializing driver based on specified @Profile
        AnnotationConfigApplicationContext app=new AnnotationConfigApplicationContext();
        app.getEnvironment().setActiveProfiles(new String[]{
                Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(CoreProperties.PROFILEDRIVER.get())});

        //register Configuration classes
        app.register(
                LocalDriverConfiguration.class,
                WebDriverConfiguration.class,
                RemoteDriverConfiguration.class);

        //start Container for bean initialization
        app.refresh();

        DesiredCapabilities cap = ApplicationContextProvider.getApplicationContext().getBean(DesiredCapabilities.class);

        //If performance is enabled
        if(((WebInitEvent) event).isPerformance()){
            Performance perf = ApplicationContextProvider.getApplicationContext().getBean(Performance.class);
            ProxyServer server=perf.proxyServer(new Random().nextInt(5000));
            perf.newHar("Har at: "+event.getTimestamp());
            cap.setCapability(CapabilityType.PROXY, perf.proxy(server));
            SessionContext.getSession().setPerformance(perf);
        }

        //start a driver object with capabilities
        WebDriver driver=(WebDriver) app.getBean(CoreProperties.PROFILEDRIVER.get(), new Object[]{cap});

        //Set objects per session
        wdActions.setDriver(driver);//set the driver object for this session
        wdActions.setJsExec((JavascriptExecutor)driver);//sets tthe Javascript executor
        SessionContext.getSession().setDriverContext(app);//set the new application context for WebDriver

        //get the address of the app under test
        wdActions.getTargetHost(((WebInitEvent) event).getHostUrl());

        //register all objects - interfaces and set them in sessionProperties
        controllers.put(CoreProperties.WEB_ACTIONS_CONTROLLER.get(), wdActions);
        SessionContext.setSessionProperties(controllers);
    	}
    }

}