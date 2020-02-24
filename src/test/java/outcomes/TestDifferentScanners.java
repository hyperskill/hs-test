package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class TestDifferentScannersMain {
    static int readNum() {
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }

    public static void main(String[] args) {
        System.out.println(readNum() + readNum());
    }
}

public class TestDifferentScanners extends StageTest<String> {

    public TestDifferentScanners() {
        super(TestDifferentScannersMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("8\n11\n").setAttach("19\n"),
            new TestCase<String>().setInput("1\n2\n").setAttach("3\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
