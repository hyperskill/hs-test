package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.testcase.TestCase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicTest {
    int order() default 0;
    int timeLimit() default TestCase.DEFAULT_TIME_LIMIT;
    String data() default "";
    int repeat() default 1;
    String feedback() default "";
}
