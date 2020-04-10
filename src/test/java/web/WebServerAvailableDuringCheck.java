package web;

import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.mocks.web.WebPage;
import org.hyperskill.hstest.mocks.web.WebServerMock;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class WebServerAvailableDuringCheckMain {
    public static void main(String[] args) {

    }
}

public class WebServerAvailableDuringCheck extends StageTest {

    public WebServerAvailableDuringCheck() {
        super(WebServerAvailableDuringCheckMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
            .runWith(new WebServerMock(45678)
                .setPage("/123", new WebPage().setContent("234")))
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        try {
            return new CheckResult("234".equals(Utils.getUrlPage("http://127.0.0.1:45678/123")), "");
        } catch (IOException ex) {
            return new CheckResult(false, "");
        }
    }
}
