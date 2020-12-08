package org.hyperskill.hstest.dynamic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicTest {
    int order() default 0;
}
