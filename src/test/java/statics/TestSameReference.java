package statics;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Static5 {
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
        super(Static5.class);
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
