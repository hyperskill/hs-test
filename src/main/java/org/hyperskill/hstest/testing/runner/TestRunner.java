package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;

public interface TestRunner {
    default void setUp(TestCase<?> testCase) {}
    default void tearDown(TestCase<?> testCase) {}
    <T> CheckResult test(TestRun testRun);
}
