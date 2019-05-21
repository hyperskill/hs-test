package org.hyperskill.hstest.dev.stage;

import java.util.ArrayList;
import java.util.List;

import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

public class AndroidTest extends MainMethodTest {

    public static void main(String[] args) { }

    public AndroidTest() throws Exception {
        super(AndroidTest.class);
    }


    @Override
    public List<TestCase> generateTestCases() {
        List<TestCase> test = new ArrayList<>();
        test.add(new TestCase<>());
        return test;
    }

    @Override
    public CheckResult check(String reply, Object clue) {


        return CheckResult.TRUE;
    }
}
