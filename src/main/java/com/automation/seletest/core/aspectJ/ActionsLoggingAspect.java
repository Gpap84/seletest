package com.automation.seletest.core.aspectJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.session.SessionControl;
import com.automation.seletest.core.services.Constants;
import com.automation.seletest.core.services.Logging;


@Aspect
@Component
public class ActionsLoggingAspect {
	
	private Logging log;

    @Autowired
    public ActionsLoggingAspect(Logging log){
    	super();
    	this.log=log;
    }  
   
   
    /************************************
     ****************Pointcuts***********
     ************************************
     */
    @Pointcut("execution(* com.automation.sele.web.selenium.webAPI.ActionsController.click*(..))") // expression
    private void clickController() {}//expression pointcut for function  returning T type and starting with click...
    
    @Pointcut("execution(* com.automation.sele.web.selenium.webAPI.ActionsController.enter*(..))") // expression
    private void enterController() {}//expression pointcut for function  returning T type and starting with enter...
    
    @After("clickController() || enterController()")
    public void highlight(final JoinPoint joinPoint){
        log.info("The following actions command executed: "+joinPoint.getSignature().getName()+" with arguments: "+arguments((ProceedingJoinPoint)joinPoint)+"!!!");
    	SessionControl.actionsController().changeStyle("backgroudColor", (String) ((ProceedingJoinPoint)joinPoint).getArgs()[0], Constants.ACTION_COLOR.get());
    }
    
    /**Arguments of an executed method*/
    private String arguments(ProceedingJoinPoint proceedPoint){
        StringBuilder arguments = new StringBuilder();
        for(Object o: proceedPoint.getArgs()){
            arguments.append("'''"+o.toString()+"'''--");
        }
        if(arguments.toString().isEmpty()){
            return "NONE";
        }
        else{
        return arguments.toString().trim();}
    }

}
