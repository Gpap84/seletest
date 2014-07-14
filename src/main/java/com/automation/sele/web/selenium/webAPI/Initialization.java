/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;


import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Reporter;

import com.automation.sele.web.selenium.session.LocalDriverConfiguration;
import com.automation.sele.web.selenium.threads.SessionContext;
import com.automation.sele.web.services.Constants;
import com.automation.sele.web.spring.ApplicationContextProvider;

/**
 * Initialization class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class Initialization {

	public static Map<String, Object> getControllers(String mode) throws BeansException, Exception {
		Map<String, Object> controllers= new HashMap<>();

		if(mode.compareToIgnoreCase("web")==0){
			ActionsController<?> webcontroller = (ActionsController<?>) ApplicationContextProvider.getApplicationContext().getBean(WebDriverActionsController.class);

			webcontroller = new WebDriverInitialize().initialize(webcontroller);

			//put the objects for controllers to the Map
			controllers.put(Constants.WEB_ACTIONS_CONTROLLER.get(), webcontroller);

		}
		return controllers;
	}

	/**
	 * Initialize class.
	 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
	 *
	 */
	static class WebDriverInitialize 
	{
		
		public ActionsController<?> initialize(ActionsController<?> control){

			//Get the beans 
			WebDriverActionsController<?> wdActions = (WebDriverActionsController<?>) control;
			WebDriver driver = null;
			
			//Create App Context for initializing driver 
			AnnotationConfigApplicationContext app=new AnnotationConfigApplicationContext();
			app.getEnvironment().setActiveProfiles(new String[]{Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(Constants.BROWSERTYPE.get())});
			app.register(LocalDriverConfiguration.class);
			app.refresh();
			driver=(WebDriver) app.getBean("profileDriver");
			wdActions.setDriver(driver);//set the driver object for this session
            SessionContext.getSession().setDriverContext(app);//set the new application context for WebDriver
			return control;
		}
	}


}
