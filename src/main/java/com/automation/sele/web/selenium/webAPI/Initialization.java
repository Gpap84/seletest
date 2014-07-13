/**
 * 
 */
package com.automation.sele.web.selenium.webAPI;


import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.BeansException;
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
			WebActionsController<?> webcontroller = (WebActionsController<?>) ApplicationContextProvider.getApplicationContext().getBean(WebDriverActionsController.class);

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
		
		public WebActionsController<?> initialize(WebActionsController<?> control){

			//Get the beans 
			WebDriverActionsController wdActions = (WebDriverActionsController) control;
			WebDriver driver = null;

			//Initialize Driver based on browser parameter
			driver=(WebDriver) ApplicationContextProvider.getApplicationContext().getBean("profileDriver");
			wdActions.setDriver(driver);//set the driver obect for this session

			return control;
		}
	}


}
