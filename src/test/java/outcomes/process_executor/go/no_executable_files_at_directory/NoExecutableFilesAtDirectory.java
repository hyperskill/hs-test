package outcomes.process_executor.go.no_executable_files_at_directory;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;


public class NoExecutableFilesAtDirectory extends UserErrorTest<String> {

    @ContainsMessage
    String m1 = "Cannot find a file to execute your code.\nAre your project files located at";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("directory");
        return CheckResult.correct();
    }
}
