package com.automation.seletest.core.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automation.seletest.core.selenium.configuration.SessionControl;
import com.automation.seletest.core.services.CoreProperties;
import com.automation.seletest.core.services.Logging;

/**
 * Error Handling Aspect
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Aspect
@Component
public class ExceptionHandlingAspect extends SuperAspect{

    @Autowired
    Logging log;


    /**
     * Around advice with custom annotation for retrying execution of JoinPoint
     * @param pjp
     * @param retry
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.*(..)) && @annotation(retry)")
    public Object retry(ProceedingJoinPoint pjp, RetryFailure retry) throws Throwable
    {
        Object returnValue = null;
        for (int attemptCount = 1; attemptCount <= (1+retry.retryCount()); attemptCount++) {
            try {
                returnValue = pjp.proceed();
                log.info("Command: "+pjp.getSignature().getName()+" executed with arguments: "+arguments(pjp)+"!!!");
                SessionControl.actionsController().changeStyle("backgroudColor", (String) (pjp).getArgs()[0], CoreProperties.ACTION_COLOR.get());
                break;
            } catch (Exception ex) {
                handleRetryException(pjp, ex, attemptCount, retry);
            }
        }
        return returnValue;
    }


    /**
     * Around advice for takeScreenshot functions
     * @param pjp
     * @param retry
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.automation.seletest.core.selenium.webAPI.ActionsController.takeScreenShot*(..))")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable
    {
        Object returnValue = null;
        try {
            returnValue = pjp.proceed();
        } catch (Exception ex) {
            log.error(String.format("%s: Failed with exception '%s'",
                    pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                    ex.getMessage().split("Build")[0].trim()));
        }

        return returnValue;
    }

    /**
     * Method for handling exceptions....
     * @param pjp
     * @param ex
     * @param attemptCount
     * @param retry
     * @throws Throwable
     */
    private void handleRetryException(ProceedingJoinPoint pjp, Throwable ex,
            int attemptCount, RetryFailure retry) throws Throwable
    {
        if (ex instanceof TimeoutException) {
            log.error("Element not found in Screen with exception: "+ex.getMessage().split("Build")[0].trim());
            throw ex;
        } if (attemptCount == 1 + retry.retryCount()) {
            throw new RuntimeException(retry.message()+" for method: "+pjp.getKind(), ex);
        } else {
            log.error(String.format("%s: Attempt %d of %d failed with exception '%s'. Will retry immediately. %s",
                    pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
                    attemptCount,
                    retry.retryCount(),
                    ex.getClass().getCanonicalName(),
                    ex.getMessage().split("Build info")[0].trim()));
            Thread.sleep(retry.sleepMillis());
        }
    }


}
