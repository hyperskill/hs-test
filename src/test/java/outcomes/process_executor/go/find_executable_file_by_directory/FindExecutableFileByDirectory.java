package outcomes.process_executor.go.find_executable_file_by_directory;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class FindExecutableFileByDirectory extends StageTest<String> {

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("directory_to_find");
        String out = main.start();
        return new CheckResult("321\n".equals(out), "");
    }
}
