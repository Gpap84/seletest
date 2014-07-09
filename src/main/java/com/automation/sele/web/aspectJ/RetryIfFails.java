package com.automation.sele.web.aspectJ;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a custom annotation for retrying execution of methods using aspectJ advices
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryIfFails {
    int sleepMillis() default 1000;

    int retryCount() default 1;

    String message() default "Retry limit exceeded.";
}
