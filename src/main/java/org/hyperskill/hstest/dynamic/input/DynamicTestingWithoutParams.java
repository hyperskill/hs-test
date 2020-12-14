package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.testcase.CheckResult;

/**
 * Helper interface to store dynamic methods before we are able to know
 * with which data parameters to invoke them. It is used in parametrized tests.
 */
interface DynamicTestingWithoutParams {
    CheckResult handle(Object[] args);
}
