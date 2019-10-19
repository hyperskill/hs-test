package statics;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class Static6 {
    public static class Parent {
        String name;
        Parent(String name) {
            this.name = name;
        }
    }

    public static class Child extends Parent {
        String name;
        Child(String name) {
            super(name + name);
            this.name = name;
        }
    }

    public static Child inheritanceTest = new Child("Child");

    public static void main(String[] args) {
        System.out.println(inheritanceTest.getClass());
        System.out.println(inheritanceTest.name);
        inheritanceTest.name = "234";
        System.out.println(inheritanceTest.name);
    }
}

public class TestInheritance extends BaseStageTest {

    public TestInheritance() {
        super(Static6.class);
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
