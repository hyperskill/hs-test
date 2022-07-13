package outcomes.process_executor.go.find_more_then_one_executable_file;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class FindMoreThenOneExecutableFile extends UserErrorTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @ContainsMessage
    String m1 = "Cannot decide which file to run out of the following: ";

    @ContainsMessage
    String m2 = "main1.go\"";

    @ContainsMessage
    String m3 = "main2.go\"";

    @ContainsMessage
    String m4 = "Leave one file with this line.";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("directory");
        return CheckResult.correct();
    }
}

