package outcomes.process_executor.go.find_executable_file_by_filename;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;


public class FindExecutableFileByFileName extends StageTest<String> {

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram("main.go");
        String out = main.start();
        return new CheckResult("123\n".equals(out), "");
    }
}
