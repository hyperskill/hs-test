package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testing.TestRun;

public interface TestRunner {
    <T> CheckResult test(TestRun testRun);
}
