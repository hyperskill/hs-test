package org.hyperskill.hstest.testing.swing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SwingComponent {
    String name() default "";
}
