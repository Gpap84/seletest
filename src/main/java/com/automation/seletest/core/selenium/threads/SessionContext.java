/**
 *
 */
package com.automation.seletest.core.selenium.threads;

import java.util.Map;
import java.util.Stack;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.target.ThreadLocalTargetSource;

import com.automation.seletest.core.selenium.webAPI.ActionsController;
import com.automation.seletest.core.services.CoreProperties;
import com.automation.seletest.core.spring.ApplicationContextProvider;


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
     * Get the session in non parallel execution from factory bean
     * @return
     */
    public static SessionProperties getSessionOnTest(){
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
    public static void cleanSession() throws Exception{
        threadStack.removeElement(getSession());//remove element from thread stack
        log.debug("*********************Object removed from thread stack, new size is: {}*****************************",threadStack.size());
        getSession().cleanSession();
        innerContext("threadLocalTargetSource").releaseTarget(getSession());
        innerContext("threadLocalTargetSource").destroy();
    }

    /**
     * Set objects and properties per session
     * @param sessionObjects
     * @throws Exception
     */
    public static void setSessionProperties(Map<String, Object> sessionObjects){
        SessionProperties session = getSession();
        session.actionscontroller=(ActionsController<?>) sessionObjects.get(CoreProperties.WEB_ACTIONS_CONTROLLER.get());
        threadStack.push(session);//push instanse of Session to stack
        getSession().setThread(Thread.currentThread());//set the current thread to threadlocal variable
        log.info("Session started with type of driver: {}", getSession().getDriverContext().containsBean("profileDriver") ? "Webdriver" : "AppiumDriver");
        Thread.currentThread().setName("SeletestFramework ["+(getSession().getDriverContext().containsBean("profileDriver") ? "Webdriver" : "AppiumDriver")+"] - session Active "+System.currentTimeMillis()%2048);
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
