/**
 * 
 */
package com.automation.sele.web.selenium.threads;

import java.util.Map;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.target.ThreadLocalTargetSource;

import com.automation.sele.web.selenium.webAPI.WebActionsController;
import com.automation.sele.web.services.Constants;
import com.automation.sele.web.spring.ApplicationContextProvider;


/**
 * SessionContext
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
public class SessionContext {


	/**
	 * Get the thread in parallel execution from a target Source
	 * @return
	 */
	public static SessionProperties getSession(){
		return (SessionProperties) innerContext("threadLocalTargetSource").getTarget();
	}

	/**
	 * Get the thread in non parallel execution from factory bean
	 * @return
	 */
	public static SessionProperties getThreadAsync(){
		return (SessionProperties) ApplicationContextProvider.getApplicationContext().getBean("engineThread");
	}

	/**
	 * Return the ThreadLocalTargetSource
	 * @param targetBean
	 * @return
	 */
	protected static ThreadLocalTargetSource innerContext(String targetBean) {
		return (ThreadLocalTargetSource) ApplicationContextProvider.getApplicationContext().getBean(targetBean);
	}

	/**
	 * Destroy instances of the thread
	 * @throws Exception
	 */
	public static void destroyThread() throws Exception{
		threadStack.removeElement(getSession());//remove element from thread stack
		log.debug("*********************Object removed from thread stack, new size is: {}*****************************",threadStack.size());
		getSession().cleanSession();
		innerContext("threadLocalTargetSource").releaseTarget(getSession());
		innerContext("threadLocalTargetSource").destroy();
	}

	public static void setSessionProperties(Map<String, Object> sessionObjects) throws Exception {
		SessionProperties session = getSession();
		session.actionscontroller=(WebActionsController<?>) sessionObjects.get(Constants.WEB_ACTIONS_CONTROLLER.get());
		threadStack.push(session);//push instanse of Session to stack
		getSession().setThread(Thread.currentThread());//set the current thread to threadlocal variable
		log.info("Session ready, driver is now set, type is {}", ApplicationContextProvider.getApplicationContext().containsBean("profileDriver") ? "Webdriver" : "AppiumDriver");
		Thread.currentThread().setName("CoreFramework ["+(ApplicationContextProvider.getApplicationContext().containsBean("profileDriver") ? "Webdriver" : "AppiumDriver")+"] - context Active "+System.currentTimeMillis()%2048);
	}

	/**Clean all active threads stored in stack
	 * 
	 */
	public static void cleanSessionsFromStack() {
		for(int i=0; i < threadStack.size();i++){
			SessionContext.stopSession(i);
		}
	}
	/**
	 * Clean specific thread from a Stack with threads
	 * @param index
	 * @throws Exception
	 */
	public static void stopSession(int index) {
		threadStack.get(index).cleanSession();
		innerContext("threadLocalTargetSource").destroy();
		threadStack.removeElement(threadStack.get(index));
		log.debug("*********************Object removed from thread stack, new size is: {}*****************************",threadStack.size());
	}

	

	/**Stack for storing instances of thread objects*/
	@Getter @Setter
	public static Stack<SessionProperties> threadStack = new Stack<SessionProperties>();

}
