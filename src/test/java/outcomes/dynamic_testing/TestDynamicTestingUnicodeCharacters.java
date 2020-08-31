package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicTestingUnicodeCharactersMain {
    public static void main(String[] args) {
        System.out.print(new Scanner(System.in).nextLine());
    }
}

public class TestDynamicTestingUnicodeCharacters extends StageTest<Object> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(TestDynamicTestingUnicodeCharactersMain.class);
        pr.start();
        String unicodeLine = "Petřiny";
        String output = pr.execute(unicodeLine);
        return new CheckResult(unicodeLine.equals(output), "");
    }
}
