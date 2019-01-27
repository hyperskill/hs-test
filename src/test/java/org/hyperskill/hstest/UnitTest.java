package org.hyperskill.hstest;

import org.hyperskill.hstest.stage.MainMethodTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;
import java.util.Objects;

public class UnitTest extends MainMethodTest {

    public UnitTest() throws Exception {
        super(Program.class);
    }

    @Override
    public List<TestCase> generateTestCases() {
        return List.of(
                new TestCase<>(null, "0 1"),
                new TestCase<>(null, "1 0"),
                new TestCase<>(null, "2 3"),
                new TestCase<>(null, "100 123"),
                new TestCase<>(null, "-1 5"),
                new TestCase<>(null, "5 -2"),
                new TestCase<>(null, "-300 -400")
        );
    }

    @Override
    public String solve(String input) {
        String[] nums = input.split("\\s+");
        int a = Integer.parseInt(nums[0]);
        int b = Integer.parseInt(nums[1]);
        return Objects.toString(a + b);
    }


    @Override
    public CheckResult checkSolved(String reply, String clue) {
        try {
            int actual = Integer.parseInt(reply.trim());
            int expected = Integer.parseInt(clue.trim());
            return new CheckResult(actual == expected);
        }
        catch (Exception ex) {
            return new CheckResult(false, "Can't check the answer");
        }
    }
}
