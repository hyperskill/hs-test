package projects.javascript.coffee_machine.stage1_ex.test;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.List;


public class CoffeeMachineTestJs1Ex extends UserErrorTest<String> {

    @ContainsMessage
    String msg = "Exception in test #1";

    @ContainsMessage
    String msg2 = "main.js:2\n" +
        "console.log(0 .valueOf1())\n" +
        "               ^\n" +
        "\n" +
        "TypeError: 0.valueOf1 is not a function";

    @Override
    public List<TestCase<String>> generate() {
        return List.of(
            new TestCase<String>()
                .setInput("")
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
