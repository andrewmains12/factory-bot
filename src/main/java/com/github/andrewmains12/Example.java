package com.github.andrewmains12;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Example {

    public Class<?> value();

    public Class<?> context() default Sentinel.class;

    public static class Sentinel {}
}
