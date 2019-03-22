package web;

import org.hyperskill.hstest.v3.common.Utils;
import org.hyperskill.hstest.v3.mocks.web.WebPage;
import org.hyperskill.hstest.v3.mocks.web.WebServerMock;
import org.hyperskill.hstest.v3.stage.MainMethodTest;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.TestCase;

import java.util.List;

public class WebServerAvailableDuringCheck extends MainMethodTest {

    public static void main(String[] args) {

    }

    public WebServerAvailableDuringCheck() throws Exception {
        super(WebServerAvailableDuringCheck.class);
    }

    @Override
    public List<TestCase> generateTestCases() {
        return List.of(
            new TestCase()
            .runWith(new WebServerMock(45678)
                .setPage("/123", new WebPage().setContent("234")))
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return new CheckResult("234".equals(Utils.getUrlPage("http://127.0.0.1:45678/123")));
    }

}
