package projects.shell.coffee_machine.stage1_wa.test;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.List;


public class CoffeeMachineTestShell1Wa extends UserErrorTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        //Assume.assumeTrue(includeProcessTesting);
    }

    @ContainsMessage
    String msg = "Wrong answer in test #1\n" +
        "\n" +
        "You should make coffee exactly like in the example\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "12123123";

    @Override
    public List<TestCase<String>> generate() {
        return List.of(
            new TestCase<String>()
                .setAttach("Starting to make a coffee\n" +
                    "Grinding coffee beans\n" +
                    "Boiling water\n" +
                    "Mixing boiled water with crushed coffee beans\n" +
                    "Pouring coffee into the cup\n" +
                    "Pouring some milk into the cup\n" +
                    "Coffee is ready!")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        boolean isCorrect = reply.trim().equals(clue.trim());
        return new CheckResult(isCorrect,
            "You should make coffee exactly " +
                "like in the example");
    }
}
