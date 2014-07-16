/**
 *
 */
package com.automation.seletest.core.selenium.webAPI;


import java.util.HashMap;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Reporter;

import com.automation.seletest.core.selenium.session.LocalDriverConfiguration;
import com.automation.seletest.core.selenium.threads.SessionContext;
import com.automation.seletest.core.services.Constants;
import com.automation.seletest.core.spring.ApplicationContextProvider;

/**
 * Initialization class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
public class Initialization {

	public static Map<String, Object> getControllers(String mode) throws BeansException, Exception {
		Map<String, Object> controllers= new HashMap<>();

		if(mode.compareToIgnoreCase("web")==0){
			ActionsController<?> webcontroller = ApplicationContextProvider.getApplicationContext().getBean(WebDriverActionsController.class);

			webcontroller = new WebDriverInitialize().initialize(webcontroller);

			//put the objects for controllers to the Map
			controllers.put(Constants.WEB_ACTIONS_CONTROLLER.get(), webcontroller);

		} else {
			throw new UnsupportedDataTypeException("The mode for testing is not set with!!!");
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

			//Create Application Context for initializing driver based on specified @Profile
			AnnotationConfigApplicationContext app=new AnnotationConfigApplicationContext();
			app.getEnvironment().setActiveProfiles(new String[]{Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(Constants.BROWSERTYPE.get())});
			app.register(LocalDriverConfiguration.class);
			app.refresh();
			driver=(WebDriver) app.getBean("profileDriver");

            //Set objects per session
			wdActions.setDriver(driver);//set the driver object for this session
			wdActions.setJsExec((JavascriptExecutor)driver);//sets tthe Javascript executor
			SessionContext.getSession().setDriverContext(app);//set the new application context for WebDriver
			return control;
		}
	}


}
