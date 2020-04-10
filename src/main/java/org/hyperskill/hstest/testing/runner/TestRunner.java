package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestRun;

public interface TestRunner {
    <T> CheckResult test(TestRun testRun);
}
