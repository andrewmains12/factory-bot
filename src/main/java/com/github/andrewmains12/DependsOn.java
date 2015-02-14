package com.github.andrewmains12;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies the fields on which a derived field depends; this allows us to
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOn {
    public String[] value();
}
