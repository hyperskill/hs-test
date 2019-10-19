package statics;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

enum StaticEnum {
    ONE, TWO, THREE
}

class Static7 {
    public static void main(String[] args) {
        System.out.println(StaticEnum.ONE);
        System.out.println(StaticEnum.TWO);
        System.out.println(StaticEnum.THREE);
        System.out.println(StaticEnum.ONE.getClass());
        System.out.println(StaticEnum.TWO.getClass());
        System.out.println(StaticEnum.THREE.getClass());
    }
}

public class TestEnum extends BaseStageTest {

    public TestEnum() {
        super(Static7.class);
    }

    private String output;

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        if (output == null) {
            output = reply;
            return CheckResult.TRUE;
        }
        return new CheckResult(reply.equals(output));
    }
}
