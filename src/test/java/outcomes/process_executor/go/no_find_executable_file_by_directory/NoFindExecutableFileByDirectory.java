package outcomes.process_executor.go.no_find_executable_file_by_directory;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;


public class NoFindExecutableFileByDirectory extends UserErrorTest<String> {

    @ContainsMessage
    String m1 =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a file to execute your code in directory \"";

    @ContainsMessage
    String m2 = "no_find_executable_file_by_directory";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
                "outcomes.process_executor.go.non_existent_directory");
        return CheckResult.correct();
    }

}
