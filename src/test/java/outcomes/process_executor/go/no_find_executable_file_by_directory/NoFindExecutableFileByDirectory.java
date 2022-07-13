package outcomes.process_executor.go.no_find_executable_file_by_directory;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class NoFindExecutableFileByDirectory extends UserErrorTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @ContainsMessage
    String m1 =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a file to execute your code in directory \"";

    @ContainsMessage
    String m2 = "no_find_executable_file_by_directory";

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram(
                            "outcomes.process_executor.go.non_existent_directory");
                    return CheckResult.correct();
                })
        );
    }
}
