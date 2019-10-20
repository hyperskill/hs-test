package statics;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class TestSameReferenceMain {
    static List list1 = new ArrayList();
    static List list2 = list1;

    public static void main(String[] args) {
        System.out.println(list1 != null);
        System.out.println(list2 != null);
        System.out.println(list1 == list2);

        System.out.println(list1.getClass());
        System.out.println(list2.getClass());
        System.out.println(list1.size());
        System.out.println(list2.size());

        list1.add(123);
        System.out.println(list1.size());
        System.out.println(list2.size());

        list2.add(234);
        System.out.println(list1.size());
        System.out.println(list2.size());
    }
}

public class TestSameReference extends BaseStageTest {

    public TestSameReference() {
        super(TestSameReferenceMain.class);
    }

    private String rightOutput =
        "true\n" +
        "true\n" +
        "true\n" +
        "class java.util.ArrayList\n" +
        "class java.util.ArrayList\n" +
        "0\n" +
        "0\n" +
        "1\n" +
        "1\n" +
        "2\n" +
        "2\n";

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
        return new CheckResult(reply.equals(rightOutput));
    }
}
