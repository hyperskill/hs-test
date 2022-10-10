package outcomes.process_executor.go.find_executable_file_by_directory;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.includeProcessTesting;


public class FindExecutableFileByDirectory extends StageTest<String> {

    @BeforeClass
    public static void stopProcessTest() {
        Assume.assumeTrue(includeProcessTesting);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram main = new TestedProgram("directory_to_find");
                    String out = main.start();
                    return new CheckResult("321\n".equals(out), "");
                })
        );
    }
}
