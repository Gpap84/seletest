package com.automation.seletest.core.services.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation defines Mobile Test
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Retention(RUNTIME)
@Target({CONSTRUCTOR, METHOD, TYPE})
public @interface AppiumTest {


    String[] parametersXML() default "";

}
