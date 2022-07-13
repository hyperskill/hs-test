package outcomes.process_executor.go.no_find_executable_file_by_file_path;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class NoFindExecutableFileByFilePath extends UserErrorTest {

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
    String m2 = "no_find_executable_file_by_file_path";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("outcomes.process_executor.go.non_existent_file.go");
        return CheckResult.correct();
    }

}