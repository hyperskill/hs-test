package projects.go.coffee_machine.stage2.test;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class CoffeeMachineTestGo2 extends StageTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @Override
    public List<TestCase<String>> generate() {
        return List.of(
            new TestCase<String>()
                .setInput("25")
                .setAttach("25"),

            new TestCase<String>()
                .setInput("125")
                .setAttach("125"),

            new TestCase<String>()
                .setInput("1")
                .setAttach("1"),

            new TestCase<String>()
                .setInput("123")
                .setAttach("123")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        String[] lines = reply.split("\\n");
        if (lines.length < 3) {
            return new CheckResult(false,
                "Output contains less than 3 lines, but should output at least 3 lines");
        }
        String[] last3Lines = {
            lines[lines.length - 3],
            lines[lines.length - 2],
            lines[lines.length - 1]
        };

        int cups = Integer.parseInt(clue);
        boolean water = false, milk = false, beans = false;

        for(String line : last3Lines) {
            line = line.toLowerCase();

            if(line.contains("milk")) {
                milk = line.contains(Integer.toString(cups * 50));
                if (!milk) {
                    return new CheckResult(false,
                        "For the input " + clue + " your program output:\n\"" +
                            line + "\"\nbut the amount of milk should be " + (cups * 50));
                }

            } else if(line.contains("water")) {
                water = line.contains(Integer.toString(cups * 200));
                if (!water) {
                    return new CheckResult(false,
                        "For the input " + clue + " your program output:\n" +
                            line + "\nbut the amount of water should be " + (cups * 200));
                }

            } else if(line.contains("beans")) {
                beans = line.contains(Integer.toString(cups * 15));
                if (!beans) {
                    return new CheckResult(false,
                        "For the input " + clue + " your program output:\n" +
                            line + "\nbut the amount of beans should be " + (cups * 15));
                }


            } else {
                return new CheckResult(false,
                    "One of the last 3 lines " +
                        "doesn't contain \"milk\", \"water\" or \"beans\"");
            }
        }

        if (!water) {
            return new CheckResult(false,
                "There is no line with amount of water");
        }

        if (!milk) {
            return new CheckResult(false,
                "There is no line with amount of milk");
        }

        if (!beans) {
            return new CheckResult(false,
                "There is no line with amount of coffee beans");
        }

        return CheckResult.correct();
    }
}
