package com.automation.sele.web.services.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({CONSTRUCTOR, METHOD, TYPE})
public @interface Author {
    /** the responsible author's name */
    String name();

    /** the responsible author's mail address */
    String mailTo();
}