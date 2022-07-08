package outcomes.separate_package.fallback_to_class_package;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class ClassWithoutMainMethod {

}

public class FallbackToClassPackage extends StageTest {

    public FallbackToClassPackage() {
        super(FallbackToClassPackage.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
