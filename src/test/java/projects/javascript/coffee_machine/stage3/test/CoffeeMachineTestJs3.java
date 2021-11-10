package projects.javascript.coffee_machine.stage3.test;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


class TestClue {
    boolean cond;
    int num;
    boolean showTests;
    TestClue(boolean c, int n, boolean showTests) {
        cond = c;
        num = n;
        this.showTests = showTests;
    }
}

public class CoffeeMachineTestJs3 extends StageTest<TestClue> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @Override
    public List<TestCase<TestClue>> generate() {
        return List.of(
            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 0, true))
                .setInput("300\n65\n111\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 2, true))
                .setInput("600\n153\n100\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 2, true))
                .setInput("1400\n150\n100\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 2, true))
                .setInput("1400\n1500\n45\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(false, 2, true))
                .setInput("599\n250\n200\n10"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 867, true))
                .setInput( "345640\n43423\n23234\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(false, 1548, true))
                .setInput("345640\n434230\n23234\n19246"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(false, 172, true))
                .setInput( "34564\n43423\n23234\n19246"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 0, false))
                .setInput("399\n112\n111\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 3, false))
                .setInput("2400\n249\n100\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 1, false))
                .setInput("1400\n1500\n44\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(false, 2, false))
                .setInput("500\n250\n200\n10"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 171, false))
                .setInput("34564\n43423\n23234\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(true, 1547, false))
                .setInput("345640\n434230\n23234\n1"),

            new TestCase<TestClue>()
                .setAttach(new TestClue(false, 868, false))
                .setInput("345640\n43423\n23234\n19246")

        );
    }

    @Override
    public CheckResult check(String reply, TestClue clue) {
        String[] lines = reply.trim().split(":");

        if (lines.length <= 1) {
            return CheckResult.wrong("Looks like you didn't print anything!");
        }

        String userOutput = lines[lines.length - 1].trim();
        String loweredOutput = userOutput.toLowerCase();
        boolean ans = clue.cond;
        int amount = clue.num;
        if (ans) {
            if (amount > 0) {
                boolean isCorrect =
                    loweredOutput.contains(Integer.toString(amount))
                     && loweredOutput.contains("yes");

                if (isCorrect) {
                    return CheckResult.correct();
                } else {

                    String rightOutput =
                        "Yes, I can make that amount of coffee" +
                            " (and even " + amount + " more than that)";

                    if (clue.showTests) {
                        return new CheckResult(false,
                            "Your output:\n" +
                                userOutput +
                                "\nRight output:\n" +
                                rightOutput);
                    } else {
                        return CheckResult.wrong("");
                    }
                }
            }

            String rightOutput =
                "Yes, I can make that amount of coffee";

            if (loweredOutput.equals(rightOutput.toLowerCase())) {
                return CheckResult.correct();
            } else {
                if (clue.showTests) {
                    return new CheckResult(false,
                        "Your output:\n" +
                            userOutput +
                            "\nRight output:\n" +
                            rightOutput);
                } else {
                    return CheckResult.wrong("");
                }
            }

        } else {
            boolean cond1 = loweredOutput.contains("no");
            boolean cond2 = loweredOutput.contains(Integer.toString(amount));

            if (cond1 && cond2) {
                return CheckResult.correct();
            } else {

                String rightOutput = "No, I can make only " +
                    amount +" cup(s) of coffee";

                if (clue.showTests) {
                    return new CheckResult(false,
                        "Your output:\n" +
                        userOutput +
                        "\nRight output:\n" +
                        rightOutput);
                } else {
                    return CheckResult.wrong("");
                }
            }
        }
    }
}
