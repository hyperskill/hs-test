package web;

import org.hyperskill.hstest.dev.common.Utils;
import org.hyperskill.hstest.dev.mocks.web.WebPage;
import org.hyperskill.hstest.dev.mocks.web.WebServerMock;
import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class WebServerAvailableDuringCheck extends BaseStageTest {

    public static void main(String[] args) {

    }

    public WebServerAvailableDuringCheck() {
        super(WebServerAvailableDuringCheck.class);
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
        return new CheckResult("234".equals(Utils.getUrlPage("http://127.0.0.1:45678/123")));
    }

}
