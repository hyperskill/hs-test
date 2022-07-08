package outcomes.process_executor.go.no_find_executable_file_by_file_path;

import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;


public class NoFindExecutableFileByFilePath extends UserErrorTest {

    @ContainsMessage
    String m =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a file to execute your code in directory \"" + FileUtils.cwd() +
                    "/src/test/java/outcomes/process_executor/go/no_find_executable_file_by_file_path\".";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
                FileUtils.cwd() + "/non_existent_file.go");
        return CheckResult.correct();
    }

}