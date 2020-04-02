package statics;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestChangingPrimitivesMain {

    public    static final int int1 = 0;
    protected static final int int2 = 0;
    private   static final int int3 = 0;
              static final int int4 = 0;
    public    static       int int5 = 0;
    protected static       int int6 = 0;
    private   static       int int7 = 0;
              static       int int8 = 0;

    static void print() {
        for (int i : new int[] {int1, int2, int3, int4, int5, int6, int7, int8}) {
            System.out.print(i);
        }
    }

    public static void main(String[] args) {
        print();
        int5++;
        int6++;
        int7++;
        int8++;
        print();
    }
}

public class TestChangingPrimitives extends StageTest {

    public TestChangingPrimitives() {
        super(TestChangingPrimitivesMain.class);
    }

    private String rightOutput = "0000000000001111";

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
