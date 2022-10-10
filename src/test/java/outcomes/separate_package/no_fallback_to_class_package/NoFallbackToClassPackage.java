package outcomes.separate_package.no_fallback_to_class_package;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class ClassWithoutMainMethod {

}

public class NoFallbackToClassPackage extends UserErrorTest<String> {

    @ContainsMessage
    String m1 = "Error in test #1\n" +
            "\n" +
            "Cannot find a main method in class " +
            "\"outcomes.separate_package.no_fallback_to_class_package.ClassWithoutMainMethod\".\n" +
            "Check if you declared it as \"public static void main(String[] args)\".";

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram(ClassWithoutMainMethod.class);
                    return CheckResult.correct();
                })
        );
    }
}
