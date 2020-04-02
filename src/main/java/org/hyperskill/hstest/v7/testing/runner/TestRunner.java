package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

public interface TestRunner {
    <T> CheckResult test(TestCase<T> testCase);
}
