package com.automation.sele.web.aspectJ;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Error Handling Aspect
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Slf4j
@Aspect
@Component
public class ErrorHandlingAspect{

	/**
	 * Around advice with custom annotation for retrying execution of JoinPoint
	 * @param pjp
	 * @param retry
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.automation.sele.web.selenium.webAPI.WebDriverActionsController.*(..)) && @annotation(retry)")
	public Object retry(ProceedingJoinPoint pjp, RetryIfFails retry) throws Throwable
	{
		Object returnValue = null;
		for (int attemptCount = 1; attemptCount <= (1+retry.retryCount()); attemptCount++) {
			try {
				returnValue = pjp.proceed();
				break;
			} catch (Exception ex) {
				handleRetryException(pjp, ex, attemptCount, retry);
			}
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
			int attemptCount, RetryIfFails retry) throws Throwable
			{
		if (attemptCount == 1 + retry.retryCount()) {
			throw new RuntimeException(retry.message()+" for method: "+pjp.getKind(), ex);
		} else {
			log.error(String.format("%s: Attempt %d of %d failed with exception '%s'. Will retry immediately. %s",
					pjp.getSignature().toString().substring(pjp.getSignature().toString().lastIndexOf(".")),
					attemptCount,
					retry.retryCount(),
					ex.getClass().getCanonicalName(),
					ex.getMessage()));
			Thread.sleep(retry.sleepMillis());
		}
    }
}
