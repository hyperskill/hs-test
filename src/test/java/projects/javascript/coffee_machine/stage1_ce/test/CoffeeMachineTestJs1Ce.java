package projects.javascript.coffee_machine.stage1_ce.test;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.List;


public class CoffeeMachineTestJs1Ce extends UserErrorTest<String> {

    @ContainsMessage
    String msg = "main.js:2\n" +
        "console.log(`Starting to make a coffee";

    @ContainsMessage
    String msg2 = "SyntaxError: missing ) after argument list";

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
