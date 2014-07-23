package com.automation.seletest.core.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Super class with common functions
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
public class SuperAspect {

    /**Arguments of an executed method*/
    public String arguments(ProceedingJoinPoint proceedPoint){
        StringBuilder arguments = new StringBuilder();
        for(int i=0; i < proceedPoint.getArgs().length ;i++ ){
            MethodSignature sig = (MethodSignature)proceedPoint.getSignature();
            arguments.append(""+sig.getParameterNames()[i].toString()+":"+proceedPoint.getArgs()[i].toString().toUpperCase()+"--*--");
        } if(arguments.toString().isEmpty()){
            return "NONE";
        } else{
            return arguments.toString().trim();
        }
    }
}
