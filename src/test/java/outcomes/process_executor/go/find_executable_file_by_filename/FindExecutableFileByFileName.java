package outcomes.process_executor.go.find_executable_file_by_filename;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class FindExecutableFileByFileName extends StageTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("main.go");
        String out = main.start();
        return new CheckResult("123\n".equals(out), "");
    }
}
