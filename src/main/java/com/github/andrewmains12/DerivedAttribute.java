package com.github.andrewmains12;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates a method as one which produces values for an object which are derived from other values in the example.
 * Methods with this annotation will be provided with one argument, the context object for the example.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DerivedAttribute {
    public String value() default "";
}
