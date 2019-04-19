package outcomes;

import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileSystemExceptionTest extends MainMethodTest {

    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        File file = new File("in.txt");
        scanner = new Scanner(file);
        System.out.println(scanner.nextInt());
    }

    public FileSystemExceptionTest() throws Exception {
        super(FileSystemExceptionTest.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Error in test #1");
        exception.expectMessage("The file in.txt can't be " +
            "deleted after the end of the test. " +
            "Probably you didn't close File or Scanner.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
            new TestCase().addFile("in.txt", "123")
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }

    @After
    public void deleteFile() throws Exception {
        scanner.close();
        Files.deleteIfExists(Paths.get(Utils.CURRENT_DIR + "in.txt"));
    }

}
