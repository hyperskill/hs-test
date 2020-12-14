package org.hyperskill.hstest.dynamic.input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Deprecated, use hstest.dynamic.DynamicTest instead
 *
 * It is deprecated to be consistent with the python's hs-test library
 * that uses decorator @dynamic_test in module hstest.dynamic.dynamic_test
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicTestingMethod {
}
