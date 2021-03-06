package outcomes.static_fields;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestInheritanceMain {
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

public class TestInheritance extends StageTest {

    public TestInheritance() {
        super(TestInheritanceMain.class);
    }

    private String rightOutput =
        "class outcomes.static_fields.TestInheritanceMain$Child\n" +
        "Child\n" +
        "234\n";

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
        return new CheckResult(reply.equals(rightOutput), "");
    }
}
