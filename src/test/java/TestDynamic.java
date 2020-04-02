import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;

class Server {
    public static void main(String[] args) {
        System.out.println("Server started!");
    }
}

class Client {
    public static void main(String[] args) {
        System.out.println("Client started!");
    }
}

public class TestDynamic extends StageTest<String> {

    public TestDynamic() {
        super(Server.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicInput(() -> {
                TestedProgram server = new TestedProgram(Server.class);
                TestedProgram client = new TestedProgram(Client.class);

                String out1 = server.start();
                String out2 = client.start();

                return CheckResult.correct();
            })
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.correct();
    }
}
