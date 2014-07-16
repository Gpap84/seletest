package com.automation.seletest.core.testNG;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation executes method of a class after invocation of a method
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface PostConfiguration {

    Class<?> classReference();

    String method();

    String[] arguments() default {""};

}
