package projects.go.coffee_machine.stage1.test;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class CoffeeMachineTestGo1 extends StageTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

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
